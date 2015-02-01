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


public class Instructions extends BasicGameState {
	
	Image backGround;
	Image turnLeft;
	Image turnRight;
	Image jump;
	Image pause;
	Image back;
	UnicodeFont uFont;
	
	public Instructions(int state){
		
	}
	
	public int getID(){
		return 2;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		backGround = new Image("res/Sky_Background.jpg");
		turnRight = new Image ("res/button.png");
		turnLeft = new Image("res/button2.png");
		jump = new Image("res/space.png");
		pause = new Image("res/esc.png");
		back = new Image("res/symbol-delete-icon.png");
		Font font = new Font("Serif",Font.PLAIN , 20);
		uFont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
	
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		//g.setFont(uFont);
		g.setColor(Color.darkGray);
		backGround.draw();
		g.drawString("Instructions", 330, 10);
		turnRight.draw(40, 110);
		g.drawString("- Turn Right",150 ,150);
		turnLeft.draw(40,210);
		g.drawString("- Turn Left",150 , 250);
		jump.draw(40,330);
		g.drawString("- Jump", 170, 340);
		pause.draw(40, 400);
		g.drawString("- Pause",150, 430);
		back.draw(40,540);
		g.drawString("Back to Main Menu", 100,555);
		
	}
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		
		// button Back to Main Menu
		if ((posX > 53) && (posX < 73) && ((posY > 25) && (posY < 43 ))){
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(0);
			}
		}
	}
}
	
	
