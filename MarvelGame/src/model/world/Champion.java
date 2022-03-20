package model.world;

import java.awt.Point;
import java.util.ArrayList;

import model.abilities.Ability;

public class Champion {
	private String name;
	private int maxHP;
	private int currentHP;
	private int mana;
	private int maxActionPointsPerTurn;
	private int currentActionPoints;
	private int attackRange;
	private int attackDamage;
	private int speed;
	private ArrayList<Ability> abilities;
	//private ArrayList<Effect> appliedEffects; // remove comment when class is done
	private Condition condition;
	private Point location;
	
	public Champion(String name , int maxHP , int mana , int maxActions , int speed , int attackRange , int attackDamage) {
		this.name = name;
		this.maxHP = maxHP;
		this.currentHP = maxHP; // logically a champion should start with maxHP, it wasn't stated in file(should ask TA)
		// not clear yet where to place maxActions
		this.mana = mana;
		this.speed = speed;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		// not clear yet how to initialize ArrayLists (whether empty or not)
	}

	// if it is not mentioned if an attribute is read or write both setter and getter were implemented for it
	
	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
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
	
	
	
	
}
