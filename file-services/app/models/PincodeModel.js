
const fs = require('fs'),
    path = require('path'),
    lineReader = require('line-reader'),
    in_file_path = path.join('/home/rajan/Stuffs/Projects/Config','/all_india_PO_list_without_APS_offices_ver2_lat_long.csv'),
    util = require('../util/PincodeUtil')

    var json = {}, headers, pinPos, jsonStr={}, i=0;

exports.getPincodeDetail = (pincode)=>{

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


  var readStream = fs.createReadStream(in_file_path, {encoding: 'utf8', autoClose: true, highWaterMark: 1024, start:1});
    readStream.on('row', (row)=>{
      console.log('Row: '+row);
    var data = row.split(',');
    //console.log(data[1]+'\n---------------\n'); 
    if(data[pinPos]==pincode){
      jsonStr[i++] = initJSON(headers, data);
      //console.log('json: '+jsonStr[i++]);
      }
    }).on('end', ()=>{
      console.log('Read file end.');
    });
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
