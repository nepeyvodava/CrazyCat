package com.crazycat.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;

public class CrazyCat extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture catImage;
	Sound meowKickCat;
	Rectangle cat;
	Vector3 touchPos;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		touchPos = new Vector3();

		catImage = new Texture("FinalCat-64x128.png");

		meowKickCat = Gdx.audio.newSound(Gdx.files.internal("meowKickCat.wav"));

		cat = new Rectangle();
		cat.x = 0;
		cat.y = 20;
		cat.width = 64;
		cat.height = 128;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(catImage, cat.x, cat.y);
		batch.end();

		if(Gdx.input.isTouched()){
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			cat.x = (int) (touchPos.x - 64 / 2);
			cat.y = (int) (touchPos.y - 128 / 2);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A)) cat.x -= 200*Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.D)) cat.x += 200*Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.S)) cat.y -= 200*Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.W)) cat.y += 200*Gdx.graphics.getDeltaTime();

		if(cat.x < 0) cat.x = 0;
		if(cat.x > (800 - 64)) cat.x = 800 - 64;
		if(cat.y < 0) cat.y = 0;
		if(cat.y > (480 - 128)) cat.y = 480 - 128;
	}
}
