package com.arcadiogames.planetadventure.Logic.enemies.generators;

import com.arcadiogames.planetadventure.Logic.enemies.DronGun;
import com.arcadiogames.planetadventure.dungeonGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Andres on 11/12/2017.
 */
public class dronGunGen extends enemyGenerator {
    TextureAtlas atlas;
    Animation idle, fire;
    com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox hitbox;

    public dronGunGen(dungeonGame game){
        atlas = game.Assets.get("textures/enemies.atlas", TextureAtlas.class);
        idle = new Animation(0.25f,atlas.findRegions("dron_gun_idle"), Animation.PlayMode.LOOP);
        fire = new Animation(0.25f,atlas.findRegions("dron_gun_fire"), Animation.PlayMode.LOOP);
        Json json = new Json();
        hitbox = json.fromJson(com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox.class, Gdx.files.internal("textures/dron_gun.json"));
        //hitbox.flip();
    }
    @Override
    public void genEnemy(int x, int y, long seed) {
        new DronGun(x, y, idle, fire, hitbox);
    }


}
