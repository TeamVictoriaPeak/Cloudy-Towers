package javagame;

import org.newdawn.slick.Animation;

public class PowerUp {

	float powerUpX;
	float powerUpY;
	
	Animation powerUpAnimation; 
	
	public PowerUp(float powerUpX,float powerUpY,Animation powerUpAnimation){
		
		this.powerUpX = powerUpX;
		this.powerUpY = powerUpY;
		this.powerUpAnimation = powerUpAnimation;
				
	}
	
	
}
