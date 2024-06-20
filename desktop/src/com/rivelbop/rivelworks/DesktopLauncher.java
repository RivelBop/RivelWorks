package com.rivelbop.rivelworks;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
		// Relaunch JVM if -XstartOnFirstThread is necessary (also handles Windows optimizations)
        if (StartupHelper.startNewJvmIfRequired()) return;

		// Handles the LWJGL3 Window
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.useVsync(true);
        config.setResizable(false);
        config.setTitle("RivelWorks");
        config.setWindowedMode(1280, 720);
        new Lwjgl3Application(new RivelWorks(), config);
    }
}