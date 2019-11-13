const fs = require('fs'),
    csv = require('fast-csv'),
    path = require('path'),
    logger = require('../util/log4js.js');
var PropertiesReader = require('properties-reader');
var prop = PropertiesReader('../location-api/resource.properties');
var readerFilePath = prop.get('file.dir')+path.sep+prop.get('pincode.filename').toString();


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
            return callback(null, matchedPincodes);
        });
}

exports.getPincode = (pincode, callback)=>{
    var matchedPincode={};
    try{
        fs.createReadStream(readerFilePath)
            .pipe(csv.parse({headers: true}))
            .on('data', (row) =>{
                if(row.pincode == pincode){
                    matchedPincode = row;                
                    //TODO: close the read stream here, Bcz only one data required.
                }
            })
            .on('error',(err)=>{
                return callback(err, null);
            })
            .on('end', (rowCount)=>{
                return callback(null, matchedPincode);
            });
    }catch(e){
        logger.errorLogger(e);
    }   
}

exports.getFile = (callback)=>{
    return readerFilePath;
};
