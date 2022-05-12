package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
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
import model.world.AntiHero;
import model.world.Champion;
import model.world.Condition;
import model.world.Cover;
import model.world.Damageable;
//import model.world.Damageable;
import model.world.Direction;
import model.world.Hero;
import model.world.Villain;

public class Game {
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private Player firstPlayer;
	private Player secondPlayer;
	private Object[][] board;
	private PriorityQueue turnOrder;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private final static int BOARDWIDTH = 5;
	private final static int BOARDHEIGHT = 5;

	public Game(Player first, Player second) {
		firstPlayer = first;
		secondPlayer = second;
		availableChampions = new ArrayList<Champion>();
		availableAbilities = new ArrayList<Ability>();
		board = new Object[BOARDWIDTH][BOARDHEIGHT];
		turnOrder = new PriorityQueue(6);
		placeChampions();
		placeCovers();
		prepareChampionTurns();
	}

	public static void loadAbilities(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Ability a = null;
			AreaOfEffect ar = null;
			switch (content[5]) {
			case "SINGLETARGET":
				ar = AreaOfEffect.SINGLETARGET;
				break;
			case "TEAMTARGET":
				ar = AreaOfEffect.TEAMTARGET;
				break;
			case "SURROUND":
				ar = AreaOfEffect.SURROUND;
				break;
			case "DIRECTIONAL":
				ar = AreaOfEffect.DIRECTIONAL;
				break;
			case "SELFTARGET":
				ar = AreaOfEffect.SELFTARGET;
				break;

			}
			Effect e = null;
			if (content[0].equals("CC")) {
				switch (content[7]) {
				case "Disarm":
					e = new Disarm(Integer.parseInt(content[8]));
					break;
				case "Dodge":
					e = new Dodge(Integer.parseInt(content[8]));
					break;
				case "Embrace":
					e = new Embrace(Integer.parseInt(content[8]));
					break;
				case "PowerUp":
					e = new PowerUp(Integer.parseInt(content[8]));
					break;
				case "Root":
					e = new Root(Integer.parseInt(content[8]));
					break;
				case "Shield":
					e = new Shield(Integer.parseInt(content[8]));
					break;
				case "Shock":
					e = new Shock(Integer.parseInt(content[8]));
					break;
				case "Silence":
					e = new Silence(Integer.parseInt(content[8]));
					break;
				case "SpeedUp":
					e = new SpeedUp(Integer.parseInt(content[8]));
					break;
				case "Stun":
					e = new Stun(Integer.parseInt(content[8]));
					break;
				}
			}
			switch (content[0]) {
			case "CC":
				a = new CrowdControlAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), e);
				break;
			case "DMG":
				a = new DamagingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
				break;
			case "HEL":
				a = new HealingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
				break;
			}
			availableAbilities.add(a);
			line = br.readLine();
		}
		br.close();
	}

	public static void loadChampions(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Champion c = null;
			switch (content[0]) {
			case "A":
				c = new AntiHero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;

			case "H":
				c = new Hero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			case "V":
				c = new Villain(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			}

			c.getAbilities().add(findAbilityByName(content[8]));
			c.getAbilities().add(findAbilityByName(content[9]));
			c.getAbilities().add(findAbilityByName(content[10]));
			availableChampions.add(c);
			line = br.readLine();
		}
		br.close();
	}

	private static Ability findAbilityByName(String name) {
		for (Ability a : availableAbilities) {
			if (a.getName().equals(name))
				return a;
		}
		return null;
	}

	public void placeCovers() {
		int i = 0;
		while (i < 5) {
			int x = ((int) (Math.random() * (BOARDWIDTH - 2))) + 1;
			int y = (int) (Math.random() * BOARDHEIGHT);

			if (board[x][y] == null) {
				board[x][y] = new Cover(x, y);
				i++;
			}
		}

	}
	//(Omar 4/5)
	public void prepareChampionTurns() {
		for(int i = 0; i < firstPlayer.getTeam().size(); i++) {
			if(!(firstPlayer.getTeam().get(i).getCondition().equals(Condition.KNOCKEDOUT))) {
				turnOrder.insert(firstPlayer.getTeam().get(i));
			}
		}
		for(int i = 0; i < secondPlayer.getTeam().size(); i++) {
			if(!(secondPlayer.getTeam().get(i).getCondition().equals(Condition.KNOCKEDOUT))) {
				turnOrder.insert(secondPlayer.getTeam().get(i));
			}
		}
		
	}
	// (Omar 4/5) this will return the champion in the front of the queue
	public Champion getCurrentChampion() {
		return (Champion) turnOrder.peekMin();
	}
	// (Omar 4/5) this is assuming that when a champion is knocked out it will be removed from the player's team
	public Player checkGameOver() {
		if(firstPlayer.getTeam().size() == 0)
			return secondPlayer;
		else if(secondPlayer.getTeam().size() == 0)
			return firstPlayer;
		return null;
	}
	//(Omar 4/5) added exception Omar (11/5)
	public void move(Direction d) throws UnallowedMovementException, NotEnoughResourcesException {
		Champion c = (Champion) turnOrder.peekMin();
		if(c.getCurrentActionPoints() < 1)
			throw new NotEnoughResourcesException();
		if(c.getCondition().equals(Condition.ROOTED))
			throw new UnallowedMovementException();
		Point tmp = new Point(c.getLocation().x , c.getLocation().y);
		c.setCurrentActionPoints(c.getCurrentActionPoints() - 1);
		if(d.equals(Direction.LEFT)) {
			if(tmp.y - 1 < 0 || board[tmp.x][tmp.y - 1] != null)
				throw new UnallowedMovementException();
			else {
				c.setLocation(new Point(tmp.x , tmp.y - 1));
				board[tmp.x][tmp.y - 1] = c;
				board[tmp.x][tmp.y] = null;
			}
		}
		else if(d.equals(Direction.RIGHT)) {
			if(tmp.y + 1 == BOARDWIDTH || board[tmp.x][tmp.y + 1] != null)
				throw new UnallowedMovementException();
			else {
				c.setLocation(new Point(tmp.x , tmp.y + 1));
				board[tmp.x][tmp.y + 1] = c;
				board[tmp.x][tmp.y] = null;
			}
		}
		else if(d.equals(Direction.DOWN)) {
			if(tmp.x - 1 < 0 || board[tmp.x - 1][tmp.y] != null)
				throw new UnallowedMovementException();
			else {
				c.setLocation(new Point(tmp.x - 1 , tmp.y));
				board[tmp.x - 1][tmp.y] = c;
				board[tmp.x][tmp.y] = null;
			}
		}
		else if(d.equals(Direction.UP)) {
			if(tmp.x + 1 == BOARDHEIGHT || board[tmp.x + 1][tmp.y] != null)
				throw new UnallowedMovementException();
			else {
				c.setLocation(new Point(tmp.x + 1 , tmp.y));
				board[tmp.x + 1][tmp.y] = c;
				board[tmp.x][tmp.y] = null;
			}
		}
		
	}
	//(Omar 7/5)
	public void attack(Direction d) throws ChampionDisarmedException, NotEnoughResourcesException, InvalidTargetException {
		Champion c = (Champion) this.getCurrentChampion();
		for(Effect e : c.getAppliedEffects()) { // exception of disarm
			if(e instanceof Disarm) {
				throw new ChampionDisarmedException();
			}
		}
		if(c.getCurrentActionPoints() < 2)
			throw new NotEnoughResourcesException();
		Point p = c.getLocation();
		boolean first = firstPlayer.getTeam().contains(c); //  boolean to indicate the team of the current champion
		boolean found = false; // boolean to indicate wether we found a valid targer or not while attacking
		if(d.equals(Direction.RIGHT)) {
			for(int i = p.y + 1; i < BOARDWIDTH; i++) {
				
				if(board[p.x][i] != null) {
					if(board[p.x][i] instanceof Cover) {
						Cover tmp = (Cover) board[p.x][i];
						coverAttack(c , tmp);
						if(tmp.getCurrentHP() == 0)
							board[p.x][i] = null;
						found = true;
						break;
					}
					else {
						Champion tmp = (Champion) board[p.x][i];
						if(first && !(firstPlayer.getTeam().contains(tmp)) || !first && firstPlayer.getTeam().contains(tmp)) {
							c2cAttack(c , tmp);
							found = true;
							break;
						}
					}
				}
			}
		}
		else if(d.equals(Direction.LEFT)) {
			for(int i = p.y - 1; i > -1; i--) {
				
				if(board[p.x][i] != null) {
					if(board[p.x][i] instanceof Cover) {
						Cover tmp = (Cover) board[p.x][i];
						coverAttack(c , tmp);
						if(tmp.getCurrentHP() == 0)
							board[p.x][i] = null;
						found = true;
						break;
					}
					else {
						Champion tmp = (Champion) board[p.x][i];
						if(first && !(firstPlayer.getTeam().contains(tmp)) || !first && firstPlayer.getTeam().contains(tmp)) {
							c2cAttack(c , tmp);
							found = true;
							break;
						}
					}
				}
			}
		}
		else if(d.equals(Direction.UP)) {
			for(int i = p.x + 1; i < BOARDHEIGHT; i++) {
				
				if(board[i][p.y] != null) {
					if(board[i][p.y] instanceof Cover) {
						Cover tmp = (Cover) board[i][p.y];
						coverAttack(c , tmp);
						if(tmp.getCurrentHP() == 0)
							board[i][p.y] = null;
						found = true;
						break;
					}
					else {
						Champion tmp = (Champion) board[i][p.y];
						if(first && !(firstPlayer.getTeam().contains(tmp)) || !first && firstPlayer.getTeam().contains(tmp)) {
							c2cAttack(c , tmp);
							found = true;
							break;
						}
					}
				}
			}
		}
		else {
			for(int i = p.x - 1; i > -1; i--) {
				
				if(board[i][p.y] != null) {
					if(board[i][p.y] instanceof Cover) {
						Cover tmp = (Cover) board[i][p.y];
						coverAttack(c , tmp);
						if(tmp.getCurrentHP() == 0)
							board[i][p.y] = null;
						found = true;
						break;
					}
					else {
						Champion tmp = (Champion) board[i][p.y];
						if(first && !(firstPlayer.getTeam().contains(tmp)) || !first && firstPlayer.getTeam().contains(tmp)) {
							c2cAttack(c , tmp);
							found = true;
							break;
						}
					}
				}
			}
		}
		
			c.setCurrentActionPoints(c.getCurrentActionPoints() - 2);
			
		
	}
	// carry out the attack on covers (helper for attack method) (Omar 7/5)
	public void coverAttack(Champion c , Cover tmp) {
		tmp.setCurrentHP(tmp.getCurrentHP() - c.getAttackDamage());
		c.setCurrentActionPoints(c.getCurrentActionPoints() - 2);
	}
	// carry out the attack on champions (hepler)
	public void c2cAttack(Champion c , Champion tmp) {
		double dodge = 0.0;
		for(Effect e : tmp.getAppliedEffects()) {
			if(e instanceof Dodge)
				dodge = Math.random();
			else if(e instanceof Shield) {
				e.remove(tmp);
				c.setCurrentActionPoints(c.getCurrentActionPoints() - 2);
				return;
			}
				
		}
		if(dodge < 0.5)
			
			if(special(c , tmp))
				specialAttack(c , tmp);
			else
				normalAttack(c , tmp);
	}

	// determines wether the attack between champions is special or not (helper for attack method) (Omar 7/5)
	public boolean special(Champion c , Champion tmp) {
		return ((c instanceof Hero) && (tmp instanceof Villain))
				|| ((tmp instanceof Hero) && (c instanceof Villain))
				|| ((c instanceof AntiHero) && !(tmp instanceof AntiHero))
				|| (!(c instanceof AntiHero) && (tmp instanceof AntiHero));
	}
	// carry out normal attacks between champions (helper for attack method)
	public void normalAttack(Champion c , Champion tmp) {
		tmp.setCurrentHP(tmp.getCurrentHP() - c.getAttackDamage());
		c.setCurrentActionPoints(c.getCurrentActionPoints() - 2);
		if(tmp.getCurrentHP() == 0) {
			tmp.setCondition(Condition.KNOCKEDOUT);
			board[tmp.getLocation().x][tmp.getLocation().y] = null;
			// not sure wether we should remove the knockedout champions from the players' teams or not
		}
	}
	// carry out special attacks between champions (helper for attack method) (Omar 7/5)
	public void specialAttack(Champion c , Champion tmp) {
		tmp.setCurrentHP( (int) (tmp.getCurrentHP() - c.getAttackDamage() * 1.5));
		c.setCurrentActionPoints(c.getCurrentActionPoints() - 2);
		if(tmp.getCurrentHP() == 0) {
			tmp.setCondition(Condition.KNOCKEDOUT);
			board[tmp.getLocation().x][tmp.getLocation().y] = null;
		}
	}
	// assuming the ability passed as parameter exists in the champion's ability array without checking
	public void castAbility(Ability a) throws NotEnoughResourcesException {
		Champion c = (Champion) getCurrentChampion();
		Point p1 = c.getLocation();
		//silence
		boolean first = firstPlayer.getTeam().contains(c);
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		if (c.getMana()<a.getManaCost())
			throw new NotEnoughResourcesException();
		if (a instanceof HealingAbility ){
			HealingAbility h = (HealingAbility)a;
			if (h.getCastArea().equals(AreaOfEffect.TEAMTARGET)){
				if (first){
					for(int i = 0 ; i<firstPlayer.getTeam().size();i++){
						//if (!firstPlayer.getTeam().get(i).getName().equals(c.getName())){
							Point p2 = firstPlayer.getTeam().get(i).getLocation();
							int distance = distance(p1,p2);
							if (distance<=a.getCastRange())
								targets.add(firstPlayer.getTeam().get(i));
							
						}
				
					h.execute(targets);
				}
				
				else{
					for(int i = 0 ; i<secondPlayer.getTeam().size();i++){
						//if (!secondPlayer.getTeam().get(i).getName().equals(c.getName())){
							Point p2 = secondPlayer.getTeam().get(i).getLocation();
							int distance = distance(p1,p2);
							if (distance<=a.getCastRange())
								targets.add(secondPlayer.getTeam().get(i));
							
						}
					
					h.execute(targets);	
				}
			}
			else if(h.getCastArea().equals(AreaOfEffect.SELFTARGET)){
				targets.add(c);
				h.execute(targets);

			}
			else if (h.getCastArea().equals(AreaOfEffect.SURROUND)){
				if (first){
					ArrayList<Champion> T= firstPlayer.getTeam();
					for (int i = 0 ; i <firstPlayer.getTeam().size(); i++){
						Champion temp = firstPlayer.getTeam().get(i);
						Point pt = temp.getLocation();
						
							if (pt.y==p1.y&&(pt.x==p1.x+1
									||pt.x==p1.x-1)
									||pt.x==p1.x&&(pt.y==p1.y-1
									||pt.y==p1.y+1)
									||pt.x==p1.x+1&&(pt.y==p1.y-1
									||pt.y==p1.y+1)
									||pt.x==p1.x-1&&(pt.y==p1.y-1||pt.y==p1.y+1))
								targets.add(temp);
							
						
						
					}
					h.execute(targets);
				}
				else{
					ArrayList<Champion> T= secondPlayer.getTeam();
					for (int i = 0 ; i <secondPlayer.getTeam().size(); i++){
						Champion temp = secondPlayer.getTeam().get(i);
						Point pt = temp.getLocation();
						
							if (pt.y==p1.y&&(pt.x==p1.x+1
									||pt.x==p1.x-1)
									||pt.x==p1.x&&(pt.y==p1.y-1
									||pt.y==p1.y+1)
									||pt.x==p1.x+1&&(pt.y==p1.y-1
									||pt.y==p1.y+1)||pt.x==p1.x-1&&(pt.y==p1.y-1||pt.y==p1.y+1))
								targets.add(temp);
							
						
						
					}
					h.execute(targets);
				}
			}
			
		}
		else if (a instanceof DamagingAbility ){
			DamagingAbility d = (DamagingAbility)a;
			if (d.getCastArea().equals(AreaOfEffect.TEAMTARGET)){
				if (first){
					for(int i = 0 ; i<secondPlayer.getTeam().size();i++){
						//if (!secondPlayer.getTeam().get(i).getName().equals(c.getName())){
							Point p2 = secondPlayer.getTeam().get(i).getLocation();
							int distance = distance(p1,p2);
							if (distance<=a.getCastRange())
								targets.add(secondPlayer.getTeam().get(i));
							
						}
					d.execute(targets);	
				}
				else{
					for(int i = 0 ; i<firstPlayer.getTeam().size();i++){
				
					//if (!firstPlayer.getTeam().get(i).getName().equals(c.getName())){
					Point p2 = firstPlayer.getTeam().get(i).getLocation();
					int distance = distance(p1,p2);
					if (distance<=a.getCastRange())
						targets.add(firstPlayer.getTeam().get(i));
					
				}
			
					d.execute(targets);
				}
			}
			else if (d.getCastArea().equals(AreaOfEffect.SURROUND)){
				if (!first){
					ArrayList<Champion> T= firstPlayer.getTeam();
					for (int i = 0 ; i <firstPlayer.getTeam().size(); i++){
						Champion temp = firstPlayer.getTeam().get(i);
						Point pt = temp.getLocation();
						
							if (pt.y==p1.y&&(pt.x==p1.x+1
									||pt.x==p1.x-1)
									||pt.x==p1.x&&(pt.y==p1.y-1
									||pt.y==p1.y+1)
									||pt.x==p1.x+1&&(pt.y==p1.y-1
									||pt.y==p1.y+1)||pt.x==p1.x-1&&(pt.y==p1.y-1||pt.y==p1.y+1))
								targets.add(temp);
							
						
						
					}
					d.execute(targets);
				}
				else{
					ArrayList<Champion> T= secondPlayer.getTeam();
					for (int i = 0 ; i <secondPlayer.getTeam().size(); i++){
						Champion temp = secondPlayer.getTeam().get(i);
						Point pt = temp.getLocation();
						
							if (pt.y==p1.y&&(pt.x==p1.x+1
									||pt.x==p1.x-1)
									||pt.x==p1.x&&(pt.y==p1.y-1
									||pt.y==p1.y+1)
									||pt.x==p1.x+1&&(pt.y==p1.y-1
									||pt.y==p1.y+1)||pt.x==p1.x-1&&(pt.y==p1.y-1||pt.y==p1.y+1))
								targets.add(temp);
							
						
						
					}
					d.execute(targets);
				}
			}
			
			
		}
		
		
		
	}
	
	public void castAbility(Ability a , Direction d) {
		
	}
	
	public void castAbility(Ability a , int x , int y) throws NotEnoughResourcesException, 
	InvalidTargetException, AbilityUseException {
		Champion c = (Champion) this.getCurrentChampion();
		boolean first = firstPlayer.getTeam().contains(c);
		for(Effect tmp : c.getAppliedEffects()) 
			if(tmp.getName().equals("Silence"))
				throw new AbilityUseException();
		
		if(c.getMana() < a.getManaCost())
			throw new NotEnoughResourcesException();
		else if(distance(c.getLocation(),new Point(x,y)) > a.getCastRange()) // removed || board[x][y] instanceof cover
			throw new AbilityUseException(); // replaced invalid target by ability use
//		else if(distance(c.getLocation() , new Point(x,y)) > a.getCastRange())
//			throw new InvalidTargetException();
		else if(a.getCurrentCooldown() > 0)
			throw new NotEnoughResourcesException();
		else if(board[x][y] instanceof Cover)
			throw new InvalidTargetException();
		
		else {
			c.setMana(c.getMana() - a.getManaCost());
			if(a instanceof HealingAbility) {
				HealingAbility h = (HealingAbility) a;
				if(board[x][y] instanceof Cover || first && !firstPlayer.getTeam().contains((Champion) board[x][y]) || 
						!first && firstPlayer.getTeam().contains((Champion) board[x][y]))
					throw new InvalidTargetException();
			
				else {
					ArrayList<Damageable> target = new ArrayList<Damageable>();
					target.add((Damageable) board[x][y]);
					h.execute(target);
				}
				c.setMana(c.getMana() - h.getManaCost());
				c.setCurrentActionPoints(c.getCurrentActionPoints() - h.getRequiredActionPoints());
				h.setCurrentCooldown(h.getBaseCooldown());
				
			}
			else if(a instanceof DamagingAbility) {
				DamagingAbility d = (DamagingAbility) a;
				if(!first && !firstPlayer.getTeam().contains((Champion) board[x][y])
						|| first && firstPlayer.getTeam().contains((Champion) board[x][y])) 
					throw new InvalidTargetException();
				else {
					ArrayList<Damageable> target = new ArrayList<Damageable>();
					target.add((Damageable) board[x][y]);
					d.execute(target);
				}
				c.setMana(c.getMana() - d.getManaCost());
				c.setCurrentActionPoints(c.getCurrentActionPoints() - d.getRequiredActionPoints());
				d.setCurrentCooldown(d.getBaseCooldown());
				if(board[x][y] instanceof Cover) {
					Cover tmp = (Cover) board[x][y];
					if(tmp.getCurrentHP() == 0)
						board[x][y] = null;
				}
				else {
					Champion tmp = (Champion) board[x][y];
					if(tmp.getCurrentHP() == 0) {
						board[x][y] = null;
						if(firstPlayer.getTeam().contains(tmp))
							firstPlayer.getTeam().remove(tmp);
						else
							secondPlayer.getTeam().remove(tmp);
					}
				}
			}
			else if(a instanceof CrowdControlAbility) {
				CrowdControlAbility ca = (CrowdControlAbility) a;
				if(ca.getEffect().getName().equals("Disarm") || ca.getEffect().getName().equals("Silence")
						|| ca.getEffect().getName().equals("Root") || ca.getEffect().getName().equals("Shock")
						|| ca.getEffect().getName().equals("Stun")) {
					if(!first && !firstPlayer.getTeam().contains((Champion) board[x][y])
							|| first && firstPlayer.getTeam().contains((Champion) board[x][y]))
						throw new InvalidTargetException();
					else {
						ArrayList<Damageable> target = new ArrayList<Damageable>();
						target.add((Damageable) board[x][y]);
						ca.execute(target);
					}
				}
				else {
					if(first && !firstPlayer.getTeam().contains((Champion) board[x][y]) || 
						!first && firstPlayer.getTeam().contains((Champion) board[x][y]))
						throw new InvalidTargetException();
					else {
						ArrayList<Damageable> target = new ArrayList<Damageable>();
						target.add((Damageable) board[x][y]);
						ca.execute(target);
					}
					
				}
				c.setMana(c.getMana() - ca.getManaCost());
				c.setCurrentActionPoints(c.getCurrentActionPoints() - ca.getRequiredActionPoints());
				ca.setCurrentCooldown(ca.getBaseCooldown());
			}
		}
		
	}
	
	public int distance(Point p1 , Point p2) { // this method returns the manhattan distance (required by the MS)
		return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
	}
	
	public void useLeaderAbility() throws LeaderNotCurrentException, LeaderAbilityAlreadyUsedException {
		Champion c = (Champion) this.getCurrentChampion();
		boolean first = firstPlayer.getTeam().contains(c);
		ArrayList<Champion> targets = new ArrayList<Champion>();
		if(!c.equals(firstPlayer.getLeader()) || !c.equals(secondPlayer.getLeader()))
			throw new LeaderNotCurrentException();
		else if((first && firstLeaderAbilityUsed) || (!first && secondLeaderAbilityUsed))
			throw new LeaderAbilityAlreadyUsedException();
		else if(c instanceof Hero) {
			if(first) {
				for(int i = 0; i < firstPlayer.getTeam().size(); i++) {
					if(!firstPlayer.getTeam().get(i).equals(c))
						targets.add(firstPlayer.getTeam().get(i));
				}
				firstLeaderAbilityUsed = true;
			}
			
			else {
				for(int i = 0; i < secondPlayer.getTeam().size(); i++) {
					if(!secondPlayer.getTeam().get(i).equals(c))
						targets.add(secondPlayer.getTeam().get(i));
				}
				secondLeaderAbilityUsed = true;
			}
			
		}
		else if(c instanceof Villain) {
			if(first) {
				for(int i = 0; i < secondPlayer.getTeam().size(); i++) 
					targets.add(secondPlayer.getTeam().get(i));
				firstLeaderAbilityUsed = true;
			}
			else {
				for(int i = 0; i < firstPlayer.getTeam().size(); i++)
					targets.add(firstPlayer.getTeam().get(i));
				secondLeaderAbilityUsed = true;
			}
		}
		else {
			for(Champion x : firstPlayer.getTeam()) 
				if(!x.equals(firstPlayer.getLeader()))
					targets.add(x);
			
			for(Champion x : secondPlayer.getTeam()) 
				if(!x.equals(secondPlayer.getLeader()))
					targets.add(x);
			if(first)
				firstLeaderAbilityUsed = true;
			else
				secondLeaderAbilityUsed = true;
		}
		c.useLeaderAbility(targets);
	}
	
	public void endTurn() {
		Champion c = (Champion) turnOrder.remove();
		for(Effect e : c.getAppliedEffects()) {
			e.setDuration(e.getDuration() - 1);
			if(e.getDuration() == 0) {
				e.remove(c);
				c.getAppliedEffects().remove(e);
			}
		}
		for(Ability a : c.getAbilities()) 
			a.setCurrentCooldown(a.getCurrentCooldown() - 1);
		c.setCurrentActionPoints(c.getMaxActionPointsPerTurn());
		
		
	}

	public void placeChampions() {
		int i = 1;
		for (Champion c : firstPlayer.getTeam()) {
			board[0][i] = c;
			c.setLocation(new Point(0, i));
			i++;
		}
		i = 1;
		for (Champion c : secondPlayer.getTeam()) {
			board[BOARDHEIGHT - 1][i] = c;
			c.setLocation(new Point(BOARDHEIGHT - 1, i));
			i++;
		}
	
	}

	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}

	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public Object[][] getBoard() {
		return board;
	}

	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}

	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}

	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}

	public static int getBoardwidth() {
		return BOARDWIDTH;
	}

	public static int getBoardheight() {
		return BOARDHEIGHT;
	}
}
