package com.arcadiogames.planetadventure.Screens;

import com.arcadiogames.planetadventure.Logic.Dir;
import com.arcadiogames.planetadventure.Logic.moving_element;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Andres on 17/12/2017.
 */
public class staticElement extends moving_element {
    protected Sprite sprite;


    public staticElement(int x, int y, Sprite sprite) {
        super(x, y);
        this.sprite = sprite;
        v_x =0;
        v_y=0;
        setDrawable(false);
    }


    @Override
    public void draw(Batch batch) {
        sprite.setPosition(getX(), getY());
        sprite.setRotation((float) (-360 * getAngle() / (2 * Math.PI)));
        sprite.draw(batch);

    }

    @Override
    public float getWidth() {
        return sprite.getWidth()*sprite.getScaleX();
    }

    @Override
    public float getHeight() {
        return sprite.getHeight()*sprite.getScaleY();
    }

    @Override
    public boolean isDead() {
        return false;
    }

}
