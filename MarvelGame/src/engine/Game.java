package engine;

import java.util.ArrayList;

import model.abilities.Ability;
import model.world.Champion;

public class Game {
	
	private Player firstPlayer;
	private Player secondPlayer;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private Object[][] board;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private PriorityQueue turnOrder;
	private static final int BOARDHEIGHT = 0;
	private static final int BOARDWIDTH = 0; // width and height are  with 0 for now
	
	public Game(Player first , Player second) {
		this.firstPlayer = first;
		this.secondPlayer = second;
		
		// should also distribute both player’s champions on the board and distributes the covers
	}
	
	
	
}
