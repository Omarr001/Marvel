package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Root extends Effect {

	public Root( int duration) {
		super("Root", duration, EffectType.DEBUFF);
		
	}
	// (Omar 4/5) not sure if this is the correct solution
	public void apply(Champion c) {
		// INACTIVE has a precedence over ROOTED so if champion is INACTIVE, Root won't affect it
		// This condition assuming if the champion is knocked out there is no way an effect is applied on it
		if(!(c.getCondition().equals(Condition.INACTIVE))) 
			c.setCondition(Condition.ROOTED);
	}
	// (Omar 4/5) not sure if this is the correct solution
	public void remove(Champion c) {
		if(c.getCondition().equals(Condition.ROOTED))
			c.setCondition(Condition.ACTIVE);
	}

}
