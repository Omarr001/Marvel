package model.effects;

import model.world.Champion;

public class Shock extends Effect {

	public Shock(int duration) {
		super("Shock", duration, EffectType.DEBUFF);
		
	}
	// (Omar 4/5) not sure if this is the correct solution
	public void apply(Champion c) {
		c.getAppliedEffects().add(this);
		c.setSpeed((int) (c.getSpeed() * 0.9));
		c.setAttackDamage((int) (c.getAttackDamage() * 0.9));
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn() - 1);
		c.setCurrentActionPoints(c.getCurrentActionPoints() - 1);
	}
	// (Omar 4/5) not sure if this is the correct solution
	public void remove(Champion c) {
		c.getAppliedEffects().remove(this);
		c.setSpeed((int) (c.getSpeed() * 1.1));
		c.setAttackDamage((int) (c.getAttackDamage() * 1.1));
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn() + 1);
		c.setCurrentActionPoints(c.getCurrentActionPoints() + 1);
	}

}
