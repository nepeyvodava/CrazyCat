package com.russianquest.game.desktop;
//xexe
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.russianquest.game.RussianQuest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Cat";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new RussianQuest(), config);
	}
}
