package dev.darrenmatthews.csgo;

import uk.oczadly.karl.csgsi.state.MapState;
import uk.oczadly.karl.csgsi.state.MapState.TeamStats;

public class Game {

	public final String map;
	public final String creator;
	public final String gameMode;

	private int ctWins;

	private int tWins;

	private int currentRound;

	public Game(String map, String creator, String gameMode) {
		this.map = map;
		this.creator = creator;
		this.gameMode = gameMode;
		this.ctWins = 0;
		this.tWins = 0;
		this.currentRound = 0;
	}

	public void updateMapState(MapState mapState) {
		int roundState = mapState.getRoundNumber();

		if (roundState > currentRound) {
			System.out.println("Completed Round " + this.currentRound + ". Updating stats");
			updateStats(mapState);
			currentRound = roundState;
			System.out.println("Playing Round " + this.currentRound);
		}
	}

	private void updateStats(MapState mapState) {
		TeamStats ctStats = mapState.getCounterTerroristStatistics();
		TeamStats tStats = mapState.getTerroristStatistics();

		//TODO Check for mid game joining
		
		int newCtScore = ctStats.getScore();
		int newTScore = tStats.getScore();

		if (newCtScore > ctWins) {
			roundWin("CT");
		} else if (newTScore > tWins) {
			roundWin("T");
		}

		this.ctWins = newCtScore;
		this.tWins = newTScore;
	}

	private void roundWin(String team) {
		System.out.println(team + " won round " + this.currentRound);
	}

	public void finalRound(MapState finalRound) {
		this.currentRound = finalRound.getRoundNumber();
		this.updateStats(finalRound);

		System.out.println("Ended game on round " + this.currentRound);
		System.out.println("Counter Terrorists Won " + this.ctWins + " Round(s)");
		System.out.println("Terrorists Won " + this.tWins + " Round(s)");

		if (ctWins > tWins) {
			System.out.println("Counter Terrorists WON!!!!");
		} else if (tWins > ctWins) {
			System.out.println("Terrorists WON!!!!");
		} else {
			System.out.println("Twas a DRAW");
		}
	}

}
