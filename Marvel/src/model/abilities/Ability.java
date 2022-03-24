package model.abilities;


public class Ability {
	//Attributes
 private String name;
 private int manaCost;
 private int baseCooldown;
 private int currentCooldown;
 private int castRange;
 private int requiredActionPoints;
 private AreaOfEffect castArea;
 
    //Constructors
public Ability(String name,int cost, int baseCoolDown, int castRange, AreaOfEffect area ,
int required){
	this.name = name;
	manaCost=cost;
	baseCooldown=baseCoolDown;
	this.castRange=castRange;
	castArea= area;
	requiredActionPoints=required;	
} //(Omar) we might have to initialise currentCooldown to 0 in the constructor
//Getters

public String getName() {
	return name;
}

public int getManaCost() {
	return manaCost;
}

public int getBaseCooldown() {
	return baseCooldown;
}

public int getCurrentCooldown() {
	return currentCooldown;
}

public int getCastRange() {
	return castRange;
}

public int getRequiredActionPoints() {
	return requiredActionPoints;
}

public AreaOfEffect getCastArea() {
	return castArea;
}
// this was not implemented
public void setCurrentCooldown(int currentCooldown) {
	this.currentCooldown = currentCooldown;
}

}
