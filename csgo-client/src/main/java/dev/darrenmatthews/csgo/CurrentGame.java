package dev.darrenmatthews.csgo;

import uk.oczadly.karl.csgsi.GameStateContext;

public class CurrentGame {

	private static Game currentGame;

	public static Game getCurrentGame() {
		return currentGame;
	}

	public static void newGame(String map, String creator, String gameMode) {
		if (gameEnded()) {
			System.out.println("Creating a new Game on : " + map + " in " + gameMode);
			currentGame = new Game(map, creator, gameMode);
		}
	}
	
	public static boolean gameEnded() {
		if(currentGame == null) {
			return true;
		}
		
		return currentGame.isEnded();
	}

	public static void forceExit(GameStateContext context) {
		System.out.println("You Exited the game before it officially exited");
		currentGame.printState();
		currentGame = null;
		//GameState finalRound = context.getPreviousState();
		//CurrentGame.endGame(finalRound);
	}

}
