package com.arcadiogames.planetadventure.Logic.stats;

import com.arcadiogames.planetadventure.Logic.bullet;
import com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox;
import com.arcadiogames.planetadventure.Logic.moving_element;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by Andres on 11/12/2017.
 */
public class futureBullet {
    moving_element element;
    private  float wait;
    private float time = 0;
    int x, y, v, range;

    public futureBullet(moving_element e, int x, int y,int range, int v, float wait){
        this.element = e;
        this.x = x;
        this.y = y;
        this.range = range;
        this.v = v;
        this.wait = wait;
        time = 0;
    }

    public void update(float delta){
        this.time += delta;
    }

    public boolean isReady(){
        return time >= wait;
    }

    public void createbullet(Animation bullet, AnimationHitbox hitbox) {
        boolean flipX = v < 0;
        new bullet((int)element.get_linealX() + x, (int)element.getLinealY() + y , range, v, bullet, hitbox, 2 ).setFlip(flipX, false);
    }

}
