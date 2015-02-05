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
			CloudY2 = 200, CloudY3 = 100, CloudX4 = 0, CloudY4 = 20,
			CloudX5 = 350, CloudY5 = -100, CloudX6 = 0, CloudY6 = -260,
			CloudX7 = +680, CloudY7 = -300, CloudX8 = +270, CloudY8 = -450,
			CloudX9 = +520, CloudY9 = -530, CloudX10 = +300, CloudY10 = -700,
			CloudX11 = +20, CloudY11 = -850, CloudX12 = 300, CloudY12 = -930;

	float EarthY = 450;
	float onCloudY = 900;
	float meters = 0;

	boolean firstClouds = true;
	boolean mustDrawCoin = false;
	boolean mustDrawWings = false;

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
	float score;

	int cloudsNumber;


	Image firstFloorCloud, secondFloorCloud, thirdFloorCloud, fourthFloorCloud,
			moveCloud, moveCloud2;



	List<PowerUp> powerUpList = new LinkedList<PowerUp>();
	List<BonusCoin> bonusList = new LinkedList<BonusCoin>();
	List<Clouds> removedClouds = new LinkedList<Clouds>();
	List<Clouds> staticClouds = new LinkedList<Clouds>();

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

		jumpSound = new Sound("/res/spin_jump.wav");

		coin = new Animation(bonus, 150);
		powerUp = new Animation(wings, 150);

		gameOver = new Image("res/GameOver.png");
		gameWon = new Image("res/emo-01.jpg");

		background = new Image("res/background.png");

		pauseWindow = new Image("res/GamePauseMenu.png");
		quitGame = new Image("res/Button-Turn-Off-icon.png");

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		gc.setShowFPS(false);
		background.draw(0, backGroundY);

		drawPowerUp(gc, powerUpList);

		drawBonusCoin(gc, bonusList);

		drawStaticClouds(gc, staticClouds);

		// Printirane na 4ove4eto na ekrana, zaedno s negovite koordinati
		charCurrent.draw(charPositionX, charPositionY);

		this.drawScore(g);

		if (pressEsc) {
			pauseWindow.draw(200, 150);
		}

		if (isGameOver) {
			gameOver.draw(80, 50);
		}

		if (isGameWon
				&& (charCurrent == charStillLeft || charCurrent == charStillRight)
				&& onEarth && !onCloud) {
			gameWon.draw();
			quitGame.draw(40, 520);
			wonScreen = true;
		}
	}

	@SuppressWarnings("static-access")
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		Input input = gc.getInput();
		int posX = Mouse.getX();
		int posY = Mouse.getY();



		if (!pressEsc && !isGameOver && !wonScreen) {

			gameMovement();

			if (input.isKeyPressed(input.KEY_ESCAPE)) {
				pressEsc = true;
			}

			// proverqva dali igrata e prevartqna
			if (meters > 1000) {
				isGameWon = true;
			}

			// Geroqt umira
			if (charPositionY >= 650) {
				isGameOver = true;
			}

			// Zapo4va dvijenieto v igrata
			if (meters > 20) {
				cloudMovement();
				removingClouds();
			}

			heroOnEarth();
			
			// Geroq pada zaedno s oblaka, na koito stoi
			if (onCloud && meters > 20) {
				heroMovingWhenOnCloud();
			}
			
			

			// Sazdavane na oblacite
			if (staticClouds.size() < 13 && !isGameWon) {

				if (firstClouds) {

					cloudsNumber = 4;
					for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 80) {
						staticClouds.add(new Clouds(CloudX1 + plusX, CloudY1,
								new Image("res/cloud8.png")));
					}

					cloudsNumber = 2;
					for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 80) {
						staticClouds.add(new Clouds(CloudX2 + plusX, CloudY2,
								new Image("res/cloud6.png")));
					}

					cloudsNumber = 3;
					for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 80) {
						staticClouds.add(new Clouds(CloudX3 + plusX, CloudY3,
								new Image("res/cloud9.png")));
					}

					firstClouds = false;
				}

				cloudsNumber = 2 + rndGenerator.nextInt(3);
				for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 80) {
					staticClouds.add(new Clouds(CloudX4 + plusX, CloudY4,
							new Image("res/cloud4.png")));
				}

				cloudsNumber = 2 + rndGenerator.nextInt(3);
				for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 80) {
					staticClouds.add(new Clouds(CloudX5 + plusX, CloudY5,
							new Image("res/cloud7.png")));
				}

				cloudsNumber = 3 + rndGenerator.nextInt(3);
				for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 80) {
					staticClouds.add(new Clouds(CloudX6 + plusX, CloudY6,
							new Image("res/cloud5.png")));
				}

				cloudsNumber = 1 + rndGenerator.nextInt(2);
				for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 60) {
					staticClouds.add(new Clouds(CloudX7 + plusX, CloudY7,
							new Image("res/cloud10.png")));
				}

				cloudsNumber = 3;
				for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 60) {
					staticClouds.add(new Clouds(CloudX8 + plusX, CloudY8,
							new Image("res/cloud8.png")));

				}

				staticClouds.add(new Clouds(CloudX9, CloudY9, new Image(
						"res/cloud7.png")));

				staticClouds.add(new Clouds(CloudX10, CloudY10, new Image(
						"res/moveCloud.png")));

				staticClouds.add(new Clouds(CloudX10 + 60, CloudY10, new Image(
						"res/moveCloud.png")));

				cloudsNumber = 2 + rndGenerator.nextInt(2);
				for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 60) {
					staticClouds.add(new Clouds(CloudX11 + plusX, CloudY11,
							new Image("res/cloud8.png")));
				}

				cloudsNumber = 2 + rndGenerator.nextInt(3);
				for (int i = 0, plusX = 0; i < cloudsNumber; i++, plusX += 60) {
					staticClouds.add(new Clouds(CloudX12 + plusX, CloudY12,
							new Image("res/cloud6.png")));
				}

			}


			// Sazdavane na PowerUp-a
			if (mustDrawWings && !isGameWon && score > 0) {
				powerUpPositionX = rndGenerator.nextInt(600);
				powerUpPositionY = rndGenerator.nextInt(400);
				powerUpList.add(new PowerUp(powerUpPositionX, 0, powerUp));

				mustDrawWings = false;
			}
			if (takenPowerUp()) {
				normalJumpPower += 50;
			}

			// Sazdavane na monetkite
			if (mustDrawCoin && !isGameWon && score > 0) {
				coinPositionX = rndGenerator.nextInt(600);
				coinPositionY = rndGenerator.nextInt(400);
				bonusList.add(new BonusCoin(coinPositionX, 0, coin));

				mustDrawCoin = false;
			}
			if (takenBonusCoin()) {
				score += 50;
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

				if (input.isKeyDown(input.KEY_RIGHT) && jumping
						&& charPositionX < 744) {
					charCurrent = charJumpRight;
					charPositionX += g * 0.5;
					charPositionY -= g * 0.3;

					jumpPower -= g * 0.3;

				} else if ((!(input.isKeyDown(input.KEY_RIGHT)) || charPositionX >= 744)
						&& jumping && charCurrent == charJumpRight) {
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
						&& jumping && charCurrent == charJumpLeft) {
					charCurrent = charJumpLeft;
					charPositionY -= g * 0.3;

					jumpPower -= g * 0.3;
				}

				// Animacii kogato geroqt pada
				if (falling) {

					if (heroOnStaticCloud()) {
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
				if ((posX > 40) && (posX < 90) && ((posY > 30) && (posY < 80))) {
					if (Mouse.isButtonDown(0)) {
						System.exit(0);
					}
				}
			}
		}
	}

	// Vizualizirane na Score countera
	public void drawScore(Graphics g) {
		g.setColor(Color.white);
		g.drawString("SCORE " + (int) score, 15, 15);
	}

	// sazdava nepodvijni oblaci
	public void drawStaticClouds(GameContainer gc, List<Clouds> staticClouds) {
		for (Clouds cloud : staticClouds) {
			cloud.cloudImage.draw(cloud.cloudX, cloud.cloudY);
		}
	}

	
	// Izvar6va spuskaneto na geroq, dokato e na oblak
	private void heroMovingWhenOnCloud() {
		
		if (meters <= 600) {
			charPositionY += 0.13;
		}
		

		if (meters > 600 && !isGameWon) {
			charPositionY += 0.17;
		}
		
		
	}
	
	
	
	// Izvar6va spuskaneto na oblacite
	private void cloudMovement() {
		
		
		if (meters <= 600) {
			for (Clouds cloud : staticClouds) {
				cloud.cloudY += 0.13;

			}

			for (BonusCoin bonusCoin : bonusList) {
				bonusCoin.coinY += 0.13;
			}

			for (PowerUp powerWings : powerUpList) {
				powerWings.powerUpY += 0.13;
			}

			meters += 0.0015;
			score += 0.0015;
		}
		
		if (meters > 600 && !isGameWon) {
			for (Clouds cloud : staticClouds) {
				cloud.cloudY += 0.17;

			}

			for (BonusCoin bonusCoin : bonusList) {
				bonusCoin.coinY += 0.17;
			}

			for (PowerUp powerWings : powerUpList) {
				powerWings.powerUpY += 0.17;
			}

			meters += 0.0023;
			score += 0.0023;
		}

	}

	// iztriva oblacite, koito sa izvan ekrana
	private void removingClouds() {
		for (Clouds cloud : staticClouds) {

			if (cloud.cloudY >= 750) {

				removedClouds.add(cloud);
			}

			if (isGameWon && cloud.cloudY < 200 && backGroundY > -400) {
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
	private void heroOnEarth() {

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
			if (inBox(charPositionX, charPositionY, cloud.cloudX - 5,
					cloud.cloudY - 20, cloud.cloudX + 100, cloud.cloudY)) {
				isStillOnCloud = true;
			}
		}

		if (!isStillOnCloud) {
			onCloud = false;
			falling = true;
		}
	}

	// Proverqva dali geroqt e varhu oblak
	private boolean heroOnStaticCloud() {

		boolean onStaticCloud = false;

		for (Clouds cloud : staticClouds) {

			if (inBox(charPositionX, charPositionY, cloud.cloudX - 5,
					cloud.cloudY - 20, cloud.cloudX + 100, cloud.cloudY)) {
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
			score++;

			if ((int) meters % 120 == 0) {
				mustDrawCoin = true;
			}

			if ((int) meters % 300 == 0 && meters > 0) {
				mustDrawWings = true;
			}

			for (Clouds cloud : staticClouds) {
				cloud.cloudY++;
			}
			
			for (BonusCoin coin : bonusList) {
				coin.coinY++;
			}
			
			for (PowerUp power : powerUpList) {
				power.powerUpY++;
			}

			if (backGroundY < -650 && !isGameWon) {
				backGroundY++;
				EarthY++;
			}

			if (backGroundY < 0 && isGameWon) {
				backGroundY++;

			}
		}

		// Ako igrata ne e prevartqna
		if (meters > 20 && backGroundY != -650) {
			backGroundY += 0.08;
			EarthY += 0.08;

		}

		if (backGroundY >= -650 && !isGameWon) {
			backGroundY = -650;
		}

		// Ako igrata e prevartqna
		if (isGameWon && backGroundY != 0) {
			backGroundY += 0.08;
			EarthY += 0.08;

		}

		if (backGroundY >= 0 && isGameWon) {
			backGroundY = 0;
			EarthY = 500;
		}
		
		
		// proverka za izrisuvane na mometa/krilca
		if (meters % 60 == 0) {
			mustDrawCoin = true;
		}

		if (meters % 200 == 0 && meters > 0) {
			mustDrawWings = true;
		}

	}

	public boolean inBox(float x, float y, float xSmall, float ySmall,
			float xBig, float yBig) {
		return (x >= xSmall && x <= xBig) && (y >= ySmall && y <= yBig);
	}
}
