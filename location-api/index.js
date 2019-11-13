const webServer = require('./services/web-server.js'),
        logger = require('./util/log4js');
        //logger = log4js.getLogger();
async function startup(){
try{
    logger.debugLogger('Starting application.');
        await webServer.initialize();
    }catch(err){
        logger.errorLogger(err);
        process.exit(1);
    }
}
startup();

async function shutdown(e){
    let err = e;
    try{
        await webServer.close();
    }catch(e){
        logger.errorLogger(e);
        err = err || e;
    }
}
process.on('uncaughtException', err=>{
    logger.errorLogger(err);
    shutdown(err);
});