var Web3 = require('web3');
var path = require('path');
var http = require('http');
var contract = require("@truffle/contract");
var myContractJSON  = require(path.join(__dirname, '../Etheruem/build/contracts/CSGOMatches.json'));

port = 3000;
host = '127.0.0.1';

//connect to node
const provider = new Web3.providers.HttpProvider("HTTP://127.0.0.1:7545");
const web3 = new Web3(provider);

var CSGOMatches = contract(myContractJSON);
CSGOMatches.setProvider(provider);

CSGOMatches.deployed().then(function(instance) {
    console.log(instance.address);

    //get all matches
    instance.getAllMatches().then(function(matches) {
        console.log("There are a total of " + matches.length + " matches recorded");
    });

    //set up server to listen for a games
    server = http.createServer( function(req, res) {
        if (req.method == 'POST') {
            res.writeHead(200, {'Content-Type': 'text/html'});
    
            var body = '';
            req.on('data', function (data) {
                body += data;
            });
            req.on('end', function () {
                //analyse the payload
                var obj = JSON.parse(body);
                console.log("The Player " + obj.player.name + ", is in the state of : " + obj.player.activity);

                res.end( '' );
            });
        }
    })

    server.listen(port, host);
    console.log('Listening at http://' + host + ':' + port);
});