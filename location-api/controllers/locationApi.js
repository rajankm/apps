const pincodeModel = require('../models/PincodeModelFastCSV.js'),
    logger = require('../util/log4js');
exports.getAllPincodes = (req, res) =>{
   pincodeModel.getAll(req, res);
};
exports.get = (req, res) =>{
    var pincode = req.params.pincode;
    logger.debugLogger('Pincode request for:'+pincode);
   
    pincodeModel.getPincode(pincode, (err, pincodeDetail)=>{
       new Promise((resolve, reject)=>{
            if(err){
                reject(err);
            }else if(pincodeDetail.length<1) {
                logger.debugLogger('No data found for Pincode:'+pincode);
            }else{
                
                logger.debugLogger('Data found for Pincode:'+pincode+' as below:\n'+
                                            JSON.stringify(pincodeDetail));
            }
            resolve(pincodeDetail);
       }).then(value=>{
            res.status(200);    
            res.send(value);
       }).catch(err=>{
            res.status(404);
            res.send('Error while fetching data:');
            logger.errorLogger('Error while fetching data:',err);
       });
        // res.setHeader('Content-Type', 'application/json');
          
    });
};
exports.getPincodes = (req, res) =>{
    const pincode = req.params.pincode;
    logger.debugLogger('Pincode request for:'+pincode);
    pincodeModel.getPincodes(pincode,(err, pincodeDetail)=>{
        if(err){
            res.status(404);
            res.send('Error while fetching data:');
            logger.errorLogger('Error while fetching data:',err);
        }  else {
            res.status(200);
            if(!pincodeDetail){
                res.status(404);
                loggeer.debugLogger('No data found for Pincode:'+pincode);
            }
            logger.debugLogger('Data found for Pincode:'+pincode+' as below:\n'+pincodeDetail);
            res.setHeader('Content-Type', 'application/json');
            res.send(pincodeDetail);
        }
    });
};

exports.getFile = (req, res)=>{
    pincodeModel.getFile((readerFilePath)=>{
        console.log(readerFilePath);
        res.sendFile(readerFilePath);
    });
};
