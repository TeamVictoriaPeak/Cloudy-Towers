package javagame;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;
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
			charFallLeft, coin, powerUp;
	float charPositionX = 400;
	float charPositionY = 450;
	float charPositionJump;
	
	float coinPositionX;
	float coinPositionY;

	float powerUpPositionX;
	float powerUpPositionY;

	Image background, Cloud;
	Image pauseWindow;
	Image quitGame;
	Image resumeGame;
	float CloudX1 = 300, CloudX2 = 100, CloudX3 = 500, CloudY1 = 300,
			CloudY2 = 200, CloudY3 = 100;
	float EarthY = 450;
	float meters = 0;

	boolean onEarth = true;
	boolean onCloud = false;
	boolean inAir = false;
	boolean falling = false;
	boolean pressEsc = false;
	Random rndGenerator = new Random();
	int score;

	List<PowerUp> powerUpList = new LinkedList<PowerUp>();
	List<BonusCoin> bonusList = new LinkedList<BonusCoin>();

	// -------------------------------------------------------
	// Novite metodi se sazdavat POD metoda UPDATE!
	// ---------------------------------------------------

	public Level_One(int state) {

	}

	public int getID() {
		return 1;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		// Sazdavane na masivi ot izobrajeniqta na geroq
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
		Image[] bonus = { new Image("res/Coins_1.png"),
				new Image("res/Coins_2.png"), new Image("res/Coins_3.png"),
				new Image("res/Coins_4.png"), new Image("res/Coins_5.png") };
		Image[] wings = { new Image("/res/wings-1.png"),
				 new Image("/res/wings-2.png"), new Image("/res/wings-3.png") };

		// Sazdavane na animacii ot masivite ot kartinki
		charMoveRight = new Animation(runRight, 150);
		charMoveLeft = new Animation(runLeft, 150);
		charJumpRight = new Animation(jumpRight, 150);
		charJumpLeft = new Animation(jumpLeft, 150);
		charStillRight = new Animation(stillRight, 150);
		charStillLeft = new Animation(stillLeft, 150);
		charFallRight = new Animation(fallRight, 150);
		charFallLeft = new Animation(fallLeft, 150);
		charCurrent = charStillRight;
		coin = new Animation(bonus, 150);
		powerUp = new Animation(wings, 150);

		background = new Image("res/background.png");
		Cloud = new Image("res/cloud.png");
		
		
		
		pauseWindow = new Image("res/GamePauseMenu.png");
//		quitGame = new Image("res/Button-Turn-Off-icon.png");
//		resumeGame = new Image("res/Button-Play-icon.png");

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		gc.setShowFPS(false);
		background.draw();

		drawPowerUp(gc, powerUpList);
		drawBonusCoin(gc, bonusList);

		Cloud.draw(CloudX1, CloudY1);
		Cloud.draw(CloudX2, CloudY2);
		Cloud.draw(CloudX3, CloudY3);
//		coin.draw(coinPositionX, coinPositionY);

		// Printirane na 4ove4eto na ekrana, zaedno s negovite koordinati
		charCurrent.draw(charPositionX, charPositionY);
		this.drawScore(g);
		
		
		if (pressEsc) {
						
			pauseWindow.draw(200, 150);
			
//			quitGame.draw(130, 320);
//			resumeGame.draw(400, 320);
//			g.drawString("Pause", 330, 170);
//			g.drawString("Quit Game", 190, 330);
//			g.drawString("Resume Game", 460, 330);
		}
	}

	@SuppressWarnings("static-access")
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		Input input = gc.getInput();
		int posX = Mouse.getX();
		int posY = Mouse.getY();

		if (!pressEsc) {

			// -------------------------------------------------------------------------------------------------
			// Deistviqta, koito se slu4vat pri IZKLIU4ENA Pause se slagat vatre
			// v "if(!pressEsc) {" !

			// ------------------------------------------------------------------------------------------

			if (input.isKeyPressed(input.KEY_ESCAPE)) {
				pressEsc = true;
			}
			
			
			HeroOnEarth();
			HeroOnCloud();
			
			score++;

			if (score % 5000 == 0) {
				powerUpPositionX = rndGenerator.nextInt(600);
				powerUpPositionY = rndGenerator.nextInt(400);
				powerUpList.add(new PowerUp(powerUpPositionX, powerUpPositionY,powerUp));
			}
			if (takenPowerUp()) {
				score += 1000000000;
			}
			
			
			if (score % 5000 == 0) {
				coinPositionX = rndGenerator.nextInt(600);
				coinPositionY = rndGenerator.nextInt(400);
				bonusList.add(new BonusCoin(coinPositionX, coinPositionY, coin));
			}
			if (takenBonusCoin()) {
				score += 8000;
			}
			

			// Proverka dali geroqt se namira na zemqta ili ne
			if ((onEarth == true || onCloud == true)
					&& (charCurrent != charJumpRight && charCurrent != charJumpLeft)
					&& !falling) {
				if (input.isKeyDown(input.KEY_RIGHT) && charPositionX < 744) {
					charCurrent = charMoveRight;
					charPositionX += g * 0.5;
				} else

				// Animaicii kogato geroqt e na zemqta
				if (charCurrent == charMoveRight
						&& (!(input.isKeyDown(input.KEY_RIGHT)) || charPositionX >= 744)) {
					charCurrent = charStillRight;
				}
				if (input.isKeyDown(input.KEY_LEFT) && charPositionX > 0) {
					charCurrent = charMoveLeft;
					charPositionX -= g * 0.5;
				} else if (charCurrent == charMoveLeft
						&& (!(input.isKeyDown(input.KEY_LEFT)) || charPositionX <= 0)) {
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

				// Animacii kogato geroqt ska4a
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
				} else if ((!(input.isKeyDown(input.KEY_RIGHT)) || charPositionX >= 744)
						&& (charPositionY >= charPositionJump)
						&& inAir
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
				} else if ((!(input.isKeyDown(input.KEY_LEFT)) || charPositionX <= 0)
						&& (charPositionY >= charPositionJump)
						&& inAir
						&& charCurrent == charJumpLeft) {
					charCurrent = charJumpLeft;
					charPositionY -= g * 0.3;
				}

				// Animacii kogato geroqt pada
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
					if ((charCurrent == charFallRight && !(input
							.isKeyDown(input.KEY_RIGHT)))
							|| charPositionX >= 744) {
						charPositionY += g * 0.3;
					}
					if ((charCurrent == charFallLeft && !(input
							.isKeyDown(input.KEY_LEFT))) || charPositionX <= 0) {
						charPositionY += g * 0.3;
					}
				}
			}
		} else {

			// Izlizane ot igrata
			if ((posX > 300) && (posX < 500) && ((posY > 230) && (posY < 280))) {
				if (Mouse.isButtonDown(0)) {
					System.exit(0);
				}
			}

			// Resume na igrata
			if ((posX > 300) && (posX < 500) && ((posY > 300) && (posY < 350))) {
				if (Mouse.isButtonDown(0)) {
					pressEsc = false;
				}
			}

			// Resume na igrata
			if (input.isKeyPressed(input.KEY_ESCAPE)) {
				pressEsc = false;
			}

		}
	}

	
	// NAGORE sa metodite na bibliotekata Slick
	// --------------------------------------------------------------------------------------------
	// OTDOLU se sazdavat novite metodi

	
	
	
	//  na Score countera
	public void drawScore(Graphics g) {
		g.setColor(Color.white);
		g.drawString("SCORE " + score, 15, 15);
	}

	// PowerUp Draw
	public void drawPowerUp(GameContainer gc, List<PowerUp> powerUpList) {
		for (PowerUp powerUp : powerUpList) {
			powerUp.powerUpAnimation.draw(powerUp.powerUpX, powerUp.powerUpY);
		}
	}

	// Bonus Coin Draw
	public void drawBonusCoin(GameContainer gc, List<BonusCoin> bonusCoins) {
		for (BonusCoin coin : bonusList) {
			coin.bonusCoinAnimation.draw(coin.coinX, coin.coinY);
		}
	}
		
	private boolean takenPowerUp() { // Taken Power UP
		boolean taken = false;
		PowerUp takenPowerUp = null;

		for (PowerUp powerUp : powerUpList) {

			if (inBox(powerUp.powerUpX, powerUp.powerUpY, charPositionX - 30,
					charPositionY, charPositionX + 30, charPositionY + 65)) {

				takenPowerUp = powerUp;
				powerUpList.remove(takenPowerUp);

				taken = true;
			}
		}
		return taken;
	}
	
	private boolean takenBonusCoin() { // Taken Bonus Coins
		boolean taken = false;
		BonusCoin takenBonus = null;

		for (BonusCoin coin : bonusList) {

			if (inBox(coin.coinX, coin.coinY, charPositionX - 30,
					charPositionY, charPositionX + 30, charPositionY + 65)) {

				takenBonus = coin;
				bonusList.remove(takenBonus);

				taken = true;
			}
		}
		return taken;
	}

	
	// Proverqva dali geroqt e na zemqta
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

	
	// Proverqva dali geroqt e varhu oblak
	private void HeroOnCloud() {

		if (inBox(charPositionX, charPositionY, CloudX1 - 5, CloudY1 - 30,
				CloudX1 + 100, CloudY1)
				|| inBox(charPositionX, charPositionY, CloudX2 - 5,
						CloudY2 - 30, CloudX2 + 100, CloudY2)
				|| inBox(charPositionX, charPositionY, CloudX3 - 5,
						CloudY3 - 30, CloudX3 + 100, CloudY3)) {
			onCloud = true;

			if (falling && charCurrent == charFallRight) {
				charCurrent = charStillRight;
				falling = false;
			}

			if (falling && charCurrent == charFallLeft) {
				charCurrent = charStillLeft;
				falling = false;
			}
		} else {
			if (onCloud) {
				onCloud = false;
				falling = true;
			}
		}
	}

	public boolean inBox(float x, float y, float xSmall, float ySmall,
			float xBig, float yBig) {
		return (x >= xSmall && x <= xBig) && (y >= ySmall && y <= yBig);
	}
}
