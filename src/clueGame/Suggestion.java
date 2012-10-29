package clueGame;

public class Suggestion {
	private String name;
	private String weapon;
	private String room;
	
	
	
	public Suggestion() {
		super();
	}
	public Suggestion(String name, String weapon, String room) {
		super();
		this.name = name;
		this.weapon = weapon;
		this.room = room;
	}
	//Getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWeapon() {
		return weapon;
	}
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	
}
