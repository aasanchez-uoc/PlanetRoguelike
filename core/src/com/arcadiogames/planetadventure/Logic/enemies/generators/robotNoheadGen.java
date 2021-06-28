package com.arcadiogames.planetadventure.Logic.enemies.generators;

import com.arcadiogames.planetadventure.Logic.enemies.behaviours.walkBehaviour;
import com.arcadiogames.planetadventure.Logic.enemies.enemy;
import com.arcadiogames.planetadventure.Logic.enemies.enemyTrigger;
import com.arcadiogames.planetadventure.Logic.enemies.pacer;
import com.arcadiogames.planetadventure.Util.extRandom;
import com.arcadiogames.planetadventure.dungeonGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Andres on 08/11/2017.
 */
public class robotNoheadGen extends enemyGenerator {
    TextureAtlas atlas;
    Animation idle_anim;
    com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox idle_hitbox;
    extRandom random;

    public robotNoheadGen(dungeonGame game){
        atlas = game.Assets.get("textures/enemies.atlas", TextureAtlas.class);
        idle_anim = new Animation(0.25f,atlas.findRegions("robot_nohead"), Animation.PlayMode.LOOP);
        Json json = new Json();
        idle_hitbox = json.fromJson(com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox.class, Gdx.files.internal("textures/robot_unhead.json"));
        idle_hitbox.flip(300);
        random = new extRandom();
    }
    @Override
    public void genEnemy(int x, int y, long seed) {
        final pacer e = new pacer(x, y, idle_anim, idle_hitbox);
        final walkBehaviour wb = new walkBehaviour(e, -200);
        enemyTrigger t = new enemyTrigger(e) {
            int MAX_walked;
            @Override
            protected boolean condition() {
                return (e.getWalked() > MAX_walked);
            }

            @Override
            protected void trigger() {
                e.setWalked(0);
                MAX_walked = random.randInt(50, 350);
                e.setV_x((int) (e.getV_x()*-1));
                e.switchFlipX();

            }
        };
        wb.triggers.add(t);
        e.setBehaviour(wb);
    }
}
