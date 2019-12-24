const CSGOMatches = artifacts.require("CSGOMatches");
const truffleAssert = require('truffle-assertions');

contract('CSGOMatches', (accounts) => { 
    const account1 = accounts[0];
    const account2 = accounts[1];  
    let instance;

    let player1 = {kills: 10, deaths: 9, mvps: 2, score: 70, player: "Darren"};
    let player2 = {kills: 3, deaths: 16, mvps: 2, score: 80, player: "Dazza"};
    let player3 = {kills: 2, deaths: 5, mvps: 1, score: 10, player: "Daz"};
    let player4 = {kills: 10, deaths: 9, mvps: 2, score: 70, player: "RazzleDazzle"};
    let player5 = {kills: 10, deaths: 9, mvps: 2, score: 70, player: "D"};
    let player6 = {kills: 10, deaths: 9, mvps: 2, score: 70, player: "Big D"};
    let player7 = {kills: 10, deaths: 9, mvps: 2, score: 70, player: "Dee"};
    let player8 = {kills: 10, deaths: 9, mvps: 2, score: 70, player: "NotSoSlimShady"};
    let player9 = {kills: 10, deaths: 9, mvps: 2, score: 70, player: "Darr"};
    let player10 = {kills: 10, deaths: 9, mvps: 2, score: 70, player: "Darra"};
    let players = [player1, player2, player3, player4, player5, player6, player7, player8, player9, player10]
    
    before(async function(){
        instance = await CSGOMatches.deployed();
    });

    describe('Add Match', function() {

        it('should be able to create a match when a winner is declared', async () => {
            let ctWins = 16;
            let tWins = 8;
            let map = "Dust 2"; 
            
            let sizeBefore = (await instance.getAllMatches({from : account1})).length;

            await instance.addMatch(map, ctWins, tWins, players, {from : account1});

            let sizeAfter = (await instance.getAllMatches({from : account1})).length;

            assert.equal(sizeAfter, sizeBefore + 1, "Match was not added");
        });
    
        it('should be able to create a match when a drawn', async () => {
            let ctWins = 15;
            let tWins = 15;
            let map = "Dust 2"; 
            
            let sizeBefore = (await instance.getAllMatches({from : account1})).length;

            await instance.addMatch(map, ctWins, tWins, players, {from : account1});

            let sizeAfter = (await instance.getAllMatches({from : account1})).length;

            assert.equal(sizeAfter, sizeBefore + 1, "Match was not added");
        });

        it('should not create match when more than 30 rounds', async () => {
            let ctWins = 20;
            let tWins = 20;
            let map = "Dust 2"; 

            await truffleAssert.reverts(instance.addMatch(map, ctWins, tWins, players, {from : account1}), 'Invalid Match');
        });

        it('should not create match when match not completed', async () => {
            let ctWins = 14;
            let tWins = 6;
            let map = "Dust 2"; 

            await truffleAssert.reverts(instance.addMatch(map, ctWins, tWins, players, {from : account1}), 'Invalid Match');
        });

        it('should not create match with a negative round score', async () => {
            let ctWins = -14;
            let tWins = -6;
            let map = "Dust 2"; 

            await truffleAssert.reverts(instance.addMatch(map, ctWins, tWins, players, {from : account1}), 'Invalid Match');
        });

        it('should not create match without 10 players', async () => {
            let ctWins = 16;
            let tWins = 8;
            let map = "Dust 2"; 
            let underPlayers = [player1, player2, player3];

            await truffleAssert.reverts(instance.addMatch(map, ctWins, tWins, underPlayers, {from : account1}), 'Invalid Match');
        });

        it('should emit an event when match is added', async () => {
            let ctWins = 16;
            let tWins = 8;
            let map = "Dust 2"; 

            let tx = await instance.addMatch(map, ctWins, tWins, players, {from : account1});

            truffleAssert.eventEmitted(tx, 'NewMatch', null, "Event was not emitted");
        });
    });
});