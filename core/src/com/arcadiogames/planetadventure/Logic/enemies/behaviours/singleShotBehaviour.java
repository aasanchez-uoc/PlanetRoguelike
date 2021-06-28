package com.arcadiogames.planetadventure.Logic.enemies.behaviours;

import com.arcadiogames.planetadventure.Logic.bulletFactory;
import com.arcadiogames.planetadventure.Logic.enemies.enemy;
import com.arcadiogames.planetadventure.Logic.enemies.enemyBehaviour;

/**
 * Created by Andrés on 07/03/2018.
 */
public class singleShotBehaviour extends enemyBehaviour {
    private double shot_pause, last_shot;
    private int vx;
    public singleShotBehaviour(enemy p, double pause_time, int v_x) {
        super(p);
        vx = v_x;
        shot_pause = pause_time;
        last_shot = pause_time;
    }

    @Override
    public void start() {
        parent.setV_x(vx);
    }

    @Override
    public void update(float delta) {
        if(last_shot >= shot_pause){
            bulletFactory.getInstance().singleShot(parent, 0, (int) parent.getHeight()/2 , 15 , -520);
            last_shot = 0;
        }
        last_shot += delta;
    }
}
