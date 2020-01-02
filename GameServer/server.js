var Web3 = require('web3');
var path = require('path');
var http = require('http');
var contract = require("@truffle/contract");
var myContractJSON  = require(path.join(__dirname, '../Etheruem/build/contracts/CSGOMatches.json'));

port = 3000;
host = '127.0.0.1';

var gameObject = new Object();

initialised = false;

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

                if(initialised == false && obj.map == undefined && obj.player.activity == 'menu'){
                    gameObject.playerId = obj.player.steamid;
                    initialised = true;
                }

                if(initialised == true && obj.map != undefined) {
                    gameObject.mapName = obj.map.name;

                    if(obj.player.steamid == gameObject.playerId && obj.player.match_stats != undefined){
                        gameObject.kills = obj.player.match_stats.kills;
                        gameObject.assists = obj.player.match_stats.assists;
                        gameObject.deaths = obj.player.match_stats.deaths;
                        gameObject.mvps = obj.player.match_stats.mvps;
                        gameObject.score = obj.player.match_stats.score;
                    }

                    if(obj.map.phase == 'gameover'){
                        gameObject.ctWins = obj.map.team_ct.score;
                        gameObject.tWins = obj.map.team_t.score;

                        //check if someone won
                        var tWins = gameObject.tWins;
                        var ctWins = gameObject.ctWins;

                        var draw = (tWins == 15 && ctWins == 15);
                        var tWin = tWins == 16;
                        var ctWin = ctWins == 16;

                        if(draw || tWins || ctWins){
                            console.log(" --- Game Ended  --- ");
                        }
                    }
                }

                console.log(gameObject);

                res.end( '' );
            });
        }
    })

    server.listen(port, host);
    console.log('Listening at http://' + host + ':' + port);
});