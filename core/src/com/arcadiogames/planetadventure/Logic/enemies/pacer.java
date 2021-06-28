package com.arcadiogames.planetadventure.Logic.enemies;

import com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by Andres on 21/03/2018.
 */
public class pacer extends enemy {
    float walked;
    public pacer(int x, int y, Animation anim, AnimationHitbox hitbox) {
        super(x, y, anim, hitbox);
    }

    @Override
    public void update(float delta){
        float last_x = getXbase();
        super.update(delta);
        walked += Math.abs(getXbase() - last_x);
    }

    public float getWalked() {
        return walked;
    }

    public void setWalked(float walked) {
        this.walked = walked;
    }
}
