package javagame;

import org.newdawn.slick.Animation;

public class BonusCoin {

	float coinX;
	float coinY;
	
	Animation bonusCoinAnimation; 
	
	public BonusCoin(float coinX,float coinY,Animation bonusCoinAnimation){
		
		this.coinX = coinX;
		this.coinY = coinY;
		this.bonusCoinAnimation = bonusCoinAnimation;
				
	}
	
}