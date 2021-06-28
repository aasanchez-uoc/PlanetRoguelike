package com.arcadiogames.planetadventure.Logic;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Andres on 03/01/2018.
 */
public class LaserPlayer extends LaserBase {

    private float angle_base;
    private playerEntity player;
    public LaserPlayer(playerEntity player, gamePlayer player_stat, float angle, TextureAtlas atlas) {
        super((int) (player.getX() + player.getRegionWidth()), (int) player.getY() +   4* player.getRegionHeight() /6, (int)player_stat.getRange(), player_stat.getDamage(), angle , atlas);
        this.player = player;
        this.angle_base = angle;
        setSinusoidal(true);
        setLineal(player_stat.hasLinealBullets());
    }

    @Override
    public void update(float delta){
        //!! no hace falta cada vez

        this.y =  (int) (player.getY()  +   4* player.getRegionHeight() /6) ;
        update_radius();
        super.update(delta);
        if(player.isFlipX()){}
    }


    public void setFlip(boolean flip) {
        super.setFlip(flip);
        if(flip) setX((int) player.getX() - start_b.getRegionWidth());
        else setX((int) (player.getX() + player.getRegionWidth()));
    }
}
