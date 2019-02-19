package be.ac.umons.slay.g02.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import be.ac.umons.slay.g02.gui.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		// il faut afficher en plein ecran au demarrage pour que tout ne soit pas deforme si on change la resolution apres
		cfg.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		cfg.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;

		// pour tester que ca s'affiche bien sur toutes les resolutions
//		config.width = 1000;
//		config.height = 1200;

		// cache la barre du haut
		cfg.fullscreen = false;

		new LwjglApplication(new Main(), cfg);
	}
}
