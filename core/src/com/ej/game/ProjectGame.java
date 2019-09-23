package com.ej.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.BitSet;
import java.util.Random;

import javax.swing.event.ChangeListener;

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


	//
	Texture enemy;
	float enemyY;
	private float enemyX;
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
	BitmapFont font;

	int gameSwitch = 0;


	Random randomGenerator;

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

		font = new BitmapFont();
		font.setColor(Color.RED);
		font.getData().setScale(12);


	}




	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		//BACKGROUND
		batch.draw(background, 0, 0, screenWidth, screenHeight);

		batch.draw(background3a, b3X, 300, screenWidth + screenWidth + 200, screenHeight);
		batch.draw(background3b, b3X + screenWidth * 2 + 175, 0, screenWidth + screenWidth + 200, screenHeight);

		batch.draw(background2a, b2X, 0, screenWidth + screenWidth + 200, screenHeight);
		batch.draw(background2b, b2X + screenWidth * 2 + 175, 0, screenWidth + screenWidth + 200, screenHeight);

		batch.draw(background1a, b1X, 0, screenWidth + screenWidth + 200, screenHeight);
		batch.draw(background1b, 0 + screenWidth * 2 + 175, 0, screenWidth + screenWidth + 200, screenHeight);
		// BACKGROUND

		//GAME SWITCH 0
		if ( gameSwitch == 0){
			batch.draw(startBtn, 350, 1000, startBtn.getWidth() * 3, startBtn.getHeight() * 3 );

			if (Gdx.input.justTouched()) {

				gameSwitch = 1;
				//startBtn = new Texture("chara.png");
			}
		}

		//GAME SWITCH 1
		if ( gameSwitch == 1) {

			if (Gdx.input.justTouched()) {
				charaVelo = -35;
				//enemyX = screenWidth /2 + enemy.getWidth() /2 * 2; // brings enemy back to right side
			}


			batch.draw(enemy, enemyX, enemyY, 250, 200);  // enemy

			batch.draw(chara, charaX, charaY, 350, 300);  // main character

			font.draw(batch, String.valueOf(winScore), 100, 2250); // win score


			if (charaY > -20 || charaVelo < 0) { // Gravity and stop after touching ground for Main character
				charaVelo = charaVelo + 2;
				charaY = charaY - charaVelo;
			}

			//shape render
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(Color.RED);

			enemyC.set(enemyX + 125, enemyY + enemy.getHeight() / 2 - 75, enemy.getWidth() / 4);  // the circle overlay on enemy
			//shapeRenderer.circle(enemyC.x, enemyC.y, enemyC.radius);

			leftRect.set(-200, 0, 8, 5000);   // left rectangle end overlay  //make X to 0 in order for it to be visible again.
			//shapeRenderer.rect(leftRect.x, leftRect.y, leftRect.width, leftRect.height);

			heroC.set(charaX + 165, charaY + chara.getHeight() / 2, chara.getWidth() / 4);
			//shapeRenderer.circle(heroC.x, heroC.y, heroC.radius);

			bottomRect.set(0, 0, 5000, 25);   // left rectangle end overlay  //make X to 0 in order for it to be visible again.
			shapeRenderer.rect(bottomRect.x, bottomRect.y, bottomRect.width, bottomRect.height);


			if (Intersector.overlaps(enemyC, leftRect)) {    // when Enemy intersects with leftmost side
				enemyX = screenWidth / 2 + enemy.getWidth() / 2 * 2 * 2;
				enemyY = randomGenerator.nextFloat() * Gdx.graphics.getHeight();
				winScore++;

			}

			if (Intersector.overlaps(enemyC, heroC) || Intersector.overlaps(heroC, bottomRect)){  // when Hero intersects with Enemy or Bottom // ON LOSING

				enemyX = screenWidth / 2 + enemy.getWidth() / 2 * 2 * 2;   // reset enemy X axis (to the right most)
				charaY = screenHeight / 2 - chara.getHeight() / 2;		// resets hero X (to the middle)
				gameSwitch = 0;
				winScore = 0;
			}



		}	// end of gameSwitch 1 IF statement

		batch.end();
		shapeRenderer.end();




		b1X -= b1Velocity;
		b2X -= b2Velocity;
		b3X -= b3Velocity;
		float randomEnemyVelocity = 7 + randomGenerator.nextFloat() * (22 - 7);
		enemyX -= randomEnemyVelocity ;



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
