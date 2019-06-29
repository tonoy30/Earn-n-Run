package com.tonoy.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite {
    public Player(String fileName, float x, float y) {
        super(new Texture(fileName));
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }
}
