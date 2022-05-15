package model.effects;

import model.world.Champion;
//added abstract mehtods not clear on implementation yet (Fayrouz 4/5)
public class Shield extends Effect {

	public Shield( int duration) {
		super("Shield", duration, EffectType.BUFF);
		
	}

	// Team 11/5 rest of the implementation is handled in the engine
	@Override
	public void apply(Champion c) {
		//c.getAppliedEffects().add(this);
		c.setSpeed((int) (c.getSpeed() * 1.02));
		
	}

	@Override
	public void remove(Champion c) {
		//c.getAppliedEffects().remove(this);
		c.setSpeed((int) (c.getSpeed() / 1.02));
		
	}

}
