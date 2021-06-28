package com.arcadiogames.planetadventure.Logic.enemies.generators;

import com.arcadiogames.planetadventure.dungeonGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Andres on 06/11/2017.
 */
public class flymonGen extends enemyGenerator {

    TextureAtlas atlas;
    Animation fly_anim;
    com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox fly_hitbox;

    public flymonGen(dungeonGame game){
        atlas = game.Assets.get("textures/enemies.atlas", TextureAtlas.class);
        fly_anim = new Animation(0.25f,atlas.findRegions("flymon1"), Animation.PlayMode.LOOP);
        Json json = new Json();
        fly_hitbox = json.fromJson(com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox.class, Gdx.files.internal("textures/flymon.json"));
        fly_hitbox.flip(60);
    }

    @Override
    public void genEnemy(int x, int y, long seed) {
        new com.arcadiogames.planetadventure.Logic.enemies.enemy(x, y, fly_anim, fly_hitbox, 0, 200, 1.0f/100, -350, 1).setScale(2f);
    }
}
