package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Player {
	private String name;
	private Color color;
	private int locationX;
	private int locationY;
	private int location;
	
	private ArrayList<Card> myCards;
	
	public Player() {
		super();
		myCards = new ArrayList<Card>();
	}
	
	public Card disproveSuggestion(String person, String room, String weapon){
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		Random rand = new Random();
		for(Card c: myCards){
			if(c.getName().equals(person) || c.getName().equals(room) || c.getName().equals(weapon)){
				matchingCards.add(c);
			}
		}
		if(matchingCards.size() > 1){
			int ranNumber = rand.nextInt(matchingCards.size());
			return matchingCards.get(ranNumber);
		}
		if(matchingCards.size() == 1){
			return matchingCards.get(0);
		}
		return null;	
	}
	
	//Getters and Setters

	public String getName() {
		return name;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
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
