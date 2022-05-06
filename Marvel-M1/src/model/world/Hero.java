package model.world;

import java.util.ArrayList;

import model.effects.EffectType;
import model.effects.Embrace;

public class Hero extends Champion {

	public Hero(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}
	// (Youssef 5/5)
	public void useLeaderAbility(ArrayList<Champion> targets) {
		for(int i=0;i<targets.size();i++) {
			for(int j=0;j<targets.get(i).getAppliedEffects().size();j++) {
				if(targets.get(i).getAppliedEffects().get(j).getType().equals(EffectType.DEBUFF)) {
					targets.get(i).getAppliedEffects().get(j).remove(targets.get(i));
					targets.get(i).getAppliedEffects().remove(j);
				}
				
			}
			// added to apply an embrace for each target (Omar)
			targets.get(i).getAppliedEffects().add(new Embrace(2));
		}
		
		
	}

	
}
