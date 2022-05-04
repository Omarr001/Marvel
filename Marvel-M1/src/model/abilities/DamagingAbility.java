package model.abilities;

import java.util.ArrayList;

import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;

public class DamagingAbility extends Ability {
	
	private int damageAmount;
	public DamagingAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area,int required,int damageAmount) {
		super(name, cost, baseCoolDown, castRadius, area,required);
		this.damageAmount=damageAmount;
	}
	public int getDamageAmount() {
		return damageAmount;
	}
	public void setDamageAmount(int damageAmount) {
		this.damageAmount = damageAmount;
	}
	
	public void execute(ArrayList<Damageable> targets){
		 int currentHp;
		    int newHp;
		    for (int i =0 ;i<targets.size();i++){
		    	Object s = targets.get(i);
		    	if (s instanceof Champion){
		    		Champion c = (Champion)s;
		    		currentHp=c.getCurrentHP();
		    		newHp=currentHp-this.damageAmount;
		    		c.setCurrentHP(newHp);
		    		}
		    	else{
		    			Cover v = (Cover)s;
		    			currentHp=v.getCurrentHP();
			    		newHp=currentHp-this.damageAmount;
			    		v.setCurrentHP(newHp);
		    			
		    		}
		    		
		    	}
	}
	

}
