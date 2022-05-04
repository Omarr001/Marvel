package model.effects;

import model.world.Champion;
// added abstract mehtods not clear on implementation yet (Fayrouz 4/5)
public class Disarm extends Effect {
	

	public Disarm(int duration) {
		super("Disarm", duration, EffectType.DEBUFF);
		
	}
	
	public void apply(Champion c) {
		
	}

	@Override
	public void remove(Champion c) {
		// TODO Auto-generated method stub
		
	}
	
}
