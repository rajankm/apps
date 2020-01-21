const fs = require('fs'),
    csv = require('fast-csv'),
    path = require('path'),
    logger = require('../util/log4js.js');
    var FileReader = require('../util/FileReader.js');
var PropertiesReader = require('properties-reader');
var prop = PropertiesReader('../location-api/resource.properties');
var readerFilePath = prop.get('file.dir')+path.sep+prop.get('pincode.filename').toString();
exports.getPincode = (pincode, cb)=>{
    let fileReader = new FileReader(readerFilePath);
    getHeader(fileReader, (err, header, pinPos)=>{
        if(err){
            return cb(err);
        }
        getSingleData(fileReader, header, pinPos, pincode, (err, data)=>{
            return cb(err, data);
        } );
    });
}

exports.getPincodes = (pincode, cb)=>{
    let fileReader = new FileReader(readerFilePath);
    getHeader(fileReader, (err, header, pinPos)=>{
        if(err){
            return cb(err);
        }
        
        getData(fileReader, header, pinPos, pincode, (err, data)=>{
            return cb(err, data);
        } );
    });
    
}
getHeader = (fileReader, cb)=>{
    let header, pinPos=-1;
    fileReader.readHeader((err, line)=>{
        if(err){
            return cb(err);
        }
        header = line.split(',');
        pinPos = header.findIndex(findPincode);
        if(pinPos==-1){
            return cb(new Error(`Can't find text(pincode) in header.`));
        }
        return cb(null, header, pinPos);
    }); 
}

getData = (fileReader, header, pinPos, pincode, cb)=>{
    new Promise((resolve, reject)=>{
        let jsonData=[];
        fileReader.on('data', data=>{
            let dataArray = data.toString().split(',');
            let pin = dataArray[pinPos];
            if(pin == pincode){
                let json={}, i=0;
                header.forEach(key => {
                    json[key]=dataArray[i++];
                });
                jsonData.push(json);
            }
        });
        fileReader.on('end',()=>{
            logger.debugLogger(`File end event invoked.`);
            resolve(jsonData);
        });
    }).then(data=>{
        return cb(null, data);
    }).catch(err=>{
        return cb(err);
    });
}
getSingleData = (fileReader, header, pinPos, pincode, cb)=>{
    new Promise((resolve, reject)=>{
        let headerJson={}
        fileReader.on('data', data=>{
            let dataArray = data.toString().split(',');
            let pin = dataArray[pinPos]; 
            if(pin == pincode){
                fileReader.stream.close();
                let i=0;
                header.forEach(key => {
                    headerJson[key]=dataArray[i++];
                });
                resolve(headerJson);
            }
        });
        fileReader.on('end',()=>{
            logger.debugLogger('File end event invoked.');
            resolve(headerJson);
        });
    }).then(data=>{
        return cb(null, data);
    }).catch(err=>{
        return cb(err);
    });
}
exports.getFile = (callback)=>{
    return readerFilePath;
}
function findPincode(element){
    return element=='pincode';
  }
  