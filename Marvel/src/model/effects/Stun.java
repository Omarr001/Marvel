package model.effects;

public class Stun extends Effect {
	
	public Stun(String name,int duration){
		super("Stun",duration,EffectType.DEBUFF);
	}

}
