package javagame;

import org.lwjgl.input.Mouse;

import java.awt.Font;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;


public class Menu extends BasicGameState{

	Image backGround;
	Image playGame;
	Image instructions;
	Image exitGame;
	Image logo;
	Image pauseMusic;
	UnicodeFont uFont;
	private Music music;
	
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
		exitGame = new Image("res/Button-Close-icon.png");
		logo = new Image("res/cloudytower.png");
		pauseMusic = new Image("res/Button-Pause-icon.png");
		Font font = new Font("Serif",Font.PLAIN,20);
		uFont = new UnicodeFont(font, font.getSize(),font.isBold(), font.isItalic());
		music = new Music("res/Happy_Background_Music_-_Sweet_by_Sophonic.wav");
		music.setVolume(0.5f);
		music.play();
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		
		//g.setFont(uFont);
		g.setColor(Color.darkGray);
		backGround.draw();
		logo.draw(10, 30);
		playGame.draw(30,330);
		g.drawString("Play Game",90, 345);
		instructions.draw(30,430);
		g.drawString("Instructions",90 , 445);
		exitGame.draw(30,530);
		g.drawString("Exit Game",90,545);
		pauseMusic.draw(700,550);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		// button Play Game
		if ((posX > 35) && (posX < 75) && ((posY > 220) && (posY < 270 ))){
			if (Mouse.isButtonDown(0)) {
				music.stop();
				sbg.enterState(1);
			}
		}
		// button Instructions
		if ((posX > 35) && (posX < 75) && ((posY > 120) && (posY < 170 ))){
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(2);
			}
		}
		// button Exit Game
		if ((posX > 30) && (posX < 80) && ((posY > 20) && (posY < 70 ))){
			if (Mouse.isButtonDown(0)) {
				System.exit(0);
			}
		}
		if ((posX > 700) && (posX < 730) && ((posY > 20) && (posY < 50 ))){
			if (Mouse.isButtonDown(0)) {
				music.pause();
			}
		}
	}
		
	public boolean inBox(int x, int y, int xSmall, int ySmall, int xBig, int yBig) {
		return (x >= xSmall && x <= xBig) && (y >= ySmall && y <= yBig);
	}
}