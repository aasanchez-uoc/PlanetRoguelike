package com.arcadiogames.planetadventure.Screens;

import com.arcadiogames.planetadventure.Logic.Dir;
import com.arcadiogames.planetadventure.Logic.moving_element;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Andres on 04/12/2017.
 */
public class Door extends moving_element {
    private Dir door_dir;
    private animatedSprite sprite;
    private boolean closed;
    private boolean waiting_change = false;
    private Animation next;
    public enum Tipo{
        NORMAL, BOSS;
    }
    private Tipo tipo = Tipo.NORMAL;

    public Door(int x, int y, Animation anim, Dir dir) {
        super(x, y);
        sprite = new animatedSprite(anim);
        sprite.pause();
        v_x =0;
        v_y=0;
        this.door_dir = dir;
        setDrawable(false);
    }

    public void open(Animation a){
        sprite.play();
        changeAnim(a);

    }

    public void changeAnim(Animation a){
        if(sprite.isAnimationFinished()) sprite.setAnimation(a);
        else {
            waiting_change = true;
            next = a;
        }
    }

    public void try_change(){
        if(waiting_change && sprite.isAnimationFinished()){
            sprite.setAnimation(next);
            waiting_change = false;
            next = null;
        }
    }

    @Override
    public void draw(Batch batch) {
        sprite.setPosition(getX(), getY());
        sprite.setRotation((float) (-360 * getAngle() / (2 * Math.PI)));
        sprite.draw(batch);
        try_change();
    }

    @Override
    public float getWidth() {
        return sprite.getWidth()*sprite.getScaleX();
    }

    @Override
    public float getHeight() {
        return sprite.getHeight()*sprite.getScaleY();
    }

    @Override
    public boolean isDead() {
        return false;
    }

    public Dir getDir() {
        return door_dir;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
