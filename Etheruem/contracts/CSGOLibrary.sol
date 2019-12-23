pragma solidity >=0.4.25 <0.7.0;

library CSGOLibrary {
    struct Player {
        uint kills;
        uint deaths;
        uint mvps;
        uint score;
        string player;
    }
}