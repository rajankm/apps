var controller = require('../controllers/locationApi');

module.exports = function(app){
    app.get('/pincodes/f/', controller.getFile);
    app.get('/pincodes/s/:pincode', controller.getPincodeDetail);
    app.get('/pincodes/a/:pincode', controller.getPincodeDetails);
};