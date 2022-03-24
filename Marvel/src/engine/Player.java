package engine;

import java.util.ArrayList;
import model.world.Champion;

public class Player {
	
	private String name;
	private Champion leader;
	ArrayList<Champion> team;
	
	public Player(String name) {
		this.name = name;
		this.team = new ArrayList<Champion>(); //  makes sense for a player to start with empty team list then he selects
	}

	public Champion getLeader() {
		return leader;
	}

	public void setLeader(Champion leader) {
		this.leader = leader;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Champion> getTeam() {
		return team;
	}
	
	
}
