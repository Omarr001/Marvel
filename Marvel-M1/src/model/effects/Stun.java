package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Stun extends Effect {

	public Stun(int duration) {
		super("Stun", duration, EffectType.DEBUFF);
	}
	// (Omar 4/5) not sure if this is the correct solution
	public void apply(Champion c) {
		c.getAppliedEffects().add(this);
		c.setCondition(Condition.INACTIVE);
	}
	// (Omar 11/5) not sure of the solution
	public void remove(Champion c) {
		c.getAppliedEffects().remove(this);
		for(Effect e : c.getAppliedEffects()) 
			if(e instanceof Root) {
				c.setCondition(Condition.ROOTED);
				return;
			}
		c.setCondition(Condition.ACTIVE);	
	}
	
}
