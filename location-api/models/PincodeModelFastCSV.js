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

logger.debugLogger(`Reading Pincode from file:${readerFilePath}`);
exports.getPincodes = (pincode, callback)=>{
    var matchedPincodes=[];
    fs.createReadStream(readerFilePath)
        .pipe(csv.parse({headers: true}))
        .on('data', row =>{
            if(row.pincode == pincode){
                matchedPincodes.push(row);
            }
        })
        .on('error',(err)=>{
            return callback(err, null);
        })
        .on('end', (rowCount)=>{
            return callback(null, JSON.parse(JSON.stringify(matchedPincodes)));
        });
}

/*
exports.getPincode = async (pincode, callback)=>{
    var matchedPincode=[];
    try{
        var readStream = fs.createReadStream(readerFilePath);
        //fs.createReadStream(readerFilePath)
            readStream.pipe(csv.parse({headers: true}))
            .on('data', (row) =>{console.log(row);
                if(row.pincode == pincode){
                    matchedPincode.push(row);   
                    //readStream.close();                    
                    //TODO: close the read stream here, Bcz only one data required.
                }
            })
            .on('error',(err)=>{
                logger.errorLogger(err);
                return callback(err, null);
            })
            .on('end', (rowCount)=>{
                return callback(null, JSON.parse(JSON.stringify(matchedPincode)));
            });
    }catch(e){
        logger.errorLogger(e);
    }   
}
*/
exports.getFile = (callback)=>{
    return readerFilePath;
};
function findPincode(element){
    return element=='pincode';
  }
  