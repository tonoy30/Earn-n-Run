package com.tonoy.earn_run.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tonoy.earn_run.GameMain;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 480;
        config.height = 800;
        config.title = "Earn N Run";
        config.addIcon("Icon/alien.png", Files.FileType.Internal);
        new LwjglApplication(new GameMain(), config);
    }
}
