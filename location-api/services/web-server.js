const http = require('http'),
        express = require('express'),
        webServerConfig = require('../config/web-server'),
        router = require('./router');
let httpServer;
function initialize(){
    return new Promise((resolve, reject)=>{
        const app = express();
        httpServer = http.createServer(app);
        httpServer.timeout = 900000;

        app.use('/api', router);
        app.get('/healthCheckConnections', async(req, res)=>{
            res.end('Success.');
        });
        httpServer.listen(webServerConfig.port, err=>{
            if(err){
                reject(err);
                return;
            }
            console.log('Web server listning on localhost:${webServerConfig.port}');
            resolve();
        });
    });
}
module.exports.initialize = initialize;
function close(){
    return new Promise((resolve, reject)=>{
        httpServer.close((err)=>{
            if(err){
                reject(err);
                return;
            }
            resolve();
        });
    });
}

module.exports.close = close;