package com.arcadiogames.planetadventure.Logic.enemies.behaviours;

import com.arcadiogames.planetadventure.Logic.bulletFactory;
import com.arcadiogames.planetadventure.Logic.enemies.enemy;
import com.arcadiogames.planetadventure.Logic.enemies.enemyBehaviour;

/**
 * Created by Andrés on 07/03/2018.
 */
public class consecutiveShotBehaviour extends enemyBehaviour {
    private double shot_pause, last_shot;
    public consecutiveShotBehaviour(enemy p, double pause_time) {
        super(p);

        shot_pause = pause_time;
        last_shot = pause_time;
    }

    @Override
    public void start() {
        parent.setV_x(0);
    }

    @Override
    public void update(float delta) {
        if(last_shot >= shot_pause){
            bulletFactory.getInstance().consecutiveShot(parent, 0, (int) parent.getHeight()/2 , 15 , -520, 5, 0.1f);
            last_shot = 0;
        }
        last_shot += delta;
    }
}
