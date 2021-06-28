package com.arcadiogames.planetadventure.Logic;

import com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox;
import com.arcadiogames.planetadventure.Logic.collision.collision;
import com.arcadiogames.planetadventure.Screens.animEntity;
import com.arcadiogames.planetadventure.Screens.animatedSprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Andres on 01/10/2017.
 */
public class playerEntity extends animEntity {
    private gamePlayer player;
    private boolean jumping = false;
    private boolean change_anim = false;
    private float vy;
    private float start_y;
    private static final float start_v = 800;
    private float last_invenc = 0;
    private float dur_invenc = 1;
    private boolean invencible = false;
    private Animation nextAnim;
    private AnimationHitbox nextHitbox;
    private TextureAtlas atlas, weapon_atlas;

    private com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox idle_hitbox, walk_hitbox, jump_hitbox;

    private animatedSprite weapon;
    private animStatus nextSatus;


    public enum animStatus {IDLE, JUMP, WALK;};
    private animStatus anim_stat = animStatus.IDLE;


    public playerEntity( gamePlayer player, TextureAtlas atlas, TextureAtlas weapon_atlas) {
        super(new Animation(0.25f, atlas.findRegions("idle"), Animation.PlayMode.LOOP), "textures/idle.json");
        this.player = player;
        this.atlas = atlas;
        this.weapon_atlas =  weapon_atlas;


        Animation<TextureRegion> a = new Animation<TextureRegion>(getFrameDuration(), (TextureRegion[]) weapon_atlas.findRegions(getWeaponString()).toArray(TextureAtlas.AtlasRegion.class));
        a.setPlayMode(Animation.PlayMode.LOOP);
        weapon = new animatedSprite(a);


        Json json = new Json();
        idle_hitbox = json.fromJson(com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox.class, Gdx.files.internal("textures/idle.json") );
        walk_hitbox = json.fromJson(com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox.class, Gdx.files.internal("textures/walk.json"));
        jump_hitbox = json.fromJson(com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox.class, Gdx.files.internal("textures/jump.json"));

        ////
        idle_hitbox.flip(300);
        walk_hitbox.flip(300);
        jump_hitbox.flip(300);
        ////
    }

    @Override
    public float getDamage() {
        return 0;
    }

    @Override
    public void on_hit(int type, collision other) {
        if((type == 2 || type ==3) && !invencible && !player.isDead()){
            player.hurt();
            last_invenc = 0;
            invencible = true;
        }
    }

    @Override
    public boolean toRemove() {
        return false;
    }

    @Override
    public int getDir() {
        return 0;
    }

    @Override
     public void update(float delta){
        super.update(delta);

        if(jumping && isAnimationFinished(getTime()*1.2f )){
            vy -= 900*delta;
            float temp_y = getY() + vy*delta;
            if(temp_y <= start_y){
                vy=0;
                setY(start_y);
                jumping = false;

            }
            else{
                setY(temp_y);
            }
        }

        if(!jumping && change_anim){
                setAnimation(nextAnim, nextHitbox);
                anim_stat = nextSatus;
                change_weapon();
                setChanged();
        }

        if(invencible){
            last_invenc += delta;
            setAlpha((float) Math.abs(Math.sin(10 * last_invenc)));
            if (last_invenc > dur_invenc) {
                invencible =false;
                setAlpha(1);
            }
        }
        weapon.setFrame(getFrameIndex());
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);

        weapon.setPosition(getX(), getY());
        weapon.draw(batch);
    }

    public void jump() {
        //player.jump(new Animation(0.05f, atlas.findRegions("jump"), Animation.PlayMode.NORMAL), jump_hitbox);
        jump(getAnimation(animStatus.JUMP), getHitbox(animStatus.JUMP));
        changeAnimation(animStatus.IDLE);
        //player.changeAnimation(new Animation(0.25f, atlas.findRegions("idle"), Animation.PlayMode.LOOP), idle_hitbox);
    }

    private void jump(Animation animation, AnimationHitbox jump_hitbox) {
        if(!jumping){
            jumping = true;
            vy = start_v;
            start_y = getY();
            anim_stat = animStatus.JUMP;
            setAnimation(animation,jump_hitbox);

           change_weapon();

            play();
        }
    }

    private void changeAnimation(Animation animation, com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox hitbox, animStatus status){
        if(!jumping) {
            setAnimation(animation);
            setHitbox(hitbox);
            anim_stat = status;

            change_weapon();
        }
        else{
            change_anim = true;
            nextAnim = animation;
            nextSatus = status;
            nextHitbox = hitbox;
        }
    }

    private void change_weapon() {
        Animation<TextureRegion> a = new Animation<TextureRegion>(getFrameDuration(), (TextureRegion[]) weapon_atlas.findRegions(getWeaponString()).toArray(TextureAtlas.AtlasRegion.class));
        a.setPlayMode(getPlayMode());
        weapon.setAnimation(a);
    }

    public void changeAnimation(animStatus anim){
        changeAnimation(getAnimation(anim), getHitbox(anim), anim);


    }


    public void setChanged() {
        change_anim = false;

    }


    public boolean isJumping() {
        return jumping;
    }


    public boolean change_anim_needed() {
        return change_anim;
    }

    public Animation getAnimation(animStatus status){
        if (status == animStatus.WALK) return  new Animation(0.15f, atlas.findRegions("walk"), Animation.PlayMode.LOOP);
        if(status == animStatus.JUMP) return new Animation(0.05f, atlas.findRegions("jump"), Animation.PlayMode.NORMAL);
        else return new Animation(0.25f, atlas.findRegions("idle"), Animation.PlayMode.LOOP);
    }

    public AnimationHitbox getHitbox(animStatus status){
        if (status == animStatus.WALK) return  walk_hitbox;
        if(status == animStatus.JUMP) return jump_hitbox;
        else return idle_hitbox;
    }

    public String getWeaponString(){
        String s = "";
        switch (player.getWeaponType()){
            case SHOTGUN:
                s = "shotgun";
                break;
            case LASERGUN:
                s = "lasergun";
                break;
            case LASERCANNON:
                s = "lasercannon";
                break;
            case REVOLVER:
                break;
            default:
               s = "default";
               break;
        }

        switch (anim_stat){
            case WALK:
                s += "_walk";
                break;
            case JUMP:
                s += "_jump";
                break;
            default:
                s += "_idle";
        }
        return  s;
    }

    @Override
    public void setFlip(boolean x, boolean y){
        super.setFlip(x,y);
        weapon.setFlip(x, y);
    }


}
