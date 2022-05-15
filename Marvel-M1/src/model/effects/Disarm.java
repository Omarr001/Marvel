package model.effects;

import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.DamagingAbility;
import model.world.Champion;
// added abstract mehtods not clear on implementation yet (Fayrouz 4/5)
public class Disarm extends Effect {
	

	public Disarm(int duration) {
		super("Disarm", duration, EffectType.DEBUFF);
		
	}
	// Team 11/5 rest of imp in engine
	public void apply(Champion c) {
		//c.getAppliedEffects().add(this);
		Ability a = new DamagingAbility("Punch", 0, 1, 1, AreaOfEffect.SINGLETARGET, 1, 50);
		c.getAbilities().add(a);
	}

	@Override
	public void remove(Champion c) {
		//c.getAppliedEffects().remove(this);
		int x = c.getAbilities().size();
		for(int i = 0; i < x; i++) {
			if(c.getAbilities().get(i).getName().equals("Punch")) {
				DamagingAbility d = (DamagingAbility) c.getAbilities().get(i);
				c.getAbilities().remove(d);
			}
				
		}
		
	}
	
}
