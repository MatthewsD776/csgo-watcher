package dev.darrenmatthews.csgo;

import uk.oczadly.karl.csgsi.GSIObserver;
import uk.oczadly.karl.csgsi.GameStateContext;
import uk.oczadly.karl.csgsi.state.GameState;
import uk.oczadly.karl.csgsi.state.MapState;

public class GameObserver implements GSIObserver {

	@Override
	public void update(GameState state, GameStateContext context) {
		Game currentGame = CurrentGame.getCurrentGame();
		
		MapState map = state.getMapState();
		
		if (map != null) {
			if(currentGame == null) {
				String mapName = map.getName();
				String clientId = state.getProviderDetails().getClientSteamId();
				String gameMode = map.getMode().getEnum().toString();
				
				CurrentGame.newGame(mapName, clientId, gameMode);
			} else {
				currentGame.updateMapState(map);
			}
		} else {
			if(currentGame != null) {
				GameState finalRound = context.getPreviousState();
				CurrentGame.endGame(finalRound);
			}
		}
	}

}
