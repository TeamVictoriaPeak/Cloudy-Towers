package javagame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Level_One extends BasicGameState {

	Animation charCurrent, charMoveRight, charMoveLeft, charJumpRight,
			charJumpLeft, charStillRight, charStillLeft, charFallRight,
			charFallLeft;
	float charPositionX = 400;
	float charPositionY = 450;
	float charPositionJump;

	Image background, Cloud;
	float CloudX = 300, CloudY = 300;
	float EarthY = 450;
	boolean onEarth = true;
	boolean onCloud = false;
	boolean inAir = false;
	boolean falling = false;
	
	int score;

	public Level_One(int state) {

	}

	public int getID() {
		return 1;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		Image[] runRight = { new Image("res/Run_1.png"),
				new Image("res/Run_2.png"), new Image("res/Run_3.png"),
				new Image("res/Run_4.png") };
		Image[] runLeft = {
				new Image("res/Run_1.png").getFlippedCopy(true, false),
				new Image("res/Run_2.png").getFlippedCopy(true, false),
				new Image("res/Run_3.png").getFlippedCopy(true, false),
				new Image("res/Run_4.png").getFlippedCopy(true, false) };
		Image[] jumpRight = { new Image("res/Jump_1.png"),
				new Image("res/Jump_2.png") };
		Image[] jumpLeft = {
				new Image("res/Jump_1.png").getFlippedCopy(true, false),
				new Image("res/Jump_2.png").getFlippedCopy(true, false) };
		Image[] stillRight = { new Image("res/Still.png") };
		Image[] stillLeft = { new Image("res/Still.png").getFlippedCopy(true,
				false) };
		Image[] fallRight = { new Image("res/Jump_3.png") };
		Image[] fallLeft = { new Image("res/Jump_3.png").getFlippedCopy(true,
				false) };
		charMoveRight = new Animation(runRight, 150);
		charMoveLeft = new Animation(runLeft, 150);
		charJumpRight = new Animation(jumpRight, 150);
		charJumpLeft = new Animation(jumpLeft, 150);
		charStillRight = new Animation(stillRight, 150);
		charStillLeft = new Animation(stillLeft, 150);
		charFallRight = new Animation(fallRight, 150);
		charFallLeft = new Animation(fallLeft, 150);
		charCurrent = charStillRight;

		background = new Image("res/background.jpg");
		Cloud = new Image("res/cloud.png");

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		gc.setShowFPS(false);
		background.draw();
		Cloud.draw(CloudX, CloudY);
		Cloud.draw(CloudX - 200, CloudY);
		Cloud.draw(CloudX + 200, CloudY);
		charCurrent.draw(charPositionX, charPositionY);
		this.drawScore(g);
		
	}
	
	public void drawScore (Graphics g) {
		g.setColor(Color.white);
		g.drawString("SCORE " + score, 15, 15);
	}

	@SuppressWarnings("static-access")
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		Input input = gc.getInput();

		HeroOnEarth();
		// HeroOnCloud();
		if ((onEarth == true || onCloud == true)
				&& (charCurrent != charJumpRight && charCurrent != charJumpLeft)
				&& !falling) {
			if (input.isKeyDown(input.KEY_RIGHT) && charPositionX < 744) {
				charCurrent = charMoveRight;
				charPositionX += g * 0.5;
			} else if (charCurrent == charMoveRight
					&& !(input.isKeyDown(input.KEY_RIGHT))) {
				charCurrent = charStillRight;
			}
			if (input.isKeyDown(input.KEY_LEFT) && charPositionX > 0) {
				charCurrent = charMoveLeft;
				charPositionX -= g * 0.5;
			} else if (charCurrent == charMoveLeft
					&& !(input.isKeyDown(input.KEY_LEFT))) {
				charCurrent = charStillLeft;
			}
			if (input.isKeyDown(input.KEY_SPACE)
					&& (charCurrent == charStillRight || charCurrent == charMoveRight)) {
				charCurrent = charJumpRight;
				charPositionJump = charPositionY - 400;
				inAir = true;
			}
			if (input.isKeyDown(input.KEY_SPACE)
					&& (charCurrent == charStillLeft || charCurrent == charMoveLeft)) {
				charCurrent = charJumpLeft;
				charPositionJump = charPositionY - 400;
				inAir = true;
			}
		} else {
			if (charPositionY <= charPositionJump) {
				falling = true;
				inAir = false;
			}
			if (input.isKeyDown(input.KEY_RIGHT)
					&& (charPositionY >= charPositionJump) && inAir
					&& charPositionX < 744) {
				charCurrent = charJumpRight;
				charPositionX += g * 0.5;
				charPositionY -= g * 0.3;
			} else if ((!(input.isKeyDown(input.KEY_RIGHT))||charPositionX>=744)
					&& (charPositionY >= charPositionJump) && inAir
					&& charCurrent == charJumpRight) {
				charCurrent = charJumpRight;
				charPositionY -= g * 0.3;
			}
			if (input.isKeyDown(input.KEY_LEFT)
					&& (charPositionY >= charPositionJump) && inAir
					&& charPositionX > 0) {
				charCurrent = charJumpLeft;
				charPositionX -= g * 0.5;
				charPositionY -= g * 0.3;
			} else if ((!(input.isKeyDown(input.KEY_LEFT))||charPositionX<=0)
					&& (charPositionY >= charPositionJump) && inAir
					&& charCurrent == charJumpLeft) {
				charCurrent = charJumpLeft;
				charPositionY -= g * 0.3;
			}

			if (falling) {
				if (input.isKeyDown(input.KEY_RIGHT) && charPositionX < 744) {
					charCurrent = charFallRight;
					charPositionX += g * 0.5;
					charPositionY += g * 0.3;
				}

				if (input.isKeyDown(input.KEY_LEFT) && charPositionX > 0) {
					charCurrent = charFallLeft;
					charPositionX -= g * 0.5;
					charPositionY += g * 0.3;
				}
				if ((charCurrent == charFallRight
						&& !(input.isKeyDown(input.KEY_RIGHT)))||charPositionX>=744) {
					charPositionY += g * 0.3;
				}
				if ((charCurrent == charFallLeft
						&& !(input.isKeyDown(input.KEY_LEFT)))||charPositionX<=0) {
					charPositionY += g * 0.3;
				}
			}

		}
	}

	private void HeroOnEarth() {

		if (charPositionY > EarthY) {
			onEarth = false;
		}

		if (charPositionY >= EarthY) {
			onEarth = true;

			if (falling && charCurrent == charFallRight) {
				charCurrent = charStillRight;
				falling = false;
			}

			if (falling && charCurrent == charFallLeft) {
				charCurrent = charStillLeft;
				falling = false;
			}
		}

	}

	private void HeroOnCloud() {

		if (charPositionY == CloudY
				&& (charPositionX > CloudX && charPositionX < CloudX + 96)) {
			onCloud = true;
			falling = false;
			inAir = false;

		} else {
			onCloud = false;
		}

	}

	public boolean inBox(int x, int y, int xSmall, int ySmall, int xBig,
			int yBig) {
		return (x >= xSmall && x <= xBig) && (y >= ySmall && y <= yBig);
	}
}
