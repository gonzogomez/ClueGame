package clueGame;

import java.util.ArrayList;

public class Player {
	private String name;
	private String color;
	private int locationX;
	private int locationY;
	
	private ArrayList<Card> myCards;
	
	public Player() {
		super();
		myCards = new ArrayList<Card>();
	}
	
	public Card disproveSuggestion(String person, String room, String weapon){
		return null;	
	}
	
	//Getters and Setters

	public String getName() {
		return name;
	}

	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}
	
}
