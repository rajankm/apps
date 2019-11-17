'use strict';

var fs = require('fs');
var EventEmitter = require('events');
const lineReader = require('line-reader');
var logger = require('../util/log4js.js');
var file;
class FileReader extends EventEmitter{
    
    constructor(file){
        super();
        this.file = file;
        this.stream = fs.createReadStream(file);
        this.stream.on('data',this.onData.bind(this));
        this.stream.on('end', this.onEnd.bind(this));
        this.buff = Buffer.alloc(0);
        this.lineBuffs = ['\r\n', '\r', '\n'].map(a => Buffer.from(a));
    }
  async readHeader(cb){ 
     let exists = fs.existsSync(this.file);
     if(!exists){
         return cb(new Error(`File/Dir:${this.file} doesn't exist.`));
     }
      new Promise((resolve, reject)=>{
       try{
        lineReader.open(this.file, (err, reader)=>{
            if(err){
                reject(err);
            }
            if(reader.hasNextLine()){
                reader.nextLine((err, line)=>{
                resolve(line);
            });
            } else{
                reject(new Error('Empty File:', this.file));
            }
        });
    }catch(e){
        console.log(e);
    }
      }).then(data=>{
        return cb(null, data);
      }).catch(err=>{
        return cb(err);
      });
    /*new Promise((resolve, reject)=>{
        
        lineReader.open(this.file, (err, reader)=>{
            if(err){
                reject(err);
            }
            if(reader.hasNextLine()){
                reader.nextLine((err, line)=>{
                resolve(line);
            });
            } else{
                logger.infoLogger('Empty File:', this.file);
                reject(new Error('Empty File:', this.file));
            }
        });
    }).then(data=>{
        return cb(null, data);
    }).catch(err=>{
        return cb(err, null);
    });*/
}

    async onData(data){
        this.buff = Buffer.concat([this.buff, data]);
        do{
            var found = false;
            for(var i=0; i<this.lineBuffs.length; i++){
                var lineBuff = this.lineBuffs[i];
                var index = this.buff.indexOf(lineBuff);
                if(index !== -1){
                    found = true
                    var emitBuff = this.buff.slice(0, index);
                    this.buff = this.buff.slice(index + lineBuff.length);
                    this.emit('data', emitBuff);
                    break;
                }
            }
        }while(found);
    }
    onEnd(){
        this.stream.close();
        this.emit('data', this.buff);
        this.emit('end');
    }
};
module.exports = FileReader;