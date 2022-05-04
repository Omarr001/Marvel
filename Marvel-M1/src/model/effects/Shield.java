package model.effects;

import model.world.Champion;
//added abstract mehtods not clear on implementation yet (Fayrouz 4/5)
public class Shield extends Effect {

	public Shield( int duration) {
		super("Shield", duration, EffectType.BUFF);
		
	}

	@Override
	public void apply(Champion c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Champion c) {
		// TODO Auto-generated method stub
		
	}

}
