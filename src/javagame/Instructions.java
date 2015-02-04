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
		Image back;
		Image forward;
		Image hero;
		Image coins;
		Image wings;
		Image logo;
		UnicodeFont uFont;
		
		public Instructions(int state){
			
		}
		
		public int getID(){
			return 2;
		}

		public void init(GameContainer gc, StateBasedGame sbg)
				throws SlickException {
			
			backGround = new Image("res/Sky_Background.jpg");
			back = new Image("res/Button-Rewind-icon.png");
			forward = new Image("res/Button-Fast-Forward-icon.png");
			hero = new Image("res/Run_1.png");
			coins = new Image("res/Coins_1.png");
			wings = new Image("res/wings-3.png");
			logo = new Image("res/1112.png");
			Font font = new Font("Serif",Font.PLAIN , 20);
			uFont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
			
		
		}
		public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
				throws SlickException {
			
			g.setColor(Color.darkGray);
			backGround.draw();
			g.drawString("You are student in SoftUni.You must go through a lot of difficulties to accomplish", 30, 150);
			g.drawString("your education.Jump on the clouds to reach the top.We will", 130, 170);
			g.drawString("give you small gifts to help you during your journey.", 165, 190);
			logo.draw(100,20);
			back.draw(550,540);
			forward.draw(650,540);
			hero.draw(30,260);
			g.drawString("- Your hero",100,290);
			coins.draw(30,370);
			g.drawString("- Grab the coin and you will get bonus 10 pts", 100, 388);
			wings.draw(30,460);
			g.drawString("- Grab the wings and you will gain boost on your jump", 100, 480);
			
		}
		public void update(GameContainer gc, StateBasedGame sbg, int g)
				throws SlickException {
			int posX = Mouse.getX();
			int posY = Mouse.getY();
			
			// button Back
			if ((posX > 550) && (posX < 600) && ((posY > 20) && (posY < 55 ))){
				if (Mouse.isButtonDown(0)) {
					sbg.enterState(0);
			}
		}
			if ((posX > 650) && (posX < 700) && ((posY > 20) && (posY < 55 ))) {
				if (Mouse.isButtonDown(0)) {
					sbg.enterState(3);
				}
			}
	}
}
