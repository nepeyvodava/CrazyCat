package com.crazycat.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import com.badlogic.gdx.math.Rectangle;
import java.util.Iterator;

public class CrazyCat extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture catImage;
	Texture miteImage;
	Sound meowCat;
	Music backGroundMusic;
	Rectangle cat;
	Vector3 touchPos;
	Array<Rectangle> mitedrops;
	long lastDropTime;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		touchPos = new Vector3();

		catImage = new Texture("FinalCat-64x128.png");
		miteImage = new Texture("mite-alt-64x64.png");

		meowCat = Gdx.audio.newSound(Gdx.files.internal("meowCat.wav"));
		backGroundMusic = Gdx.audio.newMusic(Gdx.files.internal("level_1_music.mp3"));

		cat = new Rectangle();
		cat.x = 0;
		cat.y = 20;
		cat.width = 64;
		cat.height = 128;

		mitedrops = new Array<Rectangle>();
		spawnMiteDrop();
	}

	private void spawnMiteDrop(){
		Rectangle mitedrop = new Rectangle();
		mitedrop.x = 800;
		mitedrop.y = MathUtils.random(0, 480 - 64);
		mitedrop.width = 64;
		mitedrop.height = 64;
		mitedrops.add(mitedrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		backGroundMusic.play();
		backGroundMusic.setVolume((float)0.5);
		backGroundMusic.setLooping(true);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(catImage, cat.x, cat.y);
		for(Rectangle mitedrop: mitedrops){
			batch.draw(miteImage, mitedrop.x, mitedrop.y);
		}
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

		if(TimeUtils.nanoTime() - lastDropTime > 1000000000 ) spawnMiteDrop();

		Iterator<Rectangle> iter = mitedrops.iterator();
		while (iter.hasNext()){
			Rectangle mitedrop = iter.next();
			mitedrop.x -= 200 * Gdx.graphics.getDeltaTime();
			if (mitedrop.x + 64 < 0) iter.remove();
			if (mitedrop.overlaps(cat)){
				meowCat.play();
				iter.remove();
			}
		}
	}

	@Override
	public void dispose(){
		super.dispose();
		catImage.dispose();
		miteImage.dispose();
		meowCat.dispose();
		backGroundMusic.dispose();
		batch.dispose();
	}
}
