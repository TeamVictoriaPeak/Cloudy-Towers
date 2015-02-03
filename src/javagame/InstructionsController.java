package javagame;


import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;


public class InstructionsController extends BasicGameState {
	
	Image backGround;
	Image turnLeft;
	Image turnRight;
	Image jump;
	Image pause;
	Image back;
	Image gameControllers;
	Image pauseMusic;
	UnicodeFont uFont;
	
	public InstructionsController(int state){
		
	}
	
	public int getID(){
		return 3;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		backGround = new Image("res/Sky_Background.jpg");
		turnRight = new Image ("res/button.png");
		turnLeft = new Image("res/button2.png");
		jump = new Image("res/space.png");
		pause = new Image("res/esc.png");
		back = new Image("res/Button-Rewind-icon.png");
		gameControllers = new Image("res/GameControllers.png");
		pauseMusic = new Image("res/letter-x-icon.png");
		Font font = new Font("Serif",Font.PLAIN , 20);
		uFont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
	
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		//g.setFont(uFont);
		g.setColor(Color.darkGray);
		backGround.draw();
		gameControllers.draw(200,20);
		turnRight.draw(40, 95);
		g.drawString("- Turn Right",130 ,120);
		turnLeft.draw(40,180);
		g.drawString("- Turn Left",130 , 210);
		jump.draw(40,280);
		g.drawString("- Jump", 170, 285);
		pause.draw(40, 350);
		g.drawString("- Pause",130, 380);
		pauseMusic.draw(40,430);
		g.drawString("- Pause/Start Music in Game", 130, 450);
		back.draw(100,540);
		
		
	}
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		
		// button Back
		if ((posX > 100) && (posX < 150) && ((posY > 20) && (posY < 55 ))){
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(2);
			}
		}
	}
}
	
	
