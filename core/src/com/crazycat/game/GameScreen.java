package com.crazycat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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

public class GameScreen implements Screen {

	final CrazyCat game;
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture catImage;
	Texture miteImage;
	Sound meowCat;
	Music backGroundMusic;
	Rectangle cat;
	Vector3 touchPos;
	Array<Rectangle> mitedrops;
	long lastMiteTime;
	int mitesGathered;
	

	public GameScreen (final CrazyCat gam) {
		this.game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		touchPos = new Vector3();

		catImage = new Texture("FinalCat-64x128.png");
		miteImage = new Texture("mite-64x64.png");

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
		lastMiteTime = TimeUtils.nanoTime();
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.draw(game.batch, "MEAW: " + mitesGathered, 0, 480);
		game.batch.draw(catImage, cat.x, cat.y);
		for(Rectangle mitedrop: mitedrops){
			game.batch.draw(miteImage, mitedrop.x, mitedrop.y);
		}
		game.batch.end();

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

		if(TimeUtils.nanoTime() - lastMiteTime > 1000000000 ) spawnMiteDrop();

		Iterator<Rectangle> iter = mitedrops.iterator();
		while (iter.hasNext()){
			Rectangle mitedrop = iter.next();
			mitedrop.x -= 150 * Gdx.graphics.getDeltaTime();
			if (mitedrop.x + 64 < 0) iter.remove();
			if (mitedrop.overlaps(cat)){
				mitesGathered++;
				meowCat.play();
				iter.remove();
			}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose(){
		catImage.dispose();
		miteImage.dispose();
		meowCat.dispose();
		backGroundMusic.dispose();
		batch.dispose();
	}

	@Override
	public void show() {
		backGroundMusic.play();
		backGroundMusic.setVolume(0.35f);
		backGroundMusic.setLooping(true);
	}
}
