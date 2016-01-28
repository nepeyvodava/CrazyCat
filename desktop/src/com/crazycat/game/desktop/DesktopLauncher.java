package com.crazycat.game.desktop;
//xexe
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.crazycat.game.CrazyCat;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "CrazyCat Version:0.02";
		config.width = 800;
		config.height = 480;
		config.addIcon("gameIcon32x32.png", Files.FileType.Internal);
		new LwjglApplication(new CrazyCat(), config);
	}
}
