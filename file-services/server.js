const express = require('express');
const app = express();
const pincode_route = require('./app/routes/PincodeRoute');
pincode_route(app);
app.listen(8080, ()=>{
    console.log('Server for file-services is listning');
});