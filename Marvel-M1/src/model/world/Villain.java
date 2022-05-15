package model.world;

import java.util.ArrayList;

public class Villain extends Champion {

	public Villain(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}
	
	public void useLeaderAbility(ArrayList<Champion> targets) {
		for(int i=0;i<targets.size();i++) {
			if((targets.get(i).getCurrentHP()/targets.get(i).getMaxHP()) < 0.3) {
				targets.get(i).setCurrentHP(0);
				targets.get(i).setCondition(Condition.KNOCKEDOUT);
			}
//			double hp= (targets.get(i).getMaxHP()-(targets.get(i).getMaxHP()*0.3));
//			//int hp1=targets.get(i).getCurrentHP()-this.getAttackDamage();
//			// not sure if the villan's attack damage will be applied (Omar)
//			if(hp1>=hp) {
//				targets.get(i).setCurrentHP(hp1);
//			}
//			else {
//				targets.get(i).setCurrentHP(0);
//				targets.get(i).setCurrentActionPoints(0);
//				targets.get(i).setCondition(Condition.KNOCKEDOUT);
//			}
		}
		
	}

	
}
