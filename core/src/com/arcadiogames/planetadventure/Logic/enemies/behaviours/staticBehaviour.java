package com.arcadiogames.planetadventure.Logic.enemies.behaviours;

import com.arcadiogames.planetadventure.Logic.enemies.enemy;
import com.arcadiogames.planetadventure.Logic.enemies.enemyBehaviour;

/**
 * Created by Andres on 06/03/2018.
 */
public class staticBehaviour extends enemyBehaviour {
    public staticBehaviour(enemy p) {
        super(p);
    }

    @Override
    public void start() {
        parent.setV_x(0);
    }

    @Override
    public void update(float delta) {

    }
}
