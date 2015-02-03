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
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Level_One extends BasicGameState {

	Animation charCurrent, charMoveRight, charMoveLeft, charJumpRight,
			charJumpLeft, charStillRight, charStillLeft, charFallRight,
			charFallLeft, coin, powerUp;
	
	private Sound jumpSound;
	
	float charPositionX = 400;
	float charPositionY = 450;


	float jumpPower = 200;
	float normalJumpPower = 200;
	float maxJumpPower = 1200;

	float coinPositionX;
	float coinPositionY;

	float powerUpPositionX;
	float powerUpPositionY;

	Image background, Cloud;
	float backGroundY = -1250;

	Image gameOver;
	Image gameWon;

	Image pauseWindow;
	Image quitGame;
	Image resumeGame;
	float CloudX1 = 300, CloudX2 = 100, CloudX3 = 500, CloudY1 = 300,
			CloudY2 = 200, CloudY3 = 100;

	float EarthY = 450;
	float onCloudY = 900;
	float meters = 0;

	boolean isGameWon = false;
	boolean isGameOver = false;
	boolean wonScreen = false;
	boolean pressEsc = false;
	
	boolean onEarth = true;
	boolean onCloud = false;
    boolean inAir = false;
	boolean jumping = false;
	boolean falling = false;
	
	

	
	Random rndGenerator = new Random();
	int score;

	// tisho
	
	int cloudsNumber;
	
	int firstFloorCloudX;
	int firstFloorCloudY;

	int secondFloorCloudX;
	int secondFloorCloudY;

	int thirdFloorCloudX;
	int thirdFloorCloudY;

	int fourthFloorCloudX;
	int fourthFloorCloudY;
	Image firstFloorCloud, secondFloorCloud, thirdFloorCloud, fourthFloorCloud,
			moveCloud, moveCloud2;

	private float moveCloudX = 200;
	private float moveCloudY = 150;
	private float moveCloud2X = 200;
	private float moveCloud2Y = 400;
	// tisho

	List<PowerUp> powerUpList = new LinkedList<PowerUp>();
	List<BonusCoin> bonusList = new LinkedList<BonusCoin>();
	List<Clouds> removedClouds = new LinkedList<Clouds>();
	List<Clouds> staticClouds = new LinkedList<Clouds>();

	// -------------------------------------------------------
	// Novite metodi se sazdavat POD metoda UPDATE!
	// ---------------------------------------------------

	public Level_One(int state) {

	}

	public int getID() {
		return 1;
	}

	// tisho
	public void drawClouds(GameContainer gc) {

	}

	// tisho

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
		Image[] wings = { new Image("res/wings-1.png"),
				new Image("res/wings-2.png"), new Image("res/wings-3.png") };

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
		
		jumpSound = new Sound("/res/Jump.wav");
		
		coin = new Animation(bonus, 150);
		powerUp = new Animation(wings, 150);

		gameOver = new Image("res/GameOver.png");
		gameWon = new Image("res/emo-01.jpg");

		background = new Image("res/background.png");


		pauseWindow = new Image("res/GamePauseMenu.png");
		quitGame = new Image("res/Button-Turn-Off-icon.png");
		// resumeGame = new Image("res/Button-Play-icon.png");

		// tisho
		// firstFloorCloud = new Image("res/cloud6.png");
		// secondFloorCloud = new Image("res/cloud8.png");
		// thirdFloorCloud = new Image("res/cloud4.png");
		// fourthFloorCloud = new Image("res/cloud9.png");
		//
		// moveCloud = new Image("res/moveCloud.png");
		// moveCloud2 = new Image("res/moveCloud2.png");

		// tisho

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		gc.setShowFPS(false);
		background.draw(0, backGroundY);

		drawPowerUp(gc, powerUpList);

		drawBonusCoin(gc, bonusList);

		drawStaticClouds(gc, staticClouds);



		// coin.draw(coinPositionX, coinPositionY);

		// Printirane na 4ove4eto na ekrana, zaedno s negovite koordinati
		charCurrent.draw(charPositionX, charPositionY);

		// tisho
		// firstFloorCloud.draw(firstFloorCloudX + 20, firstFloorCloudY + 300);
		// firstFloorCloud.draw(firstFloorCloudX + 100, firstFloorCloudY + 300);
		// firstFloorCloud.draw(firstFloorCloudX + 580, firstFloorCloudY + 300);
		// firstFloorCloud.draw(firstFloorCloudX + 660, firstFloorCloudY + 300);
		//
		//
		// secondFloorCloud.draw(secondFloorCloudX + 400, secondFloorCloudY +
		// 180);
		// secondFloorCloud.draw(secondFloorCloudX + 470, secondFloorCloudY +
		// 180);
		// secondFloorCloud.draw(secondFloorCloudX + 540, secondFloorCloudY +
		// 180);
		//
		//
		// thirdFloorCloud.draw(thirdFloorCloudX + 10, thirdFloorCloudY + 100,
		// 96, 96);
		// thirdFloorCloud.draw(thirdFloorCloudX + 55, thirdFloorCloudY + 100,
		// 96, 96);
		// thirdFloorCloud.draw(thirdFloorCloudX + 200, thirdFloorCloudY + 150,
		// 80, 80);

		// fourthFloorCloud.draw(fourthFloorCloudX + 300, fourthFloorCloudY +
		// 30, 100, 96);
		// fourthFloorCloud.draw(fourthFloorCloudX + 350, fourthFloorCloudY +
		// 30, 100, 96);
		// fourthFloorCloud.draw(fourthFloorCloudX + 550, fourthFloorCloudY +
		// 30, 100, 96);
		// fourthFloorCloud.draw(fourthFloorCloudX + 600, fourthFloorCloudY +
		// 30, 100, 96);
		//
		// moveCloud.draw(moveCloudX ,moveCloudY, 85, 85);
		// moveCloud2.draw(moveCloud2X ,moveCloud2Y, 85, 85);
		// tisho

		this.drawScore(g);

		if (pressEsc) {
			pauseWindow.draw(200, 150);
		}

		if (isGameOver) {
			gameOver.draw(80, 50);
		}
		
		if (isGameWon && (charCurrent == charStillLeft || charCurrent == charStillRight) && onEarth && !onCloud) {
			gameWon.draw();
			quitGame.draw(40,520);
			wonScreen = true;
		}
	}

	@SuppressWarnings("static-access")
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		Input input = gc.getInput();
		int posX = Mouse.getX();
		int posY = Mouse.getY();

		// tisho
		moveCloudX += 0.04 * g;
		// moveCloudY -= 0.01 * g;
		if (moveCloudX > 400 || moveCloudX < 200) {
			moveCloudX = 200;
			// moveCloudY = 200;
		}

		moveCloud2Y -= 0.04 * g;

		if (moveCloud2Y < 200) {
			moveCloud2Y = 400;

		}

		fourthFloorCloudX += 0.1 * g;

		// tisho

		if (!pressEsc && !isGameOver && !wonScreen) {


			
			gameMovement();

			// -------------------------------------------------------------------------------------------------
			// Deistviqta, koito se slu4vat pri IZKLIU4ENA Pause se slagat vatre
			// v "if(!pressEsc) {" !

			// ------------------------------------------------------------------------------------------

			if (input.isKeyPressed(input.KEY_ESCAPE)) {
				pressEsc = true;
			}
			
			
			// proverqva dali igrata e prevartqna
			if (meters > 450) {
				isGameWon = true;
			}
			

			// Geroqt umira
			if (charPositionY >= 650) {
				isGameOver = true;
			}

			// Zapo4va dvijenieto v igrata
			if (meters > 10) {
				cloudMovement();
				removingClouds();
			}

			HeroOnEarth();
			
			


			// Sazdavane na oblacite
			if (score % 10000 == 0 && meters >= 0 && !isGameWon) {
				
				cloudsNumber = 3 + rndGenerator.nextInt(3);
				
				for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 80) {	
					staticClouds.add(new Clouds(CloudX1 + plusX, CloudY1, new Image("res/cloud8.png")));										
				}
					
				cloudsNumber = 2 + rndGenerator.nextInt(3);
				
				for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 80) {	
					staticClouds.add(new Clouds(CloudX2 + plusX, CloudY2, new Image("res/cloud6.png")));										
				}
				
				
				cloudsNumber = 3 + rndGenerator.nextInt(2);
				
				for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 80) {	
					staticClouds.add(new Clouds(CloudX3 + plusX, CloudY3, new Image("res/cloud9.png")));									
				}
				
				cloudsNumber = 3 + rndGenerator.nextInt(2);
				
				for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 80) {	
					staticClouds.add(new Clouds(CloudX3 - 100 + plusX, CloudY3 + 200, new Image("res/cloud9.png")));									
				}


			}

			// Geroq pada zaedno s oblaka, na koito stoi
			if (onCloud && meters > 10) {
				charPositionY += 0.03;
			}

			score++;

			// Sazdavane na PowerUp-a
			if (score % 20000 == 0 && !isGameWon) {
				powerUpPositionX = rndGenerator.nextInt(600);
				powerUpPositionY = rndGenerator.nextInt(400);
				powerUpList.add(new PowerUp(powerUpPositionX, powerUpPositionY,
						powerUp));
			}
			if (takenPowerUp()) {
				normalJumpPower += 50;
			}

			// Sazdavane na monetkite
			if (score % 5000 == 0 && !isGameWon) {
				coinPositionX = rndGenerator.nextInt(600);
				coinPositionY = rndGenerator.nextInt(400);
				bonusList
						.add(new BonusCoin(coinPositionX, coinPositionY, coin));
			}
			if (takenBonusCoin()) {
				score += 500;
			}

			// Proverka dali geroqt se namira na zemqta ili ne
			if ((onEarth == true || onCloud == true)
					&& (charCurrent != charJumpRight && charCurrent != charJumpLeft)
					&& !falling && !jumping) {
				
				
				// proverka dali geroq pada ot oblaka
				if (onCloud) {
					isHeroStillOnCloud();
				}

				// Animaicii kogato geroqt e na zemqta ili na oblak
				if (input.isKeyDown(input.KEY_RIGHT) && charPositionX < 744) {

					if (charCurrent == charMoveRight) {
						jumpPower += 0.2;
					} else {
						jumpPower = normalJumpPower;
					}

					charCurrent = charMoveRight;
					charPositionX += g * 0.5;

				} else if (charCurrent == charMoveRight
						&& (!(input.isKeyDown(input.KEY_RIGHT)) || charPositionX >= 744)) {
					charCurrent = charStillRight;
					jumpPower = normalJumpPower;
				}
				
				if (input.isKeyDown(input.KEY_LEFT) && charPositionX > 0) {

					if (charCurrent == charMoveLeft) {
						jumpPower += 0.2;
					} else {
						jumpPower = normalJumpPower;
					}

					charCurrent = charMoveLeft;
					charPositionX -= g * 0.5;

				} else if (charCurrent == charMoveLeft
						&& (!(input.isKeyDown(input.KEY_LEFT)) || charPositionX <= 0)) {
					charCurrent = charStillLeft;
					jumpPower = normalJumpPower;
				}
				
				if (input.isKeyDown(input.KEY_SPACE)
						&& (charCurrent == charStillRight || charCurrent == charMoveRight)) {
					charCurrent = charJumpRight;
					jumpSound.play();
					
					jumping = true;
				}
				if (input.isKeyDown(input.KEY_SPACE)
						&& (charCurrent == charStillLeft || charCurrent == charMoveLeft)) {
					charCurrent = charJumpLeft;
					jumpSound.play();

					jumping = true;
				}

			} else { // Animacii kogato geroqt ska4a:



				
				
				// Proverka dali 4ove4eto trqbava da po4ne da pada
				if (jumpPower <= 0) {
					falling = true;
					jumping = false;
				}
				
				

				
				if (input.isKeyDown(input.KEY_RIGHT)
						&& jumping
						&& charPositionX < 744) {
					charCurrent = charJumpRight;
					charPositionX += g * 0.5;
					charPositionY -= g * 0.3;
					
					jumpPower -= g * 0.3;
					
				} else if ((!(input.isKeyDown(input.KEY_RIGHT)) || charPositionX >= 744)
						&& jumping
						&& charCurrent == charJumpRight) {
					charCurrent = charJumpRight;
					charPositionY -= g * 0.3;
					
					jumpPower -= g * 0.3;
				}
				
				if (input.isKeyDown(input.KEY_LEFT) && jumping
						&& charPositionX > 0) {
					charCurrent = charJumpLeft;
					charPositionX -= g * 0.5;
					charPositionY -= g * 0.3;
					
					jumpPower -= g * 0.3;
					
				} else if ((!(input.isKeyDown(input.KEY_LEFT)) || charPositionX <= 0)
						&& jumping
						&& charCurrent == charJumpLeft) {
					charCurrent = charJumpLeft;
					charPositionY -= g * 0.3;
					
					jumpPower -= g * 0.3;
				}


				// Animacii kogato geroqt pada
				if (falling) {
					
					
					if (HeroOnStaticCloud()) {
						onCloud = true;
					}
					
					if ((charCurrent == charJumpRight && !(input
							.isKeyDown(input.KEY_RIGHT)))
							|| charPositionX >= 744) {
						charCurrent = charFallRight;
						charPositionY += g * 0.3;
					}
					if ((charCurrent == charJumpLeft && !(input
							.isKeyDown(input.KEY_LEFT))) || charPositionX <= 0) {
						charCurrent = charFallLeft;
						charPositionY += g * 0.3;
					}
					
					
					
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
			
			
			// pri pusnata pause
			if (pressEsc) {

				// Izlizane ot igrata
				if ((posX > 300) && (posX < 500)
						&& ((posY > 230) && (posY < 280))) {
					if (Mouse.isButtonDown(0)) {
						System.exit(0);
					}
				}

				// Resume na igrata
				if ((posX > 300) && (posX < 500)
						&& ((posY > 300) && (posY < 350))) {
					if (Mouse.isButtonDown(0)) {
						pressEsc = false;
					}
				}

				// Resume na igrata
				if (input.isKeyPressed(input.KEY_ESCAPE)) {
					pressEsc = false;
				}

			}

			
			
			// Pri zaguba na igrata
			if (isGameOver) {
				// Izlizane ot igrata
				if ((posX > 100) && (posX < 600)
						&& ((posY > 100) && (posY < 450))) {
					if (Mouse.isButtonDown(0)) {
						System.exit(0);
					}
				}
			}
			
			// Pri prevartane na igrata
			if (wonScreen) {
				// Izlizane ot igrata
				if ((posX > 40) && (posX < 90)
						&& ((posY > 30) && (posY < 80))) {
					if (Mouse.isButtonDown(0)) {
						System.exit(0);
					}
				}
			}
		}
	}

	// NAGORE sa metodite na bibliotekata Slick
	// --------------------------------------------------------------------------------------------
	// OTDOLU se sazdavat novite metodi

	// Vizualizirane na Score countera
	public void drawScore(Graphics g) {
		g.setColor(Color.white);
		g.drawString("SCORE " + meters + "  : " + jumpPower, 15, 15);
	}

	// sazdava nepodvijni oblaci
	public void drawStaticClouds(GameContainer gc, List<Clouds> staticClouds) {
		for (Clouds cloud : staticClouds) {
			cloud.cloudImage.draw(cloud.cloudX, cloud.cloudY);
		}
	}

	// Izvar6va spuskaneto na oblacite
	private void cloudMovement() {
		for (Clouds cloud : staticClouds) {
			cloud.cloudY += 0.03;
		}
	}

	// iztriva oblaci
	private void removingClouds() {
		for (Clouds cloud : staticClouds) {

			if (cloud.cloudY >= 750) {

				removedClouds.add(cloud);
			}
		}

		staticClouds.removeAll(removedClouds);
		removedClouds.clear();

	}

	// PowerUp Draw
	public void drawPowerUp(GameContainer gc, List<PowerUp> powerUpList) {
		for (PowerUp powerUp : powerUpList) {
			powerUp.powerUpAnimation.draw(powerUp.powerUpX, powerUp.powerUpY);
		}
	}

	// Taken Power UP
	private boolean takenPowerUp() {
		boolean taken = false;
		PowerUp takenPowerUp = null;

		for (PowerUp powerUp : powerUpList) {

			if (inBox(powerUp.powerUpX, powerUp.powerUpY, charPositionX - 30,
					charPositionY, charPositionX + 30, charPositionY + 65)) {

				takenPowerUp = powerUp;
				powerUpList.remove(takenPowerUp);

				taken = true;

				return taken;
			}
		}
		return taken;
	}

	// BonusCoin Draw
	public void drawBonusCoin(GameContainer gc, List<BonusCoin> bonusList) {
		for (BonusCoin coin : bonusList) {
			coin.bonusCoinAnimation.draw(coin.coinX, coin.coinY);
		}
	}

	// Taken BonusCoin
	private boolean takenBonusCoin() {
		boolean taken = false;
		BonusCoin takenBonusCoin = null;

		for (BonusCoin coin : bonusList) {

			if (inBox(coin.coinX, coin.coinY, charPositionX - 30,
					charPositionY, charPositionX + 30, charPositionY + 65)) {

				takenBonusCoin = coin;
				bonusList.remove(takenBonusCoin);

				taken = true;

				return taken;
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
				jumpPower = normalJumpPower;
			}

			if (falling && charCurrent == charFallLeft) {
				charCurrent = charStillLeft;
				falling = false;
				jumpPower = normalJumpPower;
			}
		}

	}
	
	
	private void isHeroStillOnCloud() {
		boolean isStillOnCloud = false;		
		
		for (Clouds cloud : staticClouds) {
			if (inBox(charPositionX, charPositionY, cloud.cloudX - 5, cloud.cloudY - 20, cloud.cloudX + 100, cloud.cloudY)) {
				isStillOnCloud = true;
			}						
		}	
		
		if (!isStillOnCloud) {
			onCloud = false;
			falling = true;
		}
	}

	// Proverqva dali geroqt e varhu oblak
	private boolean HeroOnStaticCloud() {

		boolean onStaticCloud = false;

		for (Clouds cloud : staticClouds) {

			if (inBox(charPositionX, charPositionY, cloud.cloudX - 5, cloud.cloudY - 20, cloud.cloudX + 100, cloud.cloudY)) {
				onStaticCloud = true;
				
				if (falling && charCurrent == charFallRight) {
					charCurrent = charStillRight;
					falling = false;
					jumpPower = normalJumpPower;
				}
				
				if (falling && charCurrent == charFallLeft) {
					charCurrent = charStillLeft;
					falling = false;
					jumpPower = normalJumpPower;
				}
			}			
		}

		return onStaticCloud;
	}

	
	
	
	
	// Proverqva dali geroqt e gore na ekrana
	private void gameMovement() {

		if (charPositionY <= 0 && !falling) {
			charPositionY = -30;
			jumpPower--;
			meters++;

			if (backGroundY < -650 && !isGameWon) {
				backGroundY++;
				EarthY++;
			}

			if (backGroundY < 0 && isGameWon) {
				backGroundY++;
				normalJumpPower = 1000;
			}
		}

		
		// Ako igrata ne e prevartqna
		if (meters > 10 && backGroundY != -650) {
			backGroundY += 0.01;
			EarthY += 0.01;
		}

		if (backGroundY >= -650 && !isGameWon) {
			backGroundY = -650;
		}
		
		// Ako igrata e prevartqna
		if (isGameWon && backGroundY != 0) {
			backGroundY += 0.01;
			EarthY += 0.01;
		}
		
		if (backGroundY >= 0 && isGameWon) {
			backGroundY = 0;
			EarthY = 500;
		}
		
	}

	public boolean inBox(float x, float y, float xSmall, float ySmall,
			float xBig, float yBig) {
		return (x >= xSmall && x <= xBig) && (y >= ySmall && y <= yBig);
	}
}
