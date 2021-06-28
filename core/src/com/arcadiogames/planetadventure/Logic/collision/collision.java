package com.arcadiogames.planetadventure.Logic.collision;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Andres on 17/06/2017.
 */
public interface collision {
    public Polygon[] getHitboxes();
    public boolean overlaps(collision other);
    public void on_hit(int type, collision other);
    public boolean toRemove();
    public  float getDamage();
    public  int getDir();
}
