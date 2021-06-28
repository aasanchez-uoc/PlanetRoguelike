package com.arcadiogames.planetadventure.Logic.enemies;

import com.arcadiogames.planetadventure.Logic.bulletFactory;
import com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by Andres on 07/12/2017.
 */
public class DronGun_BAK extends enemy {
    public enum Fase {
        MOVE,ATTACK;
    }

    public Fase fase = Fase.MOVE;
    private int v_move = -400;
    private final double SHOT_PAUSE = 3;
    private double last_shot = SHOT_PAUSE;
    private Animation idle, fire;

    public DronGun_BAK(int x, int y, Animation idle_anim, Animation fire_anim, AnimationHitbox hitbox) {
        super(x, y, idle_anim, hitbox);
        setV_x(v_move);
        idle = idle_anim;
        fire = fire_anim;
    }

    @Override
    public void update(float delta){

        if(fase == Fase.ATTACK && last_shot >= SHOT_PAUSE){
            //bulletFactory.getInstance().tripleShot(this, 0, (int) getHeight()/2 ,10, -520);
            bulletFactory.getInstance().consecutiveShot(this, 0, (int) getHeight()/2 , 15 , -520, 5, 0.1f);
            last_shot = 0;
        }

        super.update(delta);

        last_shot += delta;

        //cambiamos de modo ataque o movimiento en funcion de donde estemos
        if(fase == Fase.MOVE && (((getX() + getWidth())< Gdx.graphics.getWidth()) && ((getX() + getWidth())> Gdx.graphics.getWidth()/2)  ) && getY() > 0) {
            changeFase(Fase.ATTACK);
        }
        if(fase == Fase.ATTACK && ( ((getX() + getWidth())< Gdx.graphics.getWidth()/2)) || ((getX() + getWidth())> Gdx.graphics.getWidth())) {
            changeFase(Fase.MOVE);
        }
    }

    public void changeFase(Fase f){
        fase = f;
        if(f == Fase.MOVE){
            setV_x(v_move);
            e_collider.setAnimation(idle);
        }
        if(f == fase.ATTACK){
            setV_x(0);
            e_collider.setAnimation(fire);
        }
    }

}
