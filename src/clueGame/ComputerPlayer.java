package clueGame;

import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private ArrayList<Card> seenCards;
	
	public ComputerPlayer() {
		super();
		seenCards = new ArrayList<Card>();
	}
	
	public void pickLocation(Set<BoardCell> targets){
		
	}
	
	public Suggestion createSuggestion(){
		return null;
	}
	
	public void updateSeen(Card seen){
		
	}
	//Getters and setters

	public char getLastRoomVisited() {
		return lastRoomVisited;
	}

	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}

	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}

	public void setSeenCards(ArrayList<Card> seenCards) {
		this.seenCards = seenCards;
	}
	
}
