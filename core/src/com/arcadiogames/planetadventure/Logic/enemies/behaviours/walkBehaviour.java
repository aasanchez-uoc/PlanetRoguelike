package com.arcadiogames.planetadventure.Logic.enemies.behaviours;

import com.arcadiogames.planetadventure.Logic.enemies.enemy;
import com.arcadiogames.planetadventure.Logic.enemies.enemyBehaviour;

/**
 * Created by Andrés on 06/03/2018.
 */
public class walkBehaviour extends enemyBehaviour {
    int vx;
    public walkBehaviour(enemy p, int v_move) {
        super(p);
        vx = v_move;
    }

    @Override
    public void start() {
        parent.setTrayectoria(1);
        parent.setV_x(vx);
    }

    @Override
    public void update(float delta) {

    }
}
