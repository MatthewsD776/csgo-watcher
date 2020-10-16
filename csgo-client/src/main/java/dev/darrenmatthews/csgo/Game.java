package dev.darrenmatthews.csgo;

import java.util.Date;
import uk.oczadly.karl.csgsi.state.MapState;
import uk.oczadly.karl.csgsi.state.MapState.GamePhase;
import uk.oczadly.karl.csgsi.state.RoundState;
import uk.oczadly.karl.csgsi.state.RoundState.RoundPhase;
import uk.oczadly.karl.csgsi.state.components.Team;

public class Game {

	public final String map;
	public final String creator;
	public final String gameMode;
	public final Date startTime;
	
	private int ctWins;
	private int tWins;
	private int currentRound;
	private RoundPhase currentRoundPhase;
	private boolean isEnded;

	public Game(String map, String creator, String gameMode) {
		this.map = map;
		this.creator = creator;
		this.gameMode = gameMode;
		this.ctWins = 0;
		this.tWins = 0;
		this.currentRound = 0;
		this.startTime = Helper.getDate();
		this.isEnded = false;
	}

	public void updateGameState(MapState mapState, RoundState roundState) {
		GamePhase gamePhase = mapState.getPhase().getEnum();
		RoundPhase roundPhase = roundState.getPhase().getEnum();
		
		//check if joined in progress game
		if(mapState.getRoundResults().size() > (currentRound + 1)) {
			
			int roundNumber = mapState.getRoundNumber();
			if(roundState.getPhase().getEnum().equals(RoundPhase.OVER)) {
				currentRound = roundNumber;
			} else {
				currentRound = roundNumber + 1;
			}
			
			System.out.println("Joined on Round " + currentRound);
			ctWins = mapState.getCounterTerroristStatistics().getScore();
			tWins = mapState.getTerroristStatistics().getScore();
		}
		
		
		//Check for a change in the round state
		if(roundPhase != null && !roundPhase.equals(currentRoundPhase)) {
			if(roundPhase.equals(RoundPhase.OVER)) {
				Team winningTeam = roundState.getWinningTeam().getEnum();
				System.out.println("Round " + currentRound + " is now OVER. " + winningTeam.toString() + " won");
				ctWins = mapState.getCounterTerroristStatistics().getScore();
				tWins = mapState.getTerroristStatistics().getScore();
				currentRound++;
			} else if(roundPhase.equals(RoundPhase.LIVE)) {
				System.out.println("Round " + currentRound + " is now LIVE");
				
			} else if(roundPhase.equals(RoundPhase.FREEZE_TIME)) {
				System.out.println("Round " + currentRound + " is now FROZEN");
			}
			
			currentRoundPhase = roundPhase;
		}
		
		//check the game is ended
		if(gamePhase.equals(GamePhase.GAME_OVER) && !isEnded) {
			System.out.println("Game is over");
			this.isEnded = true;
			this.printState();
		}
	}
	
	public void printState() {
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

	public boolean isEnded() {
		return isEnded;
	}

}
