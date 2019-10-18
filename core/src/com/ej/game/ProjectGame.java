package com.ej.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.BitSet;
import java.util.Random;

import javax.swing.event.ChangeListener;

import static java.lang.StrictMath.round;

public class ProjectGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture chara;
	private float charaY = 0;
	private float charaX = 0;
	private float charaVelo = 0;

	ShapeRenderer shapeRenderer;
	Circle enemyC;
	Rectangle leftRect;
	Circle heroC;
	Rectangle bottomRect;
	Circle goldCoinC;


	//
	Texture enemy;
	private float enemyY = 0;
	private float enemyX = 0;
	private float enemyVelo = 10;

	//
	Texture background1a;
	Texture background1b;
	private float b1Velocity = 2;
	private float b1X = 0;

	//
	Texture background2a;
	Texture background2b;
	private double b2Velocity = 0.5;
	private float b2X = 0;

	//
	Texture background3a;
	Texture background3b;
	private double b3Velocity = 0.25;
	private float b3X = 0;

	Texture startBtn;

	private float screenHeight;
	private float screenWidth;

	int winScore = 0;
	int coinScore = 0;
	BitmapFont fontW;
	BitmapFont fontC;
	BitmapFont fontT; //for time

	int gameSwitch = 0;


	Random randomGenerator;

	//Gold Coin Animation
	private TextureAtlas goldcoinAtlas;
	private Animation animation;
	private float timePassed = 0;
	float goldCoinX = 0;
	float goldCoinY = 0;


	Sound mp3Sound;
	Sound coinCollect;
	Sound enemyTouch;
	Sound floorTouch;
	Music backgroundMusic;


	@Override
	public void create () {
		batch = new SpriteBatch();
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();


		chara = new Texture("chara.png");
		charaY = screenHeight / 2 - chara.getHeight() / 2;
		charaX = screenWidth / 2 - chara.getWidth() / 2;

		enemy = new Texture("enemy.png");
		enemyX = screenWidth /2 + enemy.getWidth() /2 * 2;




		background = new Texture("fback.png");

		background1a = new Texture("forest.png");
		background1b = new Texture("forest.png");

		background2a = new Texture("fcc.png");
		background2b = new Texture("fcc.png");

		background3a = new Texture("fc.png");
		background3b = new Texture("fc.png");

		startBtn = new Texture("startbtn.png");




        shapeRenderer = new ShapeRenderer();
		randomGenerator = new Random();
		enemyC = new Circle();
		leftRect = new Rectangle();
		heroC = new Circle();
		bottomRect = new Rectangle();
		goldCoinC = new Circle();

		fontW = new BitmapFont();
		fontW.setColor(Color.RED);
		fontW.getData().setScale(8);
		fontC = new BitmapFont();
		fontC.setColor(Color.GOLD);
		fontC.getData().setScale(8);
		fontT = new BitmapFont();
		fontT.setColor(Color.WHITE);
		fontT.getData().setScale(7);


		//Gold Coin Animation
		goldcoinAtlas = new TextureAtlas(Gdx.files.internal("goldcoin.atlas"));
		animation = new Animation(1/13f, goldcoinAtlas.getRegions());
		goldCoinX = screenWidth /2 + enemy.getWidth() /2 * 2;


		mp3Sound = Gdx.audio.newSound(Gdx.files.internal("jumplift.wav"));
		coinCollect = Gdx.audio.newSound(Gdx.files.internal("coincollect.wav"));
		enemyTouch = Gdx.audio.newSound(Gdx.files.internal("enemytouch.wav"));
		floorTouch = Gdx.audio.newSound(Gdx.files.internal("floortouch.wav"));
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundmusic.mp3"));
		backgroundMusic.setLooping(true);



	}




	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();




		//BACKGROUND
		//backgroundMusic.play();

		batch.draw(background, 0, 0, screenWidth, screenHeight);

		batch.draw(background3a, b3X, 300, screenWidth + screenWidth + 200, screenHeight);
		batch.draw(background3b, b3X + screenWidth * 2 + 175, 0, screenWidth + screenWidth + 200, screenHeight);

		batch.draw(background2a, b2X, 0, screenWidth + screenWidth + 200, screenHeight);
		batch.draw(background2b, b2X + screenWidth * 2 + 175, 0, screenWidth + screenWidth + 200, screenHeight);

		batch.draw(background1a, b1X, 0, screenWidth + screenWidth + 200, screenHeight);
		batch.draw(background1b, 0 + screenWidth * 2 + 175, 0, screenWidth + screenWidth + 200, screenHeight);
		// BACKGROUND


		//GAME SWITCH 0 ----------------------------------------
		if ( gameSwitch == 0){


			batch.draw(startBtn, 350, 1500, startBtn.getWidth() * 3, startBtn.getHeight() * 3 );
			//batch.draw((TextureRegion) animation.getKeyFrame(timePassed, true), 200, 2300, 125, 125);


			if (Gdx.input.justTouched()) {

				gameSwitch = 1;

			}


			backgroundMusic.pause();
		}

