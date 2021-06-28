package com.arcadiogames.planetadventure.Logic.enemies.generators;

import com.arcadiogames.planetadventure.Logic.enemies.behaviours.singleShotBehaviour;
import com.arcadiogames.planetadventure.Logic.enemies.behaviours.walkBehaviour;
import com.arcadiogames.planetadventure.Logic.enemies.enemy;
import com.arcadiogames.planetadventure.Logic.enemies.triggers.onRange;
import com.arcadiogames.planetadventure.Util.extRandom;
import com.arcadiogames.planetadventure.dungeonGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Andres on 08/11/2017.
 */
public class robotGunGen extends enemyGenerator {
    TextureAtlas atlas;
    Animation idle_anim;
    com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox idle_hitbox;
    extRandom random;
    private int v_move = -200;
    private final double SHOT_PAUSE = 3;

    public robotGunGen(dungeonGame game){
        atlas = game.Assets.get("textures/enemies.atlas", TextureAtlas.class);
        idle_anim = new Animation(0.25f,atlas.findRegions("robot_gun"), Animation.PlayMode.LOOP);
        Json json = new Json();
        idle_hitbox = json.fromJson(com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox.class, Gdx.files.internal("textures/robot.json"));
        idle_hitbox.flip(300);
        random = new extRandom();
    }
    @Override
    public void genEnemy(int x, int y, long seed) {
        final enemy e = new enemy(x, y, idle_anim, idle_hitbox);



        final walkBehaviour wb = new walkBehaviour(e, v_move);
        final singleShotBehaviour csb = new singleShotBehaviour(e, SHOT_PAUSE, v_move);
        onRange t = new onRange(e) {
            @Override
            protected void trigger() {
                parent.setBehaviour(csb);
            }
        };
        onRange t2 = new onRange(e) {
            @Override
            protected void trigger() {
                parent.setBehaviour(wb);
            }
        };
        t2.setNot(true);
        wb.triggers.add(t);
        csb.triggers.add(t2);
        e.setBehaviour(wb);
    }
}
