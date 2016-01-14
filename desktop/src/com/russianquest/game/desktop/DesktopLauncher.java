package com.russianquest.game.desktop;
//xexe
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.russianquest.game.RussianQuest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		String t = "xexe";
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new RussianQuest(), config);
	}
}
