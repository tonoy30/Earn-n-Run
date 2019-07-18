package com.tonoy.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.tonoy.helpers.GameInfo;

public class Player extends Sprite {
    private World world;
    private Body body;

    private TextureAtlas playerAtlas;
    private Animation animation;
    private float elapsedTime;

    private boolean isWalking, dead;

    public Player(World world, float x, float y) {
        super(new Texture("Player/Player 1.png"));
        this.world = world;
        setPosition(x, y);
        createBody();
        playerAtlas = new TextureAtlas("Player Animation/Player Animation.atlas");
        dead = false;
    }

    void createBody() {

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        body = world.createBody(bodyDef);
        body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2f - 20f) / GameInfo.PPM,
                (getHeight() / 2f) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0f;
        fixtureDef.friction = 2f;
        fixtureDef.filter.categoryBits = GameInfo.PLAYER;
        fixtureDef.filter.maskBits = GameInfo.DEFAULT | GameInfo.COLLECTABLE;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Player");

        shape.dispose();

    }

    public void movePlayer(float x) {

        if(x < 0 && !this.isFlipX()) {
            // moving left, flip the player to face left direction
            this.flip(true, false);
        } else if(x > 0 && this.isFlipX()) {
            // moving right, flip the player to face right direction
            this.flip(true, false);
        }

        isWalking = true;

        body.setLinearVelocity(x, body.getLinearVelocity().y);

    }

    public void drawPlayerIdle(SpriteBatch batch) {
        if(!isWalking) {
            batch.draw(this, getX() + getWidth() / 2f - 20,
                    getY() - getHeight() / 2f);
        }
    }

    public void drawPlayerAnimation(SpriteBatch batch) {
        if(isWalking) {
            elapsedTime += Gdx.graphics.getDeltaTime();

            Array<TextureAtlas.AtlasRegion> frames = playerAtlas.getRegions();

            for(TextureRegion frame : frames) {
                if(body.getLinearVelocity().x < 0 && !frame.isFlipX()) {
                    frame.flip(true, false);
                } else if(body.getLinearVelocity().x > 0 && frame.isFlipX()) {
                    frame.flip(true, false);
                }
            }

            animation = new Animation(1f/10f, playerAtlas.getRegions());

            batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true)
                    , getX() + getWidth() / 2f - 20f,
                    getY() - getHeight() / 2f);
        }
    }

    public void updatePlayer() {
        if(body.getLinearVelocity().x > 0) {
            // going right
            setPosition(body.getPosition().x * GameInfo.PPM,
                    body.getPosition().y * GameInfo.PPM);
        } else if(body.getLinearVelocity().x < 0) {
            // going left
            setPosition((body.getPosition().x - 0.3f) * GameInfo.PPM,
                    body.getPosition().y * GameInfo.PPM);
        }
    }

    public void setWalking(boolean isWalking) {
        this.isWalking = isWalking;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }
}
