package model.abilities;

import java.util.ArrayList;

import model.effects.Effect;
import model.world.Champion;
import model.world.Damageable;

public class CrowdControlAbility extends Ability {
	private Effect effect;

	public CrowdControlAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area, int required,
			Effect effect) {
		super(name, cost, baseCoolDown, castRadius, area, required);
		this.effect = effect;

	}
	
	public void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException{
		
	    for (int i =0 ;i<targets.size();i++){
	    
	    		Champion c = (Champion) targets.get(i);;
	    	
				Effect x = (Effect) effect.clone();
				c.getAppliedEffects().add(x);
				x.apply(c);
			
	    		
}
}

	public Effect getEffect() {
		return effect;
	}

}
