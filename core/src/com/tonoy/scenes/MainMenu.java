package com.tonoy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.tonoy.eat_run.GameMain;
import com.tonoy.player.Player;

public class MainMenu implements Screen {
    private GameMain gameMain;
    private Texture bg;
    private Player player;

    public MainMenu(GameMain gameMain) {
        this.gameMain = gameMain;
        bg = new Texture("game-bg.png");
        player = new Player("player-1.png", bg.getWidth() / 2, bg.getHeight() / 2);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Draw the bg
        gameMain.getBatch().begin();
        gameMain.getBatch().draw(bg, 0, 0);
        gameMain.getBatch().draw(player, player.getX(), player.getY());
        gameMain.getBatch().end();
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
    public void dispose() {

    }
}
