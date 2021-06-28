package com.arcadiogames.planetadventure.Logic.enemies.generators;

import com.arcadiogames.planetadventure.Logic.enemies.*;
import com.arcadiogames.planetadventure.Logic.enemies.behaviours.walkBehaviour;
import com.arcadiogames.planetadventure.dungeonGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Andres on 08/11/2017.
 */
public class robotGen extends enemyGenerator {
    TextureAtlas atlas;
    Animation idle_anim;
    com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox idle_hitbox;

    public robotGen(dungeonGame game){
        atlas = game.Assets.get("textures/enemies.atlas", TextureAtlas.class);
        idle_anim = new Animation(0.25f,atlas.findRegions("robot"), Animation.PlayMode.LOOP);
        Json json = new Json();
        idle_hitbox = json.fromJson(com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox.class, Gdx.files.internal("textures/robot.json"));
        idle_hitbox.flip(300);
    }
    @Override
    public void genEnemy(int x, int y, long seed) {
        enemy e = new enemy(x, y, idle_anim, idle_hitbox, 0 , 0, 0, -200, 1);
        final walkBehaviour wb = new walkBehaviour(e, -200);
        e.setBehaviour(wb);
    }
}
