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
    readHeader(cb){
        new Promise((resolve, reject)=>{
            fileExistsSync(this.file, (err, exists)=>{
                if(err){
                    reject(err);
                }
                resolve(exists);
            });   
        }).then(exists=>{
            if(exists){
                lineReader.open(this.file, (err, reader)=>{
                    if(err){
                        return cb(err);
                    }
                    if(reader.hasNextLine()){
                        reader.nextLine((err,line)=>{
                            if(err){
                                return cb(err);
                            }
                            return cb(null, line);
                        });
                    } else{
                        return cb(new Error(`No header defined in file:${this.file}`));     
                    }    
                });
            } else{
                return cb(new Error(`File/Dir: ${this.file} not exist any more.`));
            }
        }).catch(err=>{
            return cb(err);
        });
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
function fileExistsSync(path, cb){
    try{
        return cb(null, fs.statSync(path).isFile());
    }catch(e){
        return cb(e, false);
    }
}
module.exports = FileReader;