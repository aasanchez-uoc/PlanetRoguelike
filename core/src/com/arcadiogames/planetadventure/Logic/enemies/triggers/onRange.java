package com.arcadiogames.planetadventure.Logic.enemies.triggers;

import com.arcadiogames.planetadventure.Logic.enemies.enemy;
import com.arcadiogames.planetadventure.Logic.enemies.enemyTrigger;
import com.badlogic.gdx.Gdx;

/**
 * Created by Andres on 11/03/2018.
 */
public abstract class onRange extends enemyTrigger {
    public onRange(enemy p) {
        super(p);
    }

    @Override
    protected boolean condition() {
        return ( ( (parent.getX() + parent.getWidth()) < Gdx.graphics.getWidth() ) && ((parent.getX() + parent.getWidth())> Gdx.graphics.getWidth()/2)  ) && parent.getY() > 0;
    }

}
