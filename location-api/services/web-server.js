const http = require('http'),
        express = require('express'),
        webServerConfig = require('../config/web-server.js'),
        apiRouter = require('./apiRouter.js'),
        webRouter = require('./webRouter.js'),
        logger = require('../util/log4js.js');
let httpServer;
 exports.initialize = ()=>{
    return new Promise((resolve, reject)=>{
        let app = express();
        httpServer = http.createServer(app);
        httpServer.timeout = 900000;
        app.use(express.static('views'));
        app.set('view engine', 'ejs');
        app.use('/', webRouter);
        app.use('/api', apiRouter);

        httpServer.listen(webServerConfig.port, err=>{
            if(err){
                logger.errorLogger(err);
                reject(err);
                return;
            }
            logger.debugLogger(`Web server listning on localhost:${webServerConfig.port}`);
            resolve();
        });
    });
}
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

exports.close = close;
