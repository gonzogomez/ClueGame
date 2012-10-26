package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import clueGame.RoomCell.DoorDirection;

public class Board {
	
	//* Part II - Code ***************************************************************
	//Variables
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private int numRows;
	private int numColumns;
	private int boardSize;
	private Map<Integer, LinkedList<Integer>> adjMtx;
	private Set<BoardCell> targets;
	private boolean[] visitedPoints;
	private static final int START_NAME = 3;
	private RoomCell roomTemp;
	private BoardCell boardcell;
	private ArrayList<Card> cards;
	private ArrayList<ComputerPlayer> computerPlayers;
	private HumanPlayer humanPlayer;
	private Solution solution;
	private int numPeople;
	private int numRooms;
	private int numWeapons;

	//Constructor - Calls loadConfigFiles() method.
	public Board() {  
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
		rooms = new HashMap<Character, String>();
		cells = new ArrayList<BoardCell>();
		loadConfigFiles();
		boardSize = numRows*numColumns;
		calcAdjacencies();
	}

	//Takes path of the legend and CSV file and calls their helper functions.
	public void loadConfigFiles() {
		String legend = "OtherFiles/Clue Game - Legend";  			// for linux: "OtherFiles/Clue Game - Legend"
		String csvFile = "OtherFiles/ClueGameBoard (Updated).csv"; // for linux: "OtherFiles/ClueGameBoard (Updated).csv"
		try{
			loadLegend(legend);
			loadCSV(csvFile);
		}
		catch(FileNotFoundException e) {
			System.out.println("Can't find the file, check the path.");
		}
		catch(BadConfigFormatException e) {
			System.out.println(e);
		}
	}


	//Iterates through the legend and loads the Initial and Room Name for each room into a HashMap called rooms.
	private void loadLegend(String fname) throws BadConfigFormatException, FileNotFoundException {
		String inString = null;
		Character initial = null;  		// Single letter initials of the space
		String name = null;				// Full name of the room

		FileReader reader = null;
		
		reader = new FileReader(fname);

		Scanner in = new Scanner(reader);
		while(in.hasNextLine()) {
			inString = in.nextLine();
			initial = inString.charAt(0);  //do a toupper
			//if second char is not a comma, throw exception
			if(!(inString.charAt(1) == ',')){
				throw new BadConfigFormatException("comma bad");
				}

			name = inString.substring(START_NAME);
			//System.out.println("Char: " + initial + " name: " + name);
			rooms.put(initial, name);
		}
		//close resources
		in.close();		
	}
	
	//new loadCSV:
	private void loadCSV(String fname) throws BadConfigFormatException, FileNotFoundException{
		FileReader reader = null;
		reader = new FileReader(fname);
		Scanner in = new Scanner(reader);
		int rows = 0;
		int columns = 0;
		while(in.hasNextLine()) {
			String inString = in.next();
			String[] tokens = inString.split(",");
			columns = tokens.length;
			
			for(int i = 0; i < tokens.length; i++) {
				BoardCell cell = null;
				if(tokens[i].equalsIgnoreCase("W")) {
					cell = new WalkwayCell();
				} else {
					cell = new RoomCell(tokens[i], numRows, i);
				}
				cells.add(cell);
			}
			rows++;
		}
	numColumns = columns;
	numRows = rows;
	in.close();	
	}
	
	//Calculates the board index, given a row and column number.
	public int calcIndex(int row, int column) {
		return ((row * numColumns) + (column));
	}
	
	//Calculates the RoomCell, given a row and column (Works with our test)
	public RoomCell getRoomCellAt(int row, int column) {
		int index = calcIndex(row, column);
		roomTemp = (RoomCell) cells.get(index);
		return  roomTemp;
	}
	
	//Duplicate of previous test except can pass in an index instead of row and column.
	public BoardCell getRoomCellAt(int index) {
		boardcell = cells.get(index);
		return  boardcell;
	}
//***************************************************************************
	
//* IntBoard Code ***********************************************************
	
