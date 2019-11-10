const webServer = require('./services/web-server'),
        logger = require('./util/log4js');
async function startup(){
try{
    logger.debugLogger('Starting application.');
        await webServer.intialize();
    }catch(err){
        logger.errorLogger(err);
        process.exit(1);
    }
}
startup();

async function shutdown(e){
    let err = e;
    try{
        await webServer.shutdown();
    }catch(e){
        logger.errorLogger(e);
        err = err || e;
    }
}
process.on('uncaughtException', err=>{
    logger.errorLogger(err);
    shutdown(err);
});