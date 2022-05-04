package model.effects;

import model.world.Champion;

public class Disarm extends Effect {
	

	public Disarm(int duration) {
		super("Disarm", duration, EffectType.DEBUFF);
		
	}
	
	public void apply(Champion c) {
		
	}
	
}
