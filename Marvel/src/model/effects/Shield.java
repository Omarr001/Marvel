package model.effects;

public class Shield extends Effect {
	
	public Shield(String name,int duration){
		super("Shield",duration,EffectType.BUFF);
	}
}
