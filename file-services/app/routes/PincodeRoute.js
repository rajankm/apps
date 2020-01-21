module.exports = function(app){
    var controller = require('../controllers/PincodeController');
    app.get('/pincodes/0/', controller.getAllPincodes);
    app.get('/pincodes/1/:pincode', controller.getPincodeDetail);
    app.get('/pincodes/2/:pincode', controller.getPincodeDetails);
};