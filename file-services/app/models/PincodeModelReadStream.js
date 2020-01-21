const fs = require('fs'),
    es = require('event-stream'),
    lineReader = require('line-reader'),
    path = require('path'),
    in_file_path = path.join('/home/rajan/Stuffs/Projects/Config','/all_india_PO_list_without_APS_offices_ver2_lat_long.csv');
var json = {}, headers, pinPos, jsonStr={}, i=0;
    lineReader.open(in_file_path, (err, reader)=>{
        if(err){
            console.log('Error in PincodeModel.ReadStream.lineReader.open:',err);
            throw err;
        }
        if(reader.hasNextLine()){
          reader.nextLine((err, line)=>{
            headers = line.split(',');
            pinPos = headers.findIndex(findPincode);
          });
        } else{
            console.log('Empty File:', in_file_path);
            throw new Error('Empty File:', in_file_path);
        }
      });
      function callback(err, reqPicode){

      }
exports.getPincodeDetail = (pincode, callback)=> {
    var readStream = fs.createReadStream(in_file_path)
        .pipe(es.split())
            .pipe(es.mapSync(function(line){
                var data = line.split(',');
                if(data[pinPos]==pincode){
                   jsonStr[i++] = initJSON(headers, data);
                   //console.log(jsonStr);
                   //return callback(initJSON(headers, data));
                    //readStream.end();
                }
    }).on('error', function(err){
        console.log('Error while reading file.', err);
    })
    .on('end', function(){
        console.log('Read entire file.');
    })
    );
    console.log(jsonStr);
    return jsonStr;
}
function initJSON(jsonHeader, data){
    for(var i=0; i<jsonHeader.length; i++){
        json[jsonHeader[i]] = data[i];
    }
    return JSON.stringify(json);
}   
function findPincode(element){
    return element=='pincode';
}