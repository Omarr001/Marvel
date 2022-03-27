package model.world;

import java.awt.Point;
import java.util.ArrayList;

import model.abilities.Ability;
import model.effects.Effect;

public class Champion {
	private String name; 
	// read only
	private int maxHP; 
	// read only
	private int currentHP;
	private int mana; 
	// read only (this will be the amount of mana throught the "entire" game
	private int maxActionPointsPerTurn; 
	// max point for actions "each" turn
	private int currentActionPoints;
	// read only remaining points in current turn
	private int attackRange;
	// read only
	private int attackDamage;
	// how much does the attack damage the others
	private int speed; 
	// might be the amount of time the champion wait to get his turn (the lower the better)
	private ArrayList<Ability> abilities;
	// read only
	private ArrayList<Effect> appliedEffects;
	// contains all current effetcs "applied on" the champion (makes sense to be empty in the beggining)
	private Condition condition; 
	// starts with ACTIVE
	private Point location;
	
	public Champion(String name , int maxHP , int mana , int maxActions , int speed , int attackRange , int attackDamage) {
		this.name = name;
		this.maxHP = maxHP;
		this.currentHP = maxHP;
		// logically a champion should start with maxHP, it wasn't stated in file(should ask TA)
		this.maxActionPointsPerTurn = maxActions;
		this.currentActionPoints = maxActions;
		// makes sense that a champion will start by the max Action points
		this.mana = mana;
		this.speed = speed;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		this.condition = Condition.ACTIVE;
		this.appliedEffects = new ArrayList<Effect>();
		this.abilities = new ArrayList<Ability>();
		// not clear yet how to initialize ArrayLists (whether empty or not)
	}

	// if it is not mentioned if an attribute is read or write both setter and getter were implemented for it
	
	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		if(currentHP > maxHP) {
			currentHP = maxHP; 
			// not sure if correct imp. but makes sense when you receive HP more than max HP will be max
			return;
		}
		if(currentHP < 0) {
			this.currentHP = 0;
			return;
		}
		this.currentHP = currentHP;
	}

	public int getMaxActionPointsPerTurn() {
		return maxActionPointsPerTurn;
	}

	public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
		this.maxActionPointsPerTurn = maxActionPointsPerTurn;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public int getMana() {
		return mana;
	}

	public int getCurrentActionPoints() {
		return currentActionPoints;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public ArrayList<Effect> getAppliedEffects() {
		return appliedEffects;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public void setCurrentActionPoints(int currentActionPoints) {
		this.currentActionPoints = currentActionPoints;
	}
	
	
	
	
}
