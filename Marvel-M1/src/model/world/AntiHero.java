package model.world;

import java.util.ArrayList;

import model.effects.Stun;

public class AntiHero extends Champion {

	public AntiHero(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}
	
	//I handled the case if there are heroes in the target but what if there are villains I am not sure what to do. youssef(5/5)
		public void useLeaderAbility(ArrayList<Champion> targets) {
			
			for(int i=0;i<targets.size();i++) {
				Stun e =new Stun(2);
				targets.get(i).getAppliedEffects().add(e);
				e.apply(targets.get(i));
			}
//			for(int j=0;j<targets.size();j++) {
				
//				switch(targets.get(j).getName()) {
//				case"Captain America":case"Dr Strange":case"Hulk":case"Iceman":case"Ironman":case"Spiderman":case"Thor":
//					double hp= (targets.get(j).getMaxHP()-(targets.get(j).getMaxHP()*0.3));
//					int hp1=targets.get(j).getCurrentHP()-this.getAttackDamage();
//					if(hp1>=hp) {
//						targets.get(j).setCurrentHP(hp1);
//					}
//					else {
//						targets.get(j).setCurrentHP(0);
//						targets.get(j).setCurrentActionPoints(0);
//						targets.get(j).setMana(0);
//						targets.get(j).setCondition(Condition.KNOCKEDOUT);
//					}
//					break;	
//					
//				}
//			}
			
		}
}
