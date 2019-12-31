var Web3 = require('web3');
var path = require('path');
var contract = require("@truffle/contract");
var myContractJSON  = require(path.join(__dirname, '../Etheruem/build/contracts/CSGOMatches.json'));

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
});
