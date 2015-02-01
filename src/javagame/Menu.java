package javagame;

import org.lwjgl.input.Mouse;
import java.awt.Font;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;


public class Menu extends BasicGameState{

	Image backGround;
	Image playGame;
	Image instructions;
	Image exitGame;
	UnicodeFont uFont;
	
	int MouseX, MouseY;
	
	
	public Menu(int state) {
		
	}
	
	
	public int getID(){
		return 0;
	}


	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		backGround = new Image("res/Sky_Background.jpg");
		playGame = new Image("res/Button-Play-icon.png");
		instructions = new Image("res/Button-Help-icon.png");
		exitGame = new Image("res/symbol-delete-icon.png");
		Font font = new Font("Serif",Font.PLAIN,20);
		uFont = new UnicodeFont(font, font.getSize(),font.isBold(), font.isItalic());
	
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		
		//g.setFont(uFont);
		g.setColor(Color.darkGray);
		backGround.draw();
		g.drawString("Main Menu", 340, 10);
		playGame.draw(30,130);
		g.drawString("Play Game",90, 145);
		instructions.draw(30,230);
		g.drawString("Instructions",90 , 245);
		exitGame.draw(30,330);
		g.drawString("Exit Game",90,345);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		// button Play Game
		if ((posX > 35) && (posX < 75) && ((posY > 420) && (posY < 470 ))){
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(1);
			}
		}
		// button Instructions
		if ((posX > 35) && (posX < 75) && ((posY > 320) && (posY < 370 ))){
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(2);
			}
		}
		// button Exit Game
		if ((posX > 45) && (posX < 65) && ((posY > 235) && (posY < 255 ))){
			if (Mouse.isButtonDown(0)) {
				System.exit(0);
			}
		}
	}
	
	public boolean inBox(int x, int y, int xSmall, int ySmall, int xBig, int yBig) {
		return (x >= xSmall && x <= xBig) && (y >= ySmall && y <= yBig);
	}
}