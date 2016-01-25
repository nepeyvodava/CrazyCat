package com.russianquest.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RussianQuest extends ApplicationAdapter {
	SpriteBatch batch;
	Texture cat;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		cat = new Texture("FinalCat-64x128.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(cat, 0, 0);
		batch.end();
	}
}
