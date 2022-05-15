package model.effects;

import model.world.Champion;
//added abstract mehtods not clear on implementation yet (Fayrouz 4/5)
public class Embrace extends Effect {
	

	public Embrace(int duration) {
		super("Embrace", duration, EffectType.BUFF);
	}

	// Fayrouz (7/5)
	@Override
	public void apply(Champion c) {
		//c.getAppliedEffects().add(this);
		c.setSpeed((int)(c.getSpeed()*1.2));
		c.setAttackDamage((int)(c.getAttackDamage()*1.2));
		c.setCurrentHP((int)((c.getMaxHP()*0.2)+c.getCurrentHP()));
		c.setMana((int)(c.getMana()*1.2));
	}

	@Override
	public void remove(Champion c) {
		//c.getAppliedEffects().remove(this);
		c.setSpeed((int)(c.getSpeed()/1.2));
		c.setAttackDamage((int)(c.getAttackDamage()/1.2));
		
	}

}
