package com.arcadiogames.planetadventure.Logic.enemies;

import com.arcadiogames.planetadventure.Logic.enemies.behaviours.emptyBehaviour;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Andres on 30/09/2017.
 */
public class enemy extends com.arcadiogames.planetadventure.Logic.moving_element {
    protected enemyCollision e_collider;
    private float dmg;
    private float health = 3;
    private boolean m_dead = false;

    private enemyBehaviour current_behaviour = new emptyBehaviour(this);

    public enemy(int x, int y, Animation anim, com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox hitbox) {
        super(x, y);
        e_collider = new enemyCollision(anim,hitbox,this);
        com.arcadiogames.planetadventure.Logic.collision.collisionManager.getInstance().add(e_collider, 3);
       // e_collider.setScale(2);
    }

    public enemy(int x, int y, Animation anim, com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox hitbox, float A, float k1, float k2, int vx, int trayectoria) {
        this(x, y, anim, hitbox);
        super.setA(A);
        super.setK1(k1);
        super.setK2(k2);
        super.setV_x(vx);
        super.setTrayectoria(trayectoria);

    }

    public float getDmg() {
        return dmg;
    }

    public void setDmg(float dmg) {
        this.dmg = dmg;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    @Override
    public void draw(Batch batch) {
        e_collider.setPosition(getX(),getY());
        e_collider.setRotation((float) (-360*getAngle()/(2*Math.PI)));
        e_collider.draw(batch);
    }

    @Override
    public float getWidth() {
        return e_collider.getRegionWidth()*e_collider.getScaleX();
    }

    @Override
    public float getHeight() {
        return  e_collider.getRegionHeight()*e_collider.getScaleY();
    }

    @Override
    public boolean isDead() {
        return m_dead;
    }

    public void do_damage(float damage, int dir) {

        health = Math.max(0,health-damage);

        start_knockback(dir);

        if(health == 0){
            //animacion de muerte y luego ya si lo mato? !!
            m_dead = true;
        }
    }

    @Override
    public void update(float delta){
        current_behaviour.update(delta);
        super.update(delta);
        current_behaviour.check();
    }

    public void setScale(float s){
        e_collider.setScale(s);
    }

    public void setBehaviour(enemyBehaviour b){
        current_behaviour = b;
        current_behaviour.start();
    }

    public void setFlip(boolean fx, boolean fy){e_collider.setFlip(fx, fy);}
    public void switchFlipX(){e_collider.switchFlipX();}
}
