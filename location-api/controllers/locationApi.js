const pincodeModel = require('../models/PincodeModelFastCSV.js'),
    logger = require('../util/log4js.js');
exports.getAllPincodes = (req, res) =>{
   pincodeModel.getAll(req, res);
};
exports.get = (req, res) =>{
    let pincode = req.params.pincode;
    logger.debugLogger('Pincode request for:'+pincode);
   
    pincodeModel.getPincode(pincode, (err, pincodeDetail)=>{
        new Promise((resolve, reject)=>{
            if(err){
                reject(err);
            }else if(Object.keys(pincodeDetail).length<1) {
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
            res.send('Error while fetching data.');
            logger.errorLogger(`Error while fetching data:\n${err.stack || err}`);
       });
    });
};
exports.getPincodes = (req, res) =>{
    let pincode = req.params.pincode;
    logger.debugLogger('Pincode request for:'+pincode);
   
    pincodeModel.getPincodes(pincode, (err, pincodeDetail)=>{
        new Promise((resolve, reject)=>{
            if(err){
                reject(err);
            }else if(Object.keys(pincodeDetail).length<1) {
                logger.debugLogger('No data found for Pincode:'+pincode);
            }else{
                logger.debugLogger(`Data found for Pincode:${pincode} as below:\n` +
                                            JSON.stringify(pincodeDetail));
            }
            resolve(pincodeDetail);
       }).then(value=>{
            res.status(200);
            res.send(value);
       }).catch(err=>{
            res.status(404);
            res.send(`Error while fetching data:`);
            logger.errorLogger(`Error while fetching data:${err}`);
       });
    });
};
exports.getFile = (req, res)=>{
    pincodeModel.getFile((readerFilePath)=>{
        console.log(readerFilePath);
        res.sendFile(readerFilePath);
    });
};
