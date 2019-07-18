package com.tonoy.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tonoy.eat_run.GameMain;
import com.tonoy.helpers.GameInfo;
import com.tonoy.helpers.GameManager;
import com.tonoy.scenes.MainMenu;


public class UIHud {

    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private Image coinImg, lifeImg, scoreImg, pausePanel;

    private Label coinLabel, lifeLabel, scoreLabel;

    private ImageButton pauseBtn, resumeBtn, quitBtn;

    public UIHud(GameMain game) {
        this.game = game;

        gameViewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT,
                new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        if(GameManager.getInstance().gameStartedFromMainMenu) {
            // this is the first time starting the game, set initial values
            GameManager.getInstance().gameStartedFromMainMenu = false;

            GameManager.getInstance().lifeScore = 2;
            GameManager.getInstance().coinScore = 0;
            GameManager.getInstance().score = 0;

        }

        createLabels();
        createImages();
        createBtnAndAddListener();

        Table lifeAndCoinTable = new Table();
        lifeAndCoinTable.top().left();
        lifeAndCoinTable.setFillParent(true);

        lifeAndCoinTable.add(lifeImg).padLeft(10).padTop(10);
        lifeAndCoinTable.add(lifeLabel).padLeft(5);
        lifeAndCoinTable.row();

        lifeAndCoinTable.add(coinImg).padLeft(10).padTop(10);
        lifeAndCoinTable.add(coinLabel).padLeft(5);

        Table scoreTable = new Table();
        scoreTable.top().right();
        scoreTable.setFillParent(true);

        scoreTable.add(scoreImg).padRight(10).padTop(10);
        scoreTable.row();
        scoreTable.add(scoreLabel).padRight(20).padTop(15);

        stage.addActor(lifeAndCoinTable);
        stage.addActor(scoreTable);
        stage.addActor(pauseBtn);

    }

    void createLabels() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("Fonts/blow.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 40;

        BitmapFont font = generator.generateFont(parameter);

        coinLabel = new Label("x" + GameManager.getInstance().coinScore,
                new Label.LabelStyle(font, Color.WHITE));

        lifeLabel = new Label("x" + GameManager.getInstance().lifeScore,
                new Label.LabelStyle(font, Color.WHITE));

        scoreLabel = new Label(String.valueOf(GameManager.getInstance().score),
                new Label.LabelStyle(font, Color.WHITE));

    }

    void createImages() {
        coinImg = new Image(new Texture("Collectables/Coin.png"));
        lifeImg = new Image(new Texture("Collectables/Life.png"));
        scoreImg = new Image(new Texture("Buttons/Gameplay/Score.png"));
    }

    void createBtnAndAddListener() {
        pauseBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Buttons/Gameplay/Pause.png"))));

        pauseBtn.setPosition(460, 17, Align.bottomRight);

        pauseBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!GameManager.getInstance().isPaused) {
                    GameManager.getInstance().isPaused = true;
                    createPausePanel();
                }
            }
        });

    }

    void createPausePanel() {

        pausePanel = new Image(new Texture("Buttons/Pause/Pause Panel.png"));
        resumeBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Buttons/Pause/Resume.png"))));
        quitBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Buttons/Pause/Quit 2.png"))));

        pausePanel.setPosition(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f,
                Align.center);
        resumeBtn.setPosition(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2 + 50,
                Align.center);
        quitBtn.setPosition(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f - 80,
                Align.center);

        resumeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                removePausePanel();
                GameManager.getInstance().isPaused = false;
            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });

        stage.addActor(pausePanel);
        stage.addActor(resumeBtn);
        stage.addActor(quitBtn);

    }

    void removePausePanel() {
        pausePanel.remove();
        resumeBtn.remove();
        quitBtn.remove();
    }

    public void createGameOverPanel() {

        Image gameOverPanel = new Image(
                new Texture("Buttons/Pause/Show Score.png"));

        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("Fonts/blow.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;

        BitmapFont font = generator.generateFont(parameter);

        Label endScore = new Label(String.valueOf(GameManager.getInstance().score),
                new Label.LabelStyle(font, Color.WHITE));

        Label endCoinScore = new Label(String.valueOf(GameManager.getInstance().coinScore),
                new Label.LabelStyle(font, Color.WHITE));

        gameOverPanel.setPosition(GameInfo.WIDTH / 2f,
                GameInfo.HEIGHT / 2f, Align.center);

        endScore.setPosition(GameInfo.WIDTH / 2f - 30,
                GameInfo.HEIGHT / 2f + 20, Align.center);

        endCoinScore.setPosition(GameInfo.WIDTH / 2f - 30,
                GameInfo.HEIGHT / 2f - 90, Align.center);

        stage.addActor(gameOverPanel);
        stage.addActor(endScore);
        stage.addActor(endCoinScore);

    }

    public void incrementScore(int score) {
        GameManager.getInstance().score += score;
        scoreLabel.setText(String.valueOf(GameManager.getInstance().score));
    }

    public void incrementCoins() {
        GameManager.getInstance().coinScore++;
        coinLabel.setText("x" + GameManager.getInstance().coinScore);
        incrementScore(200);
    }

    public void incrementLifes() {
        GameManager.getInstance().lifeScore++;
        lifeLabel.setText("x" + GameManager.getInstance().lifeScore);
        incrementScore(300);
    }

    public void decrementLife() {
        GameManager.getInstance().lifeScore--;
        if(GameManager.getInstance().lifeScore >= 0) {
            lifeLabel.setText("x" + GameManager.getInstance().lifeScore);
        }
    }

    public Stage getStage() {
        return this.stage;
    }

}














































