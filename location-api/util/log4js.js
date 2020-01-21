const path = require('path');
var log4js = require('log4js');
var PropertiesReader = require('properties-reader');
var prop = PropertiesReader('./resource.properties');
//var prop = PropertiesReader('../resource.properties');// for local run
var logPath = prop.get('log.path')+path.sep;
//var logPath = '/home/rajan/Stuffs/Project/npp-apps/logs/'
log4js.configure({
    appenders: {
        out: { type: 'console' },
        default: { type: 'dateFile', filename: logPath+'default', pattern: "-dd-MM.log", alwaysIncludePattern: true },
        info: { type: 'dateFile', filename: logPath+'location-api-info',pattern:"-dd-MM.log", alwaysIncludePattern: true},
        debug: { type: 'dateFile', filename: logPath+'location-api-debug',pattern:"-dd-MM.log", alwaysIncludePattern: true},
        error: { type: 'dateFile', filename: logPath+'locatoin-api-error', pattern:"-dd-MM.log", alwaysIncludePattern: true},
    },
    categories: {
      default: { appenders: ['out','default'], level: 'trace' },
      info: {appenders:['info'], level: 'info' },
      debug: { appenders: ['debug'], level: 'debug' },
      error: { appenders: ['error'], level: 'warn'  },
    }
});
var logger = log4js.getLogger();
var logger_debug = log4js.getLogger('debug');// for debugging the application.
var logger_info = log4js.getLogger('info');//for data flows trace.
var logger_error = log4js.getLogger('error');// for all type of errors, warn and fatal.
var console_logger = log4js.getLogger('out');

exports.debugLogger =  (data)=>{
    logger_debug.debug(data);
    console_logger.trace(data);
}
exports.infoLogger = (data)=>{
    logger_info.info(data);
    console_logger.trace(data);
}
exports.warnLogger = (data)=>{
    logger_error.warn(data);
    console_logger.trace(data);
}
exports.errorLogger = (data)=>{
    logger_error.error(data);
    console_logger.error(data);
}
exports.fatalLogger = (data)=>{
    logger_error.fatal(data);
    console_logger.trace(data);
}
exports.consoleLogger = (data)=>{
    console_logger.info(data);
}
/**
 * Some Examples
 */
/*logger_debug.trace('Trace log in fileServices');
logger_debug.debug('Debug log in fileServices');
logger_info.info('Info log in fileServices');
logger_error.warn('Warn log in fileServices');
logger_error.error('Error log in fileServices');
logger_error.fatal('Fatal log in fileServices');
*/
