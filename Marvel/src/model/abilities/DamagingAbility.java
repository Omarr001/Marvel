package model.abilities;

public class DamagingAbility extends Ability{
 private int damageAmount;
 
 public DamagingAbility(String name,int cost, int baseCoolDown, int castRange, AreaOfEffect area ,
 int required , int damageAmount){
	 
	 super(name,cost,baseCoolDown,castRange,area,required);
	 this.damageAmount=damageAmount;
 }
//Getters and Setters
public int getDamageAmount() {
	return damageAmount;
}

public void setDamageAmount(int damageAmount) {
	if(damageAmount < 0) {
		this.damageAmount = 0;
		return;
	}
	// added this condition (Omar) (31/3)
	this.damageAmount = damageAmount;
}
 
}

