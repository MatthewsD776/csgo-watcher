package dev.darrenmatthews.csgo;

import uk.oczadly.karl.csgsi.state.GameState;
import uk.oczadly.karl.csgsi.state.MapState;

public class CurrentGame {

	private static Game currentGame;

	public static Game getCurrentGame() {
		return currentGame;
	}

	public static void newGame(String map, String creator, String gameMode) {
		if (currentGame == null) {
			System.out.println("Creating a new Game on : " + map + " in " + gameMode);
			currentGame = new Game(map, creator, gameMode);
		}
	}

	public static void endGame(GameState finalRound) {
		if (currentGame != null) {
			MapState finalMapState = finalRound.getMapState();
			currentGame.finalRound(finalMapState);
			System.out.println("Ending game for : " + currentGame.map);
			currentGame = null;
		}
	}

}
