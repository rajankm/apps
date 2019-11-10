const express = require('express'),
    router = new express.Router(),
    comm = require('../controllers/locationApi'),
    userdetails = require('../controllers/healthcheck');
    router.route('/healthCheckConnections').get(userdetails.get);
    router.route('/comm/data/commapi').post(comm.post);

    module.exports = router;
