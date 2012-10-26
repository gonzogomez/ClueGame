package clueGameTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class GameSetupTests {
	private static Board board;
	
	@BeforeClass
	public static void setUp(){
		board = new Board();
	}
	
	//Tests people were loaded correctly from file
	@Test
	public void testLoadPeople() {
		//Tests human player
		Player testHuman = new Player();
		testHuman = board.getHumanPlayer();
		//Tests name
		assertEquals("Miss Scarlet", testHuman.getName());
		//Tests color
		assertEquals("Red", testHuman.getColor());
		//Tests starting location
		assertEquals(13, testHuman.getLocationX());
		assertEquals(22, testHuman.getLocationY());
		
		//Tests two computer players
		ArrayList<ComputerPlayer> players = new ArrayList<ComputerPlayer>();
		players = board.getComputerPlayers();
		
		//Tests first computer player in file
		//Tests name
		assertEquals("Mr. Green", players.get(0).getName());
		//Tests color
		assertEquals("Green", players.get(0).getColor());
		//Tests Starting location
		assertEquals(21, testHuman.getLocationX());
		assertEquals(6, testHuman.getLocationY());

		//Tests last computer player in file
		//Tests name
		assertEquals("Professor Plum", players.get(players.size()-1).getName());
		//Tests color
		assertEquals("Purple", players.get(players.size()-1).getColor());
		//Tests Starting location
		assertEquals(0, testHuman.getLocationX());
		assertEquals(19, testHuman.getLocationY());
	}
	
	//Tests cards were loaded correctly from file
	@Test
	public void testLoadCards(){
		ArrayList<Card> deck = new ArrayList<Card>();
		deck = board.getCards();
		
		//Tests that the deck contains the correct total number of cards
		assertEquals(21, deck.size());
		
		//Tests that the deck contains the correct number of cards of each type
		//Tests that the deck contains correct number of people
		assertEquals(6, board.getNumPeople());
		//Tests that the deck contains correct number of rooms
		assertEquals(9, board.getNumRooms());
		//Tests that the deck contains correct number of weapons
		assertEquals(6, board.getNumWeapons());
		
		//Tests the deck contains one room, one weapon, and one person
		//Test the deck contains a certain room
		Card room = new Card("Kitchen",Card.CardType.ROOM);
		Assert.assertTrue(deck.contains(room));
		//Test that the deck contains a certain person
		Card person = new Card("Mr. Green",Card.CardType.PERSON);
		Assert.assertTrue(deck.contains(person));
		//Test that the deck contains a certain person
		Card weapon = new Card("Lead Pipe",Card.CardType.WEAPON);
		Assert.assertTrue(deck.contains(weapon));
	}
	
	//Tests that cards are dealt correctly
	@Test
	public void testDealing(){
		ArrayList<Card> deck = new ArrayList<Card>();
		board.deal();
		deck = board.getCards();
		Player human = new Player();
		Player computer1 = new Player();
		Player computer2 = new Player();
		Player computer3 = new Player();
		Player computer4 = new Player();
		Player computer5 = new Player();
		human = board.getHumanPlayer();
		computer1 = board.getComputerPlayers().get(0);
		computer2 = board.getComputerPlayers().get(1);
		computer3 = board.getComputerPlayers().get(2);
		computer4 = board.getComputerPlayers().get(3);
		computer5 = board.getComputerPlayers().get(4);
		
		//Test that all the cards were dealt
		assertEquals(0, deck.size());
		
		//Test all players have roughly the same number of cards
		int baseNumber = human.getMyCards().size();
		Assert.assertTrue(Math.abs(computer1.getMyCards().size() - baseNumber) <= 1);
		Assert.assertTrue(Math.abs(computer2.getMyCards().size() - baseNumber) <= 1);
		Assert.assertTrue(Math.abs(computer3.getMyCards().size() - baseNumber) <= 1);
		Assert.assertTrue(Math.abs(computer4.getMyCards().size() - baseNumber) <= 1);
		Assert.assertTrue(Math.abs(computer5.getMyCards().size() - baseNumber) <= 1);
		
		//Test that one card is not given to two different players
		int numApperanceOfCard = 0;
		String name = "Mr. Green";
		//Iterates through all the computers to check for the appearance of the card
		for(Player p : board.getComputerPlayers()){
			for(Card c: p.getMyCards()){
				if(c.getName().equals(name)){
					++numApperanceOfCard;
				}
			}
		}
		//Iterates through the humans cards
		for(Card c: human.getMyCards()){
			if(c.getName().equals(name)){
				++numApperanceOfCard;
			}
		}
		//Tests that Mr.Green was only dealt to one player
		assertEquals(1,numApperanceOfCard);
		
		numApperanceOfCard = 0;
		name = "Lead Pipe";
		//Iterates through all the computers to check for the appearance of the card
		for(Player p : board.getComputerPlayers()){
			for(Card c: p.getMyCards()){
				if(c.getName().equals(name)){
					++numApperanceOfCard;
				}
			}
		}
		//Iterates through the humans cards
		for(Card c: human.getMyCards()){
			if(c.getName().equals(name)){
				++numApperanceOfCard;
			}
		}
		//Test that Lead Pipe was only dealt to one player
		assertEquals(1,numApperanceOfCard);
		
		numApperanceOfCard = 0;
		name = "Kitchen";
		//Iterates through all the computers to check for the appearance of the card
		for(Player p : board.getComputerPlayers()){
			for(Card c: p.getMyCards()){
				if(c.getName().equals(name)){
					++numApperanceOfCard;
				}
			}
		}
		//Iterates through the humans cards
		for(Card c: human.getMyCards()){
			if(c.getName().equals(name)){
				++numApperanceOfCard;
			}
		}
		//Test that Kitchen was only dealt to one player
		assertEquals(1,numApperanceOfCard);
	}
	
}
