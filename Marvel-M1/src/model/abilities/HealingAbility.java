package model.abilities;

import java.util.ArrayList;

import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;

public  class HealingAbility extends Ability {
	private int healAmount;

	public HealingAbility(String name,int cost, int baseCoolDown, int castRadius, AreaOfEffect area,int required, int healingAmount) {
		super(name,cost, baseCoolDown, castRadius, area,required);
		this.healAmount = healingAmount;
	}

	public int getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}
	
	public void execute(ArrayList<Damageable> targets){
	    int currentHp;
	    int newHp;
	    for (int i =0 ;i<targets.size();i++){
	    	Object s = targets.get(i);
	    	if (s instanceof Champion){
	    		Champion c = (Champion)s;
	    		currentHp=c.getCurrentHP();
	    		newHp=currentHp+this.healAmount;
	    		c.setCurrentHP(newHp);
	    		}
	    	else{
	    			Cover v = (Cover)s;
	    			currentHp=v.getCurrentHP();
		    		newHp=currentHp+this.healAmount;
		    		v.setCurrentHP(newHp);
	    			
	    		}
	    		
	    	}
	    }
	

}
