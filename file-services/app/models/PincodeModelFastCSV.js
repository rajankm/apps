const fs = require('fs'),
    csv = require('fast-csv'),
    path = require('path'),
    in_file_path = path.join('/home/rajan/Stuffs/Projects/Config','/all_india_PO_list_without_APS_offices_ver2_lat_long.csv');

exports.getPincodeDetails = (pincode, callback)=>{
    var matchedPincodes=[];
    fs.createReadStream(in_file_path)
        .pipe(csv.parse({headers: true}))
        .on('data', row =>{
            if(row.pincode == pincode){
                matchedPincodes.push(row);
            }
        })
        .on('error',(error)=>{
            return callback(err, null);
        })
        .on('end', (rowCount)=>{
            console.log('RowCount:'+rowCount);
            return callback(null, matchedPincodes);
        });
}

exports.getPincodeDetail = (pincode, callback)=>{
    var matchedPincode;
    fs.createReadStream(in_file_path)
        .pipe(csv.parse({headers: true}))
        .on('data', row =>{
            if(row.pincode == pincode){
                matchedPincode = row;
                fs.close();    
            }
        })
        .on('error',(error)=>{
            return callback(err, null);
        })
        .on('end', (rowCount)=>{
            console.log('RowCount:'+rowCount);
            return callback(null, matchedPincode);
        });
}
