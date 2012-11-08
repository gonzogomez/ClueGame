package clueGame;

import java.awt.Graphics;

public class RoomCell extends BoardCell {
	
	public enum DoorDirection { UP, DOWN, LEFT, RIGHT, NONE };
	
	private DoorDirection doorDirection;
	private char roomInitial;
	private boolean isDoor; //added 10/21

	
	public RoomCell(String inString, int row, int col) { //added 10/21
		if(inString.length() == 2) {
			setDoorWay(inString);
			isDoor = true;
			
		} else {
			setDoorDirection(doorDirection.NONE);
			isDoor = false;
		}
		setRow(row);
		setColumn(col);
		setRoomInitial(inString);
	}
	
	@Override
	public boolean isRoom() {
		return true;
	}
	
	public boolean isDoorway() {  //added 10/21, 
		//System.out.println("got a door");
		return isDoor;
	}
	//TODO Add override for BoardCell-->draw function

	
	
	
	
//*Getters and Setters***************************************************
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}


	public char getInitial() {
		return roomInitial;
	}


	public void setRoomInitial(char roomInitial) {
		this.roomInitial = roomInitial;
	}
	
	public void setRoomInitial(String input) { //added 10/21
		this.roomInitial = input.charAt(0);
	}	
	
	// added 10/21
	public void setDoorWay(String inString) {
		this.isDoor = true;
		char dir = inString.charAt(1);
		if(dir == 'R')
			setDoorDirection(doorDirection.RIGHT);
		if(dir == 'L')
			setDoorDirection(doorDirection.LEFT);
		if(dir == 'U')
			setDoorDirection(doorDirection.UP);
		if(dir == 'D')
			setDoorDirection(doorDirection.DOWN);
		
	}
//**********************************************************************

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
