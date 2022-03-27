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
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private PriorityQueue turnOrder;
	private static final int BOARDHEIGHT = 5;
	private static final int BOARDWIDTH = 5;
	
	public Game(Player first , Player second) {
		this.firstPlayer = first;
		this.secondPlayer = second;
		this.firstLeaderAbilityUsed = false;
		this.secondLeaderAbilityUsed = false;
		this.board = new Object[BOARDHEIGHT][BOARDWIDTH];
		availableChampions = new ArrayList<Champion>();
		availableAbilities = new ArrayList<Ability>();
		this.turnOrder = new PriorityQueue(6);
		
		placeChampions();
		placeCovers();
		
		// should also distribute both playerâ€™s champions on the board and distributes the covers
	}
	
	private void placeChampions() {
		
		ArrayList<Champion> p1 = firstPlayer.getTeam();
		ArrayList<Champion> p2= secondPlayer.getTeam();
		
		for(int i = 0; i < p1.size(); i++) {
			Point p = new Point(0,i + 1);
			board[0][i + 1] = p1.get(i);
			p1.get(i).setLocation(p);
		}
		
		for(int i = 0; i < p2.size(); i++) {
			Point p = new Point(5 , i + 1);
			board[4][i + 1] = p2.get(i);
			p2.get(i).setLocation(p);
		}
		
		// this is assuming player1 champions are placed first in the queue
		
		// will loop over the board array board[0][1 --> 3] and board[4][1 --> 3] to add champions
		// kept empty till we figure out from which data structure do we draw the champions from
	}
	
	private void placeCovers() {
		
		Random ran = new Random();
		
		int i = 0;
		while(i < 5) {
			int x = ran.nextInt(5);
			int y = ran.nextInt(3) + 1;
			if(board[y][x] == null) {
				board[y][x] = new Cover(x,y);
				i++;
			}
		}
	}
	
	public static void loadChampions(String filePath) throws Exception{ 
		// might need to call other load first to be able to add abilities to champions
			
		int i = 0;
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		
		while(br.ready()) {
			line = br.readLine();
			String[] ch = line.split(",");
			switch(ch[0].charAt(0)) {
			
			case 'H':
				Hero h = new Hero(ch[1], Integer.parseInt(ch[2]),Integer.parseInt(ch[3]) ,
						Integer.parseInt(ch[4]), Integer.parseInt(ch[5]), Integer.parseInt(ch[6]), 
						Integer.parseInt(ch[7]));
				
				for(int j = 0; j < 3; j++) {
					h.getAbilities().add(availableAbilities.get(i));
					i++;
				}
				availableChampions.add(h);
				
				break;
				
			case 'V':
				Villain v = new Villain(ch[1], Integer.parseInt(ch[2]),Integer.parseInt(ch[3]) ,
						Integer.parseInt(ch[4]), Integer.parseInt(ch[5]), Integer.parseInt(ch[6]), 
						Integer.parseInt(ch[7]));
				
				for(int j = 0; j < 3; j++) {
					v.getAbilities().add(availableAbilities.get(i));
					i++;
				}
				availableChampions.add(v);
				
				break;
				
			default:
				AntiHero a = new AntiHero(ch[1], Integer.parseInt(ch[2]),Integer.parseInt(ch[3]) ,
						Integer.parseInt(ch[4]), Integer.parseInt(ch[5]), Integer.parseInt(ch[6]), 
						Integer.parseInt(ch[7]));
				
				for(int j = 0; j < 3; j++) {
					a.getAbilities().add(availableAbilities.get(i));
					i++;
				}
				availableChampions.add(a);
				
				break;
			}
		}	
		br.close();
	}
	
	public static void loadAbilities(String filePath) throws Exception{
		
		//int i = 0;
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(filePath)); 
		
		while(br.ready()) {
			line = br.readLine();
			String[] ab = line.split(",");
			AreaOfEffect a = null;
			switch(ab[5]) {

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
			
			switch(ab[0]) {
			
			case "DMG":
				
				DamagingAbility d = new DamagingAbility(ab[1] , Integer.parseInt(ab[2]), 
						Integer.parseInt(ab[4]) , Integer.parseInt(ab[3]), a, Integer.parseInt(ab[6]), 
						Integer.parseInt(ab[7]));
				availableAbilities.add(d);
				//availableChampions.get(i / 3).getAbilities().add(d);
				break;
			case "HEL":
				
				HealingAbility h = new HealingAbility(ab[1] , Integer.parseInt(ab[2]), 
						Integer.parseInt(ab[4]) , Integer.parseInt(ab[3]), a, Integer.parseInt(ab[6]), 
						Integer.parseInt(ab[7]));
				availableAbilities.add(h);
				//availableChampions.get(i / 3).getAbilities().add(h);
				break;
			default:
				Effect e = null;
				
				switch(ab[7]) {
				
				case "Disarm":
					e = new Disarm(Integer.parseInt(ab[8]));
					break;
				case "PowerUp":
					e = new PowerUp(Integer.parseInt(ab[8]));
					break;
				case "Shield":
					e = new Shield(Integer.parseInt(ab[8]));
					break;
				case "Silence":
					e = new Silence(Integer.parseInt(ab[8]));
					break;
				case "SpeedUp":
					e = new SpeedUp(Integer.parseInt(ab[8]));
					break;
				case "Root":
					e = new Root(Integer.parseInt(ab[8]));
					break;
				case "Shock":
					e = new Shock(Integer.parseInt(ab[8]));
					break;
				case "Dodge":
					e = new Dodge(Integer.parseInt(ab[8]));
					break;
				case "Stun":
					e = new Stun(Integer.parseInt(ab[8]));
					break;
				default:
					e = new Embrace(Integer.parseInt(ab[8]));
					break;
				}
				
				CrowdControlAbility c = new CrowdControlAbility(ab[1] , Integer.parseInt(ab[2]), 
						Integer.parseInt(ab[4]) , Integer.parseInt(ab[3]), a, Integer.parseInt(ab[6]), e);
				availableAbilities.add(c);
				//availableChampions.get(i / 3).getAbilities().add(c);
			}
			
		}
		br.close();
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