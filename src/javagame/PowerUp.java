package javagame;

import org.newdawn.slick.Image;

public class PowerUp {

	float powerUpX;
	float powerUpY;
	
	Image powerUpImage; 
	
	public PowerUp(float powerUpX,float powerUpY,Image powerUpAnimation){
		
		this.powerUpX = powerUpX;
		this.powerUpY = powerUpY;
		this.powerUpImage = powerUpAnimation;
				
	}
	
	
}
