package model.effects;

import java.util.ArrayList;

import model.abilities.Ability;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;

public class PowerUp extends Effect {
	

	public PowerUp(int duration) {
		super("PowerUp", duration, EffectType.BUFF);
		
	}
	// (Omar 4/5) not sure if this is the correct solution
	public void apply(Champion c) {
		//c.getAppliedEffects().add(this);
		ArrayList<Ability> tmp = c.getAbilities();
		for(int i = 0; i < tmp.size(); i++) {
			if(tmp.get(i) instanceof DamagingAbility) {
				DamagingAbility d = (DamagingAbility) tmp.get(i);
				d.setDamageAmount((int) (1.2 * d.getDamageAmount()));
			}
			else if(tmp.get(i) instanceof HealingAbility) {
				HealingAbility h = (HealingAbility) tmp.get(i);
				h.setHealAmount((int) (1.2 * h.getHealAmount()));
			}
		}
	}
	// (Omar 4/5) not sure if this is the correct solution
	public void remove(Champion c) {
		//c.getAppliedEffects().remove(this);
		ArrayList<Ability> tmp = c.getAbilities();
		for(int i = 0; i < tmp.size(); i++) {
			if(tmp.get(i) instanceof DamagingAbility) {
				DamagingAbility d = (DamagingAbility) tmp.get(i);
				d.setDamageAmount((int) (d.getDamageAmount() / 1.2));
			}
			else if(tmp.get(i) instanceof HealingAbility) {
				HealingAbility h = (HealingAbility) tmp.get(i);
				h.setHealAmount((int) (h.getHealAmount() / 1.2));
			}
		}
	}
	
}