//
		//GAME SWITCH 1 -----------------------------------------
		if ( gameSwitch == 1) {


			backgroundMusic.play();

			timePassed += Gdx.graphics.getDeltaTime(); //time passing tracked

			if (Gdx.input.justTouched() && charaY < 2300) {
				charaVelo = -35;
				//enemyX = screenWidth /2 + enemy.getWidth() /2 * 2; // brings enemy back to right side

				// mp3Sound.play();
			}




			batch.draw(chara, charaX, charaY, 350, 300);  // main character

			batch.draw(enemy, enemyX, enemyY, 250, 200);  // enemy

			batch.draw((TextureRegion) animation.getKeyFrame(timePassed, true), goldCoinX, goldCoinY, 150, 150); // gold coin

			fontW.draw(batch, String.valueOf(winScore)+" x", 100, 2350); // win score

			fontC.draw(batch, String.valueOf(round(coinScore)) + " x", 350, 2350);  // coin score

			// fontT.draw(batch, String.valueOf(round(timePassed) * 1.00) + " x", 350, 2350); // timer
			fontT.draw(batch, String.format("%.2f", timePassed / 60) + " t", 1200, 2350);


			if (charaY > -20 || charaVelo < 0) { // Gravity and stop after touching ground for Main character
				charaVelo = charaVelo + 2;
				charaY = charaY - charaVelo;
			}

			//shape render
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(Color.RED);

			enemyC.set(enemyX + 125, enemyY + enemy.getHeight() / 2 - 75, enemy.getWidth() / 4 - 40);  // the circle overlay on enemy
			//shapeRenderer.circle(enemyC.x, enemyC.y, enemyC.radius);

			leftRect.set(-200, 0, 8, 5000);   // left rectangle end overlay  //make X to 0 in order for it to be visible again.
			//shapeRenderer.rect(leftRect.x, leftRect.y, leftRect.width, leftRect.height);

			heroC.set(charaX + 165, charaY + chara.getHeight() / 2, chara.getWidth() / 4);
			//shapeRenderer.circle(heroC.x, heroC.y, heroC.radius);

			bottomRect.set(0, 0, 5000, 25);   // left rectangle end overlay  //make X to 0 in order for it to be visible again.
			//shapeRenderer.rect(bottomRect.x, bottomRect.y, bottomRect.width, bottomRect.height);

			goldCoinC.set(goldCoinX + 60, goldCoinY  + 60, chara.getWidth() / 6);
			//shapeRenderer.circle(goldCoinC.x , goldCoinC.y, goldCoinC.radius);


			if (Intersector.overlaps(enemyC, leftRect)) {    // when Enemy intersects with leftmost side
				enemyX = screenWidth / 2 + enemy.getWidth() / 2 * 2 * 2;
				enemyY = randomGenerator.nextFloat() * Gdx.graphics.getHeight();
				winScore++;

			}


			//on LOSE
			if (Intersector.overlaps(enemyC, heroC) || Intersector.overlaps(heroC, bottomRect)){  // when Hero intersects with Enemy or Bottom // ON LOSING

				enemyX = screenWidth / 2 + enemy.getWidth() / 2 * 2 * 2;   // reset enemy X axis (to the right most)
                enemyY = randomGenerator.nextFloat() * Gdx.graphics.getHeight();// randomizes enemy Y so it spawns in a different place.
				charaY = screenHeight / 2 - chara.getHeight() / 2;		// resets hero X (to the middle)
				gameSwitch = 0;
				winScore = 0;
				coinScore = 0;

				goldCoinX = screenWidth / 2 * 2;
				goldCoinY = randomGenerator.nextFloat() * Gdx.graphics.getHeight();

				timePassed = 0;
			}

			if (Intersector.overlaps(enemyC, heroC)) {
				enemyTouch.play();
			}
			if (Intersector.overlaps(heroC, bottomRect)) {
				floorTouch.play();
			}

			if (Intersector.overlaps(goldCoinC, leftRect)){
				goldCoinX = screenWidth / 2 * 2;
				goldCoinY = randomGenerator.nextFloat() * Gdx.graphics.getHeight();

			}


			if (Intersector.overlaps(heroC, goldCoinC)){ // when Hero intersects with Gold Coin

				coinScore++;
				goldCoinX = screenWidth / 2 * 2;
				goldCoinY = randomGenerator.nextFloat() * Gdx.graphics.getHeight();

				coinCollect.play();
			}



		}	// end of gameSwitch 1 IF statement

		batch.end();
		shapeRenderer.end();




		b1X -= b1Velocity;
		b2X -= b2Velocity;
		b3X -= b3Velocity;
		float randomEnemyVelocity = 7 + randomGenerator.nextFloat() * (22 - 7);
		enemyX -= randomEnemyVelocity ;
		//enemyY -= 50 + randomGenerator.nextFloat() * (200 - 50);
		//enemyY += 50 + randomGenerator.nextFloat() * (200 - 50) ;
		goldCoinX -= 7;



		if (b1X + screenWidth == 0){    // bug right here, background keeps going
			b1X = 0; }
		if (b2X + screenWidth == 0){
			b2X = 0; }
		if (b3X + screenWidth == 0){
			b3X = 0; }






        Gdx.app.log("Y1 : ", String.valueOf(charaY));
		Gdx.app.log("VV : ", String.valueOf(charaVelo));
		Gdx.app.log("Enemy X: ", String.valueOf(enemyX));
        Gdx.app.log("randomEnemyVelocity ", String.valueOf(randomEnemyVelocity));
        Gdx.app.log("Score: ", String.valueOf(winScore));



	}





	@Override
	public void dispose () {
		batch.dispose();



	}
}


