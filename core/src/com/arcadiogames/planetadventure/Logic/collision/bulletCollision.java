package com.arcadiogames.planetadventure.Logic.collision;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by Andres on 01/10/2017.
 */
public class bulletCollision extends com.arcadiogames.planetadventure.Screens.animEntity {
    private com.arcadiogames.planetadventure.Logic.bullet m_parent;

    public bulletCollision(Animation animation, AnimationHitbox box, com.arcadiogames.planetadventure.Logic.bullet b) {
        super(animation, box);
        m_parent = b;
    }


    @Override
    public float getDamage() {
        return m_parent.getDamage();
    }

    @Override
    public void on_hit(int type, collision other) {
        m_parent.setEnemy_hit(true);
    }

    @Override
    public boolean toRemove() {
        return m_parent.isDead();
    }

    @Override
    public int getDir() {
        int v = 0;
        if( m_parent.getV_x() != 0) v = (int) (m_parent.getV_x() / Math.abs( m_parent.getV_x()));
        return v;
    }
}
