pragma solidity >=0.4.25 <0.7.0;
pragma experimental ABIEncoderV2;

import "./Match.sol";
import "./CSGOLibrary.sol";

contract CSGOMatches {
    Match[] allMatches;

    function addMatch(string memory _map, uint _ctRounds, uint _tRounds, CSGOLibrary.Player[] memory _players) public{
        bool exceedsRounds = (_ctRounds + _tRounds) <= 30;
        bool drawn = (_ctRounds == 15) && (_tRounds == 15);
        bool ctWon = _ctRounds == 16;
        bool tWon = _tRounds == 16;
        bool aWinner = ctWon || tWon;
        bool enoughPlayers = _players.length == 10;

        require(exceedsRounds, "Invalid Match");
        require(aWinner || drawn, "Invalid Match");
        require(enoughPlayers, "Invalid Match");

        Match thisMatch = new Match(_map, _ctRounds, _tRounds);

        for(uint count = 0; count < _players.length; count++){
            thisMatch.addPlayer(_players[count]);
        }

        allMatches.push(thisMatch);
    }

    function getAllMatches() public view returns (Match[] memory){
        return allMatches;
    }
}