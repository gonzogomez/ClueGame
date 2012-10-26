package clueGame;

import clueGame.RoomCell.DoorDirection;

public abstract class BoardCell {
	private int row;
	private int column;
	
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}

	public boolean isDoorway( ) { //10/21
		return false;
}
	
//	//Our Code commented out 10/21
//	public boolean isDoorway(RoomCell room) {
//		if(room.getDoorDirection() == null) {
//			return false;
//		} else {
//			return true;
//		}
//	}
	
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	//TODO Add abstract method called draw
}
