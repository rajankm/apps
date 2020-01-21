//const pincodeModel = require('../models/PincodeModel');
//const pincodeModel = require('../models/PincodeModelReadStream');
const pincodeModel = require('../models/PincodeModelFastCSV');
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
            res.status(204);
            if(pincodeDetail.length>=1){
                res.status(200);
            }
            res.setHeader('Content-Type', 'application/json');
            res.send(JSON.parse(pincodeDetail));
        }
        
    });
};
exports.getPincodeDetail = (req, res) =>{
    const pincode = req.params.pincode;
    pincodeModel.getPincodeDetail(pincode,(err, pincodeDetail)=>{
        console.log('pincodeDetail: '+pincodeDetail);
        if(err){
            res.status(404);
            res.send('Error while fetching data:');
            console.log('Error while fetching data:',err);
        }  else {
            res.status(204);
            if(pincodeDetail){
                res.status(200);
            }
            res.setHeader('Content-Type', 'application/json');
            res.send(JSON.parse(pincodeDetail));
        }
        
    });
};
