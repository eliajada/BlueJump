package com.ej.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ProjectGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture chara;
	float charaY;
	float charaVelo = 0;

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

	private float screenHeight;
	private float screenWidth;



	@Override
	public void create () {
		batch = new SpriteBatch();

		chara = new Texture("chara.png");
		charaY = screenHeight / 2 - chara.getHeight() / 2;

		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();

		background = new Texture("fback.png");

		background1a = new Texture("forest.png");
		background1b = new Texture("forest.png");

		background2a = new Texture("fcc.png");
		background2b = new Texture("fcc.png");

		background3a = new Texture("fc.png");
		background3b = new Texture("fc.png");






	}




	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();



		if (Gdx.input.justTouched()){
			charaVelo = -35;
		}


		batch.draw(background, 0, 0, screenWidth, screenHeight);

		batch.draw(background3a, b3X, 300, screenWidth + screenWidth + 200, screenHeight);
		batch.draw(background3b, b3X + screenWidth * 2 + 175, 0, screenWidth + screenWidth + 200, screenHeight);

		batch.draw(background2a, b2X, 0, screenWidth + screenWidth + 200, screenHeight);
		batch.draw(background2b, b2X + screenWidth* 2 + 175, 0, screenWidth + screenWidth + 200, screenHeight);

		batch.draw(background1a, b1X, 0, screenWidth + screenWidth + 200, screenHeight);
		batch.draw(background1b, 0 + screenWidth * 2 + 175, 0, screenWidth + screenWidth + 200, screenHeight);

		batch.draw(chara, screenWidth / 2 - chara.getWidth() / 2 , charaY, 350, 300);


		if (charaY > 0 || charaVelo < 0) {
			charaVelo = charaVelo + 2;
			charaY = charaY - charaVelo;
		}



		batch.end();

		b1X -= b1Velocity;
		b2X -= b2Velocity;
		b3X -= b3Velocity;

		if (b1X + screenWidth == 0){    // bug right here, background keeps going
			b1X = 0; }
		if (b2X + screenWidth == 0){
			b2X = 0; }
		if (b3X + screenWidth == 0){
			b3X = 0; }

        Gdx.app.log("Y : ", String.valueOf(charaY));
		Gdx.app.log("VV : ", String.valueOf(charaVelo));
	}





	@Override
	public void dispose () {
		batch.dispose();



	}
}
