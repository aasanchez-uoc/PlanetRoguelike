package com.arcadiogames.planetadventure.Logic;

import com.arcadiogames.planetadventure.Logic.collision.bulletCollision;
import com.arcadiogames.planetadventure.Logic.collision.collisionManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;


/**
 * Created by Andres on 14/05/2017.
 */
public class bullet extends  moving_element {
    private int traveled = 0;
    private int range;
    float damage=1;
    private bulletCollision b_collider;
    private boolean enemy_hit;
    private boolean penetrating = false;


    public bullet(int x, int y, int range, float damage, int v, float angle, Animation animation, com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox hitbox, int type, boolean eslineal) {
        super(x, y);

        this.v_x = v;
        this.range = range;
        this.damage = damage;
        b_collider = new bulletCollision(animation,hitbox,this);
        b_collider.setScale(2);
        collisionManager.getInstance().add(b_collider,type);

        ////lineal
       // setTrayectoria(2);
        if (eslineal)setLineal();
        float rads = (float) (2*Math.PI * angle /360);
        setA( (float) Math.tan(rads));
        ////
    }

    public bullet(int x, int y, gamePlayer player, int v, float angle, Animation animation, com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox hitbox, int type){
        this(x, y, (int)(player.getRange()), player.getDamage(), v, angle,animation, hitbox,type, player.hasLinealBullets());
    }

    public bullet(int x, int y, int range, int v, Animation animation, com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox hitbox, int type) {
             this(x, y, range, 1f,v, 0f, animation,hitbox, type, false);
    }
    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setEnemy_hit(boolean enemy_hit) {
        this.enemy_hit = enemy_hit;
    }

    @Override
    public void draw(Batch batch) {
        b_collider.setPosition(getX(), getY());
        b_collider.draw(batch);
    }

    @Override
    public float getWidth() {
        return b_collider.getRegionWidth();
    }

    @Override
    public float getHeight() {
        return b_collider.getRegionHeight();
    }


    @Override
    public boolean isDead() {
        if(traveled > max_x*range/100f || (enemy_hit && (!penetrating)) ){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void update(float delta){
        float last_x = getXbase();
        super.update(delta);
        traveled += Math.abs(getXbase() - last_x);
    }

    public void setFlip(boolean fx, boolean fy){
        b_collider.setFlip(fx,fy);
    }
}
