package model.effects;

import model.world.Champion;
//added abstract mehtods not clear on implementation yet (Fayrouz 4/5)
public class Dodge extends Effect {

	public Dodge(int duration) {
		super("Dodge", duration, EffectType.BUFF);
		
	}

	@Override
	// (Fayrouz 7/5) rest of the implementation is handled in the engine in c2c helper
	public void apply(Champion c) {
		c.setSpeed((int)(c.getSpeed()*1.05));
		//c.getAppliedEffects().add(this);
	}

	@Override
	public void remove(Champion c) {
		c.setSpeed((int) (c.getSpeed() / 1.05));
		//c.getAppliedEffects().remove(this);
		
	}

}
