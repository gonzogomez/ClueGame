package clueGameTests;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameActionsTests {
	
	private static Board board;
	private static Card mustardCard;
	private static Card mrGreenCard;
	private static Card leadPipeCard;
	private static Card knifeCard;
	private static Card hallCard;
	private static Card kitchenCard;
	
	@BeforeClass
	public static void setUp(){
		board = new Board();
		mustardCard = new Card("Colonel Mustard", Card.CardType.PERSON);
		mrGreenCard = new Card("Mr. Green", Card.CardType.PERSON);
		leadPipeCard = new Card("Lead Pipe", Card.CardType.WEAPON);
		knifeCard = new Card("Knife", Card.CardType.WEAPON);
		hallCard = new Card("Hall", Card.CardType.ROOM);
		kitchenCard = new Card("Kitchen", Card.CardType.ROOM);
	}
	
	//Test make an accusation
	@Test
	public void testAccusation() {
		//Set the "answer"
		Solution testAnswer = new Solution("Mr. Green","Kitchen", "Lead Pipe");
		board.setSolution(testAnswer);
		
		//Test when accusation is correct
		Assert.assertTrue(board.checkAccusation("Mr. Green", "Kitchen", "Lead Pipe"));
		
		//Test when accusation is incorrect with wrong room
		Assert.assertFalse(board.checkAccusation("Mr. Green", "Library", "Lead Pipe"));

		//Test when accusation is incorrect with wrong person
		Assert.assertFalse(board.checkAccusation("Mrs. White", "Kitchen", "Lead Pipe"));

		//Test when accusation is incorrect with wrong weapon
		Assert.assertFalse(board.checkAccusation("Mr. Green", "Kitchen", "Revolver"));
		
		//Test when accusation is incorrect with all three wrong
		Assert.assertFalse(board.checkAccusation("Colonel Mustard", "Billiard Room", "Knife"));
	}
	
	//Test selecting a target location
	//Room Preference Tests
	@Test
	public void testTargetRoomPreference(){
		ComputerPlayer player = new ComputerPlayer();
		//Test1
		board.calcTargets(board.calcIndex(14, 4), 2);
		int loc_Room = 0;
		//Run the test 100 times
		for(int i=0; i<100; i++){
			player.pickLocation(board.getTargets());
			BoardCell selected = board.getCells().get(board.calcIndex(player.getLocationX(), player.getLocationY()));
			if(selected == board.getCells().get(board.calcIndex(13, 4))){
				loc_Room++;
			}
		}
		//Assert that the room was chosen each time
		assertEquals(100, loc_Room);
	
		//Test2
		board.calcTargets(board.calcIndex(5, 15), 3);
		loc_Room = 0;
		//Run the test 100 times
		for(int i=0; i<100; i++){
			player.pickLocation(board.getTargets());
			BoardCell selected = board.getCells().get(board.calcIndex(player.getLocationX(), player.getLocationY()));
			if(selected == board.getCells().get(board.calcIndex(5, 15))){
				loc_Room++;
			}
		}
		//Assert that the room was chosen each time
		assertEquals(100, loc_Room);
		
		//Test3
		board.calcTargets(board.calcIndex(13, 16), 4);
		loc_Room = 0;
		//Run the test 100 times
		for(int i=0; i<100; i++){
			player.pickLocation(board.getTargets());
			BoardCell selected = board.getCells().get(board.calcIndex(player.getLocationX(), player.getLocationY()));
			if(selected == board.getCells().get(board.calcIndex(10, 17))){
				loc_Room++;
			}
		}
		//Assert that the room was chosen each time
		assertEquals(100, loc_Room);
	}
	
	//Random choice tests
	@Test
	public void testTargetRandomSelection(){
		ComputerPlayer player = new ComputerPlayer();
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(board.calcIndex(14, 0), 2);
		int loc_12_0Tot = 0;
		int loc_14_2Tot = 0;
		int loc_15_1Tot = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			player.pickLocation(board.getTargets());
			BoardCell selected = board.getCells().get(board.calcIndex(player.getLocationX(), player.getLocationY()));
			if (selected == board.getCells().get(board.calcIndex(12, 0))){
				loc_12_0Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(14, 2))){
				loc_14_2Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(15, 1))){
				loc_15_1Tot++;
			}
			else{
				fail("Invalid target selected");
			}
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, loc_12_0Tot + loc_14_2Tot + loc_15_1Tot);
		// Ensure each target was selected more than once
		assertTrue(loc_12_0Tot > 10);
		assertTrue(loc_14_2Tot > 10);
		assertTrue(loc_15_1Tot > 10);
		
		//Location with no rooms in target, just 5 targets
		board.calcTargets(board.calcIndex(5, 0), 3);
		int loc_8_0Tot = 0;
		int loc_5_3Tot = 0;
		int loc_6_0Tot = 0;
		int loc_6_2Tot = 0;
		int loc_5_1Tot = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			player.pickLocation(board.getTargets());
			BoardCell selected = board.getCells().get(board.calcIndex(player.getLocationX(), player.getLocationY()));
			if (selected == board.getCells().get(board.calcIndex(8, 0))){
				loc_8_0Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(5, 3))){
				loc_5_3Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(6, 0))){
				loc_6_0Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(6, 2))){
				loc_6_2Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(5, 1))){
				loc_5_1Tot++;
			}
			else{
				fail("Invalid target selected");
			}
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, loc_8_0Tot + loc_5_3Tot + loc_6_0Tot + loc_6_2Tot + loc_5_1Tot);
		// Ensure each target was selected more than once
		assertTrue(loc_8_0Tot > 10);
		assertTrue(loc_5_3Tot > 10);
		assertTrue(loc_6_0Tot > 10);
		assertTrue(loc_6_2Tot > 10);
		assertTrue(loc_5_1Tot > 10);
	}
	
	//Test a random choice is made when the room is the last visited
	@Test
	public void testTargetRandomSelectionRoom(){
		ComputerPlayer player = new ComputerPlayer();
		//Test1
		// Pick a location with last visited room in target, six targets
		board.calcTargets(board.calcIndex(4, 4), 2);
		int loc_4_3Tot = 0;
		int loc_4_6Tot = 0;
		int loc_3_5Tot = 0;
		int loc_6_4Tot = 0;
		int loc_5_3Tot = 0;
		int loc_5_5Tot = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			//Set the last room visited to C
			player.setLastRoomVisited('C');
			player.pickLocation(board.getTargets());
			BoardCell selected = board.getCells().get(board.calcIndex(player.getLocationX(), player.getLocationY()));
			if (selected == board.getCells().get(board.calcIndex(4, 3))){
				loc_4_3Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(4, 6))){
				loc_4_6Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(3, 5))){
				loc_3_5Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(6, 4))){
				loc_6_4Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(5, 3))){
				loc_5_3Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(5, 5))){
				loc_5_5Tot++;
			}
			else{
				fail("Invalid target selected");
			}
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, loc_4_3Tot + loc_4_6Tot + loc_3_5Tot + loc_6_4Tot + loc_5_3Tot + loc_5_5Tot);
		// Ensure each target was selected more than once
		assertTrue(loc_4_3Tot > 10);
		assertTrue(loc_4_6Tot > 10);
		assertTrue(loc_3_5Tot > 10);
		assertTrue(loc_6_4Tot > 10);
		assertTrue(loc_5_3Tot > 10);
		assertTrue(loc_5_5Tot > 10);
		
		//Test2
		// Pick a location with last visited room in target, six targets
		board.calcTargets(board.calcIndex(5, 8), 2);
		int loc_5_6Tot = 0;
		int loc_6_7Tot = 0;
		int loc_4_8Tot = 0;
		int loc_7_8Tot = 0;
		int loc_6_9Tot = 0;
		int loc_5_10Tot = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			//Set the last room visited to C
			player.setLastRoomVisited('R');
			player.pickLocation(board.getTargets());
			BoardCell selected = board.getCells().get(board.calcIndex(player.getLocationX(), player.getLocationY()));
			if (selected == board.getCells().get(board.calcIndex(5, 6))){
				loc_5_6Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(6, 7))){
				loc_6_7Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(4, 8))){
				loc_4_8Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(7, 8))){
				loc_7_8Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(6, 9))){
				loc_6_9Tot++;
			}
			else if (selected == board.getCells().get(board.calcIndex(5, 10))){
				loc_5_10Tot++;
			}
			else{
				fail("Invalid target selected");
			}
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, loc_5_6Tot + loc_6_7Tot + loc_4_8Tot + loc_7_8Tot + loc_6_9Tot + loc_5_10Tot);
		// Ensure each target was selected more than once
		assertTrue(loc_5_6Tot > 10);
		assertTrue(loc_6_7Tot > 10);
		assertTrue(loc_4_8Tot > 10);
		assertTrue(loc_7_8Tot > 10);
		assertTrue(loc_6_9Tot > 10);
		assertTrue(loc_5_10Tot > 10);
	}
	
	//Test disproving a suggestion
	@Test
	public void testDisprovingSuggestion(){
		//create a player
		Player player = new Player();
		//Deal the six cards to the player
		player.getMyCards().add(mustardCard);
		player.getMyCards().add(mrGreenCard);
		player.getMyCards().add(leadPipeCard);
		player.getMyCards().add(knifeCard);
		player.getMyCards().add(hallCard);
		player.getMyCards().add(kitchenCard);
		
	}
	
	@Test
	public void testMakingSuggestion(){
		
	}

}
