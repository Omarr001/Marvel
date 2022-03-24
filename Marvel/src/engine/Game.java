package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import model.abilities.Ability;
import model.world.Champion;
import model.world.Cover;

public class Game {
	
	private Player firstPlayer;
	private Player secondPlayer;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private Object[][] board;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private PriorityQueue turnOrder;
	private static final int BOARDHEIGHT = 5;
	private static final int BOARDWIDTH = 5;
	
	public Game(Player first , Player second) {
		this.firstPlayer = first;
		this.secondPlayer = second;
		
		placeChampions();
		placeCovers();
		
		// should also distribute both player’s champions on the board and distributes the covers
	}
	
	private void placeChampions() {
		
		for(int i = 1; i < 4; i++) 
			board[0][i] = turnOrder.remove();
		
		for(int i = 1; i < 4; i++)
			board[4][i] = turnOrder.remove();
		
		// this is assuming player1 champions are placed first in the queue
		
		// will loop over the board array board[0][1 --> 3] and board[4][1 --> 3] to add champions
		// kept empty till we figure out from which data structure do we draw the champions from
	}
	
	private void placeCovers() {
		
		Random ran = new Random();
		/*for(int i = 0; i < 5; i++) {
			int x = ran.nextInt(5);
			int y = ran.nextInt(3) + 1;
			board[y][x] = new Cover(0,0); // kept 0 for now till we figure out the dimensions of the board on the screen
		}*/
		
		int i = 0;
		while(i < 5) {
			int x = ran.nextInt(5);
			int y = ran.nextInt(3) + 1;
			if(board[x][y] == null) {
				board[x][y] = new Cover(0,0);
				i++;
			}
		}
	}
	
	public static void loadChampions(String filePath) throws Exception{
		
		String line = "";
		String[][] ch = new String[15][11];
		try (BufferedReader br = new BufferedReader(new FileReader("Champions.csv"))) {
			int i = 0;
			while(i < 15 && (line = br.readLine()) != null) {
				ch[i] = line.split(",");
				i++;
			}
		}
		
		
		
	}
	
	public static void loadAbilities(String filePath) throws Exception{
		
		String line = "";
		String[][] ab = new String[45][9];
		try (BufferedReader br = new BufferedReader(new FileReader("Abilities.csv"))) {
			int i = 0;
			while(i < 45 && (line = br.readLine()) != null) {
				ab[i] = line.split(",");
				i++;
			}
		}
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}

	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}

	public Object[][] getBoard() {
		return board;
	}

	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}

	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}

	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}

	public static int getBoardheight() {
		return BOARDHEIGHT;
	}

	public static int getBoardwidth() {
		return BOARDWIDTH;
	}
	
	// in the load methods we may need to assign the filePath the name of csv files
	
	
	// still need to add heros and abilities into their arraylists
	
}
