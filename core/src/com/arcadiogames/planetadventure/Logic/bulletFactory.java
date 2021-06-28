package com.arcadiogames.planetadventure.Logic;

import com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox;
import com.arcadiogames.planetadventure.Logic.stats.futureBullet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

/**
 * Created by Andres on 11/12/2017.
 */
public class bulletFactory {
    Animation normal_bullet;
    AnimationHitbox normal_hitbox;

    private ArrayList<futureBullet> bullets;

    private static bulletFactory ourInstance = new bulletFactory();

    public static bulletFactory getInstance() {
        return ourInstance;
    }

    public bulletFactory(){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/enemies.atlas"));
        normal_bullet = new Animation(0.1f,atlas.findRegions("bullet"), Animation.PlayMode.LOOP);
        Json json = new Json();
        normal_hitbox = json.fromJson(com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox.class, Gdx.files.internal("textures/bullet.json"));
        bullets = new ArrayList<futureBullet>();

    }
    public void tripleShot(moving_element e, int x, int y, int range, int v){

        bullets.add( new futureBullet(e,x ,y , range, v, 0f));
        bullets.add(new futureBullet(e, x, y + 10, range, v, 0f));
        bullets.add( new futureBullet(e,x ,y - 10 , range, v, 0f));

    }

    public void singleShot(moving_element e, int x, int y, int range, int v){

        bullets.add( new futureBullet(e,x ,y , range, v, 0f));

    }
    public void consecutiveShot(moving_element e, int x, int y, int range, int v, int number, float delay){
        for(int i=0; i<number;i++ ){
            bullets.add(new futureBullet(e,x, y, range, v, i*delay));
        }
    }

    public void update(float delta){
        ArrayList toRemove = new ArrayList<futureBullet>();
        for(futureBullet b : bullets){
            b.update(delta);
            if(b.isReady()){
                b.createbullet(normal_bullet, normal_hitbox);
                toRemove.add(b);
                b = null;
            }
        }
        bullets.removeAll(toRemove);
        toRemove = null;
    }

    public void removeAll() {
        ArrayList toRemove = new ArrayList<futureBullet>();
        for(futureBullet b : bullets){
            toRemove.add(b);
        }
        bullets.removeAll(toRemove);
        toRemove = null;
    }
}
