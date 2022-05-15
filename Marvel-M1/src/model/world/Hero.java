package model.world;

import java.util.ArrayList;

import model.effects.Effect;
import model.effects.EffectType;
import model.effects.Embrace;

public class Hero extends Champion {

	public Hero(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}
	// (Youssef 5/5)
	public void useLeaderAbility(ArrayList<Champion> targets) {
		//ArrayList<Integer> index = new ArrayList<Integer>();
		
		for(int i=0;i<targets.size();i++) {
			ArrayList<Effect> tmp = targets.get(i).getAppliedEffects();
			for(int j = 0; j < tmp.size(); j++) {
				Effect e = tmp.get(j);
				if(e.getType().equals(EffectType.DEBUFF)) {
					e.remove(targets.get(i));
					tmp.remove(e);
				}
			}
			
			// added to apply an embrace for each target (Omar)
			Effect e = new Embrace(2);
			e.apply(targets.get(i));
			targets.get(i).getAppliedEffects().add(e);
		}
		
		
	}

	
}
