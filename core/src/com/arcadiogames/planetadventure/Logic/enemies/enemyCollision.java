package com.arcadiogames.planetadventure.Logic.enemies;

import com.arcadiogames.planetadventure.Logic.collision.collision;
import com.arcadiogames.planetadventure.Screens.animEntity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Andres on 30/09/2017.
 */
public class enemyCollision extends animEntity {
    private enemy m_parent;
    private float dmgd_time = 0.5f;
    private float last_hit;
    private Color orig_color;
    public enemyCollision(Animation animation, com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox box, enemy enemy) {
        super(animation, box);
        this.m_parent = enemy;
        orig_color = getColor();
    }

    @Override
    public float getDamage() {
        return m_parent.getDmg();
    }

    @Override
    public boolean toRemove(){
        return m_parent.isDead();
    }

    @Override
    public int getDir() {
        return (int) getV();
    }

    @Override
    public void on_hit(int type, collision other) {
        //posibles tipos para enemy:0(player), 1(bullets)
        if(type ==1){
            last_hit = 0;
            setColor(1, 0, 0, 1f);
            m_parent.do_damage(other.getDamage(),  (other.getDir( )));
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    @Override
    public  void update(float delta){
        super.update(delta);
        if(last_hit < dmgd_time){
            last_hit += delta;
            if(last_hit >= dmgd_time){
                setColor(orig_color);
            }
        }
    }
}
