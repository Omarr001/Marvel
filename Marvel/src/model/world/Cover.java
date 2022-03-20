package model.world;

import java.awt.Point;
import java.util.* ;

public class Cover {
 
	private int currentHP;
	private Point location;
	
	
public Cover(int x, int y){
	Random r = new Random ();
	currentHP=r.nextInt(1000-100)+100;
	location = new Point (x,y);
	
}

// Getters and Setters
public int getCurrentHP() {
	return currentHP;
}


public Point getLocation() {
	return location;
}


public void setCurrentHP(int currentHP) {
	this.currentHP = currentHP;
}

}
