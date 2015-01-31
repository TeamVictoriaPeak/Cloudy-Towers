package javagame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Level_One extends BasicGameState{

	public Level_One(int state) {
		
	}


	public int getID(){
		return 1;
	}


	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		
		
		
	}


	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		
		
		
		
	}


	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		Input input = gc.getInput();
		
		
	}
	
	
	
	public boolean inBox(int x, int y, int xSmall, int ySmall, int xBig, int yBig) {
		return (x >= xSmall && x <= xBig) && (y >= ySmall && y <= yBig);
	}
}
