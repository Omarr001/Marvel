package model.abilities;

public class HealingAbility extends Ability{
 private int healAmount;
 
public HealingAbility(String name,int cost, int baseCoolDown, int castRange, AreaOfEffect area ,
int required , int healAmount)
{
	 super(name,cost,baseCoolDown,castRange,area,required);
	 this.healAmount=healAmount;

}

public int getHealAmount() {
	return healAmount;
}

public void setHealAmount(int healAmount) {
	if(healAmount < 0) {
		this.healAmount = 0;
		return;
	}
	// added this condition (Omar) (31/3)
	this.healAmount = healAmount;
}

//Getters and Setters

}
