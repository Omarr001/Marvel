package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Disarm;
import model.effects.Dodge;
import model.effects.Effect;
import model.effects.Embrace;
import model.effects.PowerUp;
import model.effects.Root;
import model.effects.Shield;
import model.effects.Shock;
import model.effects.Silence;
import model.effects.SpeedUp;
import model.effects.Stun;
//import model.effects.EffectType;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;
import model.world.Hero;
import model.world.Villain;

public class Game {
	
	private Player firstPlayer;
	private Player secondPlayer;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private Object[][] board;
	private static ArrayList<Champion> availableChampions = new ArrayList<Champion>();
	private static ArrayList<Ability> availableAbilities = new ArrayList<Ability>();
	private PriorityQueue turnOrder;
	private static final int BOARDHEIGHT = 5;
	private static final int BOARDWIDTH = 5;
	
	public Game(Player first , Player second) {
		this.firstPlayer = first;
		this.secondPlayer = second;
		this.firstLeaderAbilityUsed = false;
		this.secondLeaderAbilityUsed = false;
		this.board = new Object[BOARDHEIGHT][BOARDWIDTH];
		this.turnOrder = new PriorityQueue(6);
		
		placeChampions();
		placeCovers();
		
		// should also distribute both player’s champions on the board and distributes the covers
	}
	
	private void placeChampions() {
		
		ArrayList<Champion> p1 = firstPlayer.getTeam();
		ArrayList<Champion> p2= secondPlayer.getTeam();
		int j = 0;
		
		for(int i = 1; i < 4; i++) {
			Point p = new Point(0,i);
			board[0][i] = p1.get(j);
			p1.get(j).setLocation(p);
			j++;
		}
		j = 0;
		
		for(int i = 1; i < 4; i++) {
			Point p = new Point(5,i);
			board[4][i] = p2.get(j);
			p2.get(j).setLocation(p);
			j++;
		}
		
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
				board[x][y] = new Cover(x,y);
				i++;
			}
		}
	}
	
	public static void loadChampions(String filePath) throws Exception{ // might need to call other load first to be able to add abilities to champions
		
		String line = "";
		String[][] ch = new String[15][11];
		try (BufferedReader br = new BufferedReader(new FileReader("Champions.csv"))) {
			int i = 0;
			while(i < 15 && (line = br.readLine()) != null) {
				ch[i] = line.split(",");
				i++;
			}
		}
		
		for(int i = 0; i < ch.length; i++) {
			for(int j = 0; j < ch[i].length; j++) {
				
				switch(ch[i][0].charAt(0)) {
				
				case 'H':
					Hero h = new Hero(ch[i][1], Integer.parseInt(ch[i][2]),Integer.parseInt(ch[i][3]) ,
							Integer.parseInt(ch[i][4]), Integer.parseInt(ch[i][5]), Integer.parseInt(ch[i][6]), 
							Integer.parseInt(ch[i][7]));
					availableChampions.add(h);
					
					break;
					
				case 'V':
					Villain v = new Villain(ch[i][1], Integer.parseInt(ch[i][2]),Integer.parseInt(ch[i][3]) ,
							Integer.parseInt(ch[i][4]), Integer.parseInt(ch[i][5]), Integer.parseInt(ch[i][6]), 
							Integer.parseInt(ch[i][7]));
					availableChampions.add(v);
					
					break;
					
				default:
					AntiHero a = new AntiHero(ch[i][1], Integer.parseInt(ch[i][2]),Integer.parseInt(ch[i][3]) ,
							Integer.parseInt(ch[i][4]), Integer.parseInt(ch[i][5]), Integer.parseInt(ch[i][6]), 
							Integer.parseInt(ch[i][7]));
					availableChampions.add(a);
					
					break;
				}
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
		
		for(int i = 0; i < ab.length; i++) {
			for(int j = 0; j < ab[i].length; j++) {
				
				AreaOfEffect a;
				switch(ab[i][5]) {
				
				case "SELFTARGET":
					a = AreaOfEffect.SELFTARGET;
					break;
				case "SINGLETARGET":
					a = AreaOfEffect.SINGLETARGET;
					break;
				case "TEAMTARGET":
					a = AreaOfEffect.TEAMTARGET;
					break;
				case "DIRECTIONAL":
					a = AreaOfEffect.DIRECTIONAL;
					break;	
				default:
					a = AreaOfEffect.SURROUND;
					break;
				}
				
				switch(ab[i][0]) {
				
				case "DMG":
					
					DamagingAbility d = new DamagingAbility(ab[i][1] , Integer.parseInt(ab[i][2]), 
							Integer.parseInt(ab[i][4]) , Integer.parseInt(ab[i][3]), a, Integer.parseInt(ab[i][6]), 
							Integer.parseInt(ab[i][7]));
					availableAbilities.add(d);
					break;
				case "HEL":
					
					HealingAbility h = new HealingAbility(ab[i][1] , Integer.parseInt(ab[i][2]), 
							Integer.parseInt(ab[i][4]) , Integer.parseInt(ab[i][3]), a, Integer.parseInt(ab[i][6]), 
							Integer.parseInt(ab[i][7]));
					availableAbilities.add(h);
					break;
				default:
					Effect e;
					
					switch(ab[i][7]) {
					
					case "Disarm":
						e = new Disarm(Integer.parseInt(ab[i][8]));
						break;
					case "PowerUp":
						e = new PowerUp(Integer.parseInt(ab[i][8]));
						break;
					case "Shield":
						e = new Shield(Integer.parseInt(ab[i][8]));
						break;
					case "Silence":
						e = new Silence(Integer.parseInt(ab[i][8]));
						break;
					case "SpeedUp":
						e = new SpeedUp(Integer.parseInt(ab[i][8]));
						break;
					case "Root":
						e = new Root(Integer.parseInt(ab[i][8]));
						break;
					case "Shock":
						e = new Shock(Integer.parseInt(ab[i][8]));
						break;
					case "Dodge":
						e = new Dodge(Integer.parseInt(ab[i][8]));
						break;
					case "Stun":
						e = new Stun(Integer.parseInt(ab[i][8]));
						break;
					default:
						e = new Embrace(Integer.parseInt(ab[i][8]));
						break;
					}
					
					CrowdControlAbility c = new CrowdControlAbility(ab[i][1] , Integer.parseInt(ab[i][2]), 
							Integer.parseInt(ab[i][4]) , Integer.parseInt(ab[i][3]), a, Integer.parseInt(ab[i][6]), e);
					availableAbilities.add(c);
				}
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
