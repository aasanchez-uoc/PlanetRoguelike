package com.arcadiogames.planetadventure.Logic;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Andres on 02/01/2018.
 */
public abstract class drawable {
    public abstract void update(float delta);
    public abstract void draw(Batch batch);
    public abstract boolean toRemove();

}
