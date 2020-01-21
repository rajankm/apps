const express = require('express'),
    router = new express.Router(),

    locationApi = require('../controllers/locationApi.js'),
    healthcheck = require('../controllers/healthcheck.js');
    
    router.route('/healthCheckConnections').get(healthcheck.get);
    router.route('/home').get(healthcheck.home);
    router.route('/er').get(healthcheck.err);
    module.exports = router;
