package com.tonoy.earn_run;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tonoy.helpers.GameManager;
import com.tonoy.scenes.MainMenu;


public class GameMain extends Game {
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        GameManager.getInstance().initializeGameData();
        setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}

    public SpriteBatch getBatch() {
        return this.batch;
    }
}
