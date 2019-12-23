pragma solidity >=0.4.25 <0.7.0;
pragma experimental ABIEncoderV2;

import "./CSGOLibrary.sol";

/*
    A match will have :
    Final round count
    Player scoreboard
    Map
*/
contract Match {

    CSGOLibrary.Player[] scoreboard;
    string public map;
    uint public ctRounds;
    uint public tRounds;
    string gameType;

    constructor(string memory _map, uint _ctRounds, uint _tRounds) public {
        map = _map;
        ctRounds = _ctRounds;
        tRounds = _tRounds;
        gameType = 'Competitive';
    }

    function addPlayer(CSGOLibrary.Player memory _player) public{
        scoreboard.push(_player);
    }
}