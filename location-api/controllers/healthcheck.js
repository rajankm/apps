async function get(req, res, next){
    try{

        try{

        }catch(err){
            return res.status(400).json([{"Connection to ______": false}])
        }
        res.status(200).json(`Connection to api is Success.`);

    }catch(err){
        console.log(err);        
        next(err);
    }

}

exports.get = get;