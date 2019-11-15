const fs = require('fs'),
    csv = require('fast-csv'),
    path = require('path'),
    logger = require('../util/log4js.js');
    var FileReader = require('../util/FileReader.js');
var PropertiesReader = require('properties-reader');
var prop = PropertiesReader('../location-api/resource.properties');
var readerFilePath = prop.get('file.dir')+path.sep+prop.get('pincode.filename').toString();

exports.getPincode = async (pincode, cb)=>{
    var fileReader = new FileReader(readerFilePath);
    var header, pinPos=-1;
    await fileReader.readHeader((err, line)=>{
        if(err){
            logger.errorLogger(err);
            return cb(err, null);
        }
        header = line.split(',');
        pinPos = header.findIndex(findPincode);

    }); 
    new Promise((resolve, reject)=>{
        var headerJson={}
        fileReader.on('data', data=>{
            var dataArray = data.toString().split(',');
            var pin = dataArray[pinPos];
            if(pin == pincode){
                fileReader.stream.close();
                var i=0;
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
    });
}

exports.getPincodes = async (pincode, cb)=>{
    var fileReader = new FileReader(readerFilePath);
    var header, pinPos=-1;
    await fileReader.readHeader((err, line)=>{
        if(err){
            logger.errorLogger(err);
            return cb(err, null);
        }
        header = line.split(',');
        pinPos = header.findIndex(findPincode);

    }); 
    new Promise((resolve, reject)=>{
        var jsonData=[];
        fileReader.on('data', data=>{
            var dataArray = data.toString().split(',');
            var pin = dataArray[pinPos];
            if(pin == pincode){
                //fileReader.stream.close();
                var json={}, i=0;
                header.forEach(key => {
                    json[key]=dataArray[i++];
                });
                jsonData.push(json);
            }
            //resolve(jsonData);
        });
        fileReader.on('end',()=>{
            logger.debugLogger('File end event invoked.');
            resolve(jsonData);
        });
    }).then(data=>{
        return cb(null, data);
    });
}
exports.getFile = (callback)=>{
    return readerFilePath;
};
function findPincode(element){
    return element=='pincode';
  }
  