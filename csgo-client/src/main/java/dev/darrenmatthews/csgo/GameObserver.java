package dev.darrenmatthews.csgo;

import uk.oczadly.karl.csgsi.GSIObserver;
import uk.oczadly.karl.csgsi.GameStateContext;
import uk.oczadly.karl.csgsi.state.GameState;
import uk.oczadly.karl.csgsi.state.MapState;
import uk.oczadly.karl.csgsi.state.RoundState;

public class GameObserver implements GSIObserver {

	@Override
	public void update(GameState state, GameStateContext context) {
		Game currentGame = CurrentGame.getCurrentGame();
		MapState mapState = state.getMapState();
		RoundState roundState = state.getRoundState();
		
		if (mapState != null) {
			if(CurrentGame.gameEnded()) {
				//No game is recorded as being made so make one
				String mapName = mapState.getName();
				String clientId = state.getProviderDetails().getClientSteamId();
				String gameMode = mapState.getMode().getEnum().toString();
				CurrentGame.newGame(mapName, clientId, gameMode);
			} else {
				// A game object is present, update the sate
				currentGame.updateGameState(mapState, roundState);
			}
		} else {
			if(!CurrentGame.gameEnded()) {
				//No map state is given but the game was not completed so was a force exit
				CurrentGame.forceExit(context);
			}
		}
	}

}
