const pincodeModel = require('../models/PincodeModelFastCSV'),
    logger = require('../util/log4js');
exports.getAllPincodes = (req, res) =>{
   pincodeModel.getAll(req, res);
};
exports.getPincodeDetails = (req, res) =>{
    const pincode = req.params.pincode;
    pincodeModel.getPincodeDetails(pincode,(err, pincodeDetail)=>{
        if(err){
            res.status(404);
            res.send('Error while fetching data:');
            console.log('Error while fetching data:',err);
        }  else {
            res.status(200);
            if(!pincodeDetail){
                res.status(404);
            }
            res.setHeader('Content-Type', 'application/json');
            res.send(pincodeDetail);
        }
        
    });
};
exports.post = (req, res) =>{
    const pincode = req.params.pincode;
    pincodeModel.getPincodeDetail(pincode,(err, pincodeDetail)=>{
        if(err){
            res.status(404);
            res.send('Error while fetching data:');
            console.log('Error while fetching data:',err);
        }  else {
            res.status(200);
            if(!pincodeDetail){
                res.status(404);
            }
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
