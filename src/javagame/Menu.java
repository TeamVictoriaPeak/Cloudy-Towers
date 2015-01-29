package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState{

	Image backGround;
	Image startButton;
	Image exitButton;
	
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
		
		
	}


	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		backGround.draw();
		
		
		
	}


	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		Input input = gc.getInput();
		MouseX = input.getMouseX();
		MouseY = input.getMouseY();
		
		
		if (input.isMousePressed(0)) {			
			
			if (inBox(MouseX, MouseY, 350, 250, 542, 348)) {
				
				sbg.enterState(1);
				
			}
			else if (inBox(MouseX, MouseY, 370, 350, 488, 478)) {
				
				System.exit(0);
				
			}
					
		}
		
		
	}
	
	
	
	public boolean inBox(int x, int y, int xSmall, int ySmall, int xBig, int yBig) {
		return (x >= xSmall && x <= xBig) && (y >= ySmall && y <= yBig);
	}
}