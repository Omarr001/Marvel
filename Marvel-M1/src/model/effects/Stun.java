package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Stun extends Effect {

	public Stun(int duration) {
		super("Stun", duration, EffectType.DEBUFF);
	}
	// (Omar 4/5) not sure if this is the correct solution
	public void apply(Champion c) {
		c.setCondition(Condition.INACTIVE);
	}
	// (Omar 4/5)
	public void remove(Champion c) {
		// not sure which condition to set the champion to
	}
	
}
