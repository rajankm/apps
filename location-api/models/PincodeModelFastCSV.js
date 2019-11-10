const fs = require('fs'),
    csv = require('fast-csv'),
    path = require('path');

var PropertiesReader = require('properties-reader');
var prop = PropertiesReader('../location-api/resource.properties');
var readerFilePath = prop.get('file.dir')+path.sep+prop.get('pincode.filename').toString();

exports.getPincodeDetails = (pincode, callback)=>{
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

exports.getPincodeDetail = (pincode, callback)=>{
    var matchedPincode={};console.log(pincode);
    fs.createReadStream(readerFilePath)
        .pipe(csv.parse({headers: true}))
        .on('data', row =>{
            if(row.pincode == pincode){
                console.log(row);
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
}

exports.getFile = (callback)=>{
    return readerFilePath;
};
