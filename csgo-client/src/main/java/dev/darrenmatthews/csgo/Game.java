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
		
		System.out.println(mapState.getRoundResults());
		
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

		this.printState();
	}
	
	private void printState() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(" - Game State -").append(System.lineSeparator());
		builder.append("     Map : ").append(map).append(System.lineSeparator());
		builder.append("     GameMode : ").append(gameMode).append(System.lineSeparator());
		builder.append("     Rounds : ").append(currentRound).append(System.lineSeparator());
		builder.append("     CT Wins : ").append(ctWins).append(System.lineSeparator());
		builder.append("     T Wins : ").append(tWins).append(System.lineSeparator());
		
		if (ctWins > tWins) {
			builder.append("     Result : Counter Terrorists");
		} else if (tWins > ctWins) {
			builder.append("     Result : Terrorists");
		} else {
			builder.append("     Result : Draw");
		}
		
		builder.append(System.lineSeparator());
		
		System.out.println(builder.toString());
	}

}