	//Calculates the adjacency lists for each grid space.
	public void calcAdjacencies() {
		for(int i=0; i < boardSize; i++) {
			LinkedList<Integer> list = new LinkedList<Integer>();
			BoardCell bc = cells.get(i);
			if (bc.isWalkway() || bc.isDoorway()) {
				//Checking move upwards
				if(i - numColumns  >= 0) {
					BoardCell b = cells.get(i - numColumns);
					if(b.isWalkway()) {
						list.add(i-numColumns);
					}

					if(b.isDoorway() == true) {
						RoomCell r = (RoomCell) b;
						if (r.getDoorDirection() == RoomCell.DoorDirection.DOWN) 
							list.add(i-numColumns);
					}
				}
				//Checking move to left
				//(i % COLS) returns the column number
				if(i % numColumns != 0) {
					BoardCell b = cells.get(i - 1);
					if(b.isWalkway()) {
						list.add(i-1);
					}
					if(b.isDoorway() == true) {
						RoomCell r = (RoomCell) b;
						if (r.getDoorDirection() == RoomCell.DoorDirection.RIGHT) 
							list.add(i-1);
					}
				}
				//Checking move to right
				if(i % numColumns != (numColumns - 1)) {
					BoardCell b = cells.get(i + 1);
					if(b.isWalkway()) {
						list.add(i+1);
					}
					if(b.isDoorway() == true) {
						RoomCell r = (RoomCell) b;
						if (r.getDoorDirection() == RoomCell.DoorDirection.LEFT) 
							list.add(i+1);
					}
				}
				//Checking move downwards
				if(i + numColumns < boardSize) {
					BoardCell b = cells.get(i + numColumns);
					if(b.isWalkway()) {
						list.add(i+numColumns);
					}
					if(b.isDoorway() == true) {
						RoomCell r = (RoomCell) b;
						if (r.getDoorDirection() == RoomCell.DoorDirection.UP) 
							list.add(i+numColumns);
					}
				}
			}
			adjMtx.put(i, list);
		}
	}
	
	//Determines possible move locations based on a starting location and the die roll
	public void calcTargets(int startLocation, int numberOfSteps) {
		targets = new HashSet<BoardCell>();
		LinkedList<Integer> path = new LinkedList<Integer>();
		LinkedList<Integer> list = new LinkedList<Integer>();
		
		//Sets every point to unseen
		visitedPoints = new boolean[boardSize];				
		for(int i = 0; i < boardSize; i++){
			visitedPoints[i] = false;
		}
		
		visitedPoints[startLocation] = true;
		list = getAdjList(startLocation);
		calcTargetsHelper(path, list, numberOfSteps);
	}
	
	//The helper function for calcTargets (contains a recursive call).
	public void calcTargetsHelper(LinkedList<Integer> path, LinkedList<Integer> adjList, int numberOfSteps) {
		for(Integer i : adjList) {
			if(visitedPoints[i] == false) {
				visitedPoints[i] = true;
				path.addLast(i);
				if(path.size() == numberOfSteps || cells.get(i).isDoorway()) {					
					BoardCell b = cells.get(i);
					targets.add(b);
				} else {
					LinkedList<Integer> recursiveList = new LinkedList<Integer>(); 
					recursiveList = getAdjList(i);
					calcTargetsHelper(path, recursiveList, numberOfSteps);
				}
				path.removeLast();
				visitedPoints[i] = false;
			}
		}
	}
	
	
	//Gets the list of targets in the form of a TreeSet
	public Set<BoardCell> getTargets() {
		return targets;
	}

	//Returns the adjacency list for a given index
	public LinkedList<Integer> getAdjList(int index) {
		LinkedList<Integer> b = new LinkedList<Integer>();
		b = adjMtx.get(index);
		return b;
	}
	
//*Clue players methods***********************************************************
	public void selectAnswer(){

	}

	public void deal(ArrayList<String> cardList){

	}

	public void deal(){

	}

	public boolean checkAccusation(String person, String room, String weapon){
		return false;
	}

	public void handleSuggestion(String person, String weapon, String room){

	}
//*Getters and Setters***********************************************************
	
	
	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	public int getNumPeople() {
		return numPeople;
	}

	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}

	public int getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	public int getNumWeapons() {
		return numWeapons;
	}

	public void setNumWeapons(int numWeapons) {
		this.numWeapons = numWeapons;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public ArrayList<ComputerPlayer> getComputerPlayers() {
		return computerPlayers;
	}

	public void setComputerPlayers(ArrayList<ComputerPlayer> computerPlayers) {
		this.computerPlayers = computerPlayers;
	}

	public HumanPlayer getHumanPlayer() {
		return humanPlayer;
	}

	public void setHumanPlayer(HumanPlayer humanPlayer) {
		this.humanPlayer = humanPlayer;
	}

	public void setCells(ArrayList<BoardCell> cells) {
		this.cells = cells;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public void setRooms(Map<Character, String> rooms) {
		this.rooms = rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}
//******************************************************************

}
