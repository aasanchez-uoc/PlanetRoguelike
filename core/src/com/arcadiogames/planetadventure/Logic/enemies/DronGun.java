package com.arcadiogames.planetadventure.Logic.enemies;

import com.arcadiogames.planetadventure.Logic.bulletFactory;
import com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox;
import com.arcadiogames.planetadventure.Logic.enemies.behaviours.consecutiveShotBehaviour;
import com.arcadiogames.planetadventure.Logic.enemies.behaviours.walkBehaviour;
import com.arcadiogames.planetadventure.Logic.enemies.triggers.onRange;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by Andres on 07/12/2017.
 */
public class DronGun extends enemy {

    private int v_move = -100;
    private final double SHOT_PAUSE = 3;
    private double last_shot = SHOT_PAUSE;
    private Animation idle, fire;

    public DronGun(int x, int y, Animation idle_anim, Animation fire_anim, AnimationHitbox hitbox) {
        super(x, y, idle_anim, hitbox);
        setV_x(v_move);
        idle = idle_anim;
        fire = fire_anim;

        final walkBehaviour wb = new walkBehaviour(this, v_move);
        final consecutiveShotBehaviour csb = new consecutiveShotBehaviour(this, SHOT_PAUSE);
        onRange t = new onRange(this) {
            @Override
            protected void trigger() {
                parent.setBehaviour(csb);
            }
        };
        onRange t2 = new onRange(this) {
            @Override
            protected void trigger() {
                parent.setBehaviour(wb);
            }
        };
        t2.setNot(true);
        wb.triggers.add(t);
        csb.triggers.add(t2);
        setBehaviour(wb);

    }


}
