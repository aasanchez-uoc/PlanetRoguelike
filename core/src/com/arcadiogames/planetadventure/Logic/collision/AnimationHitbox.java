package com.arcadiogames.planetadventure.Logic.collision;

import java.util.ArrayList;

/**
 * Created by Andres on 27/09/2017.
 */
public class AnimationHitbox {
    public ArrayList<hitboxframe> hitbox;

    public AnimationHitbox(){
        hitbox = new ArrayList<hitboxframe>();
        hitbox.add(new hitboxframe());
        hitbox.add(new hitboxframe());
    }

    public int number_of_boxes(int frame_index){
        if(frame_index < hitbox.size()){
            return hitbox.get(frame_index).number_of_boxes();}
            else{
            return 0;
        }
    }

    public hitboxframe getFrame(int i){
        return  hitbox.get(i);
    }

    public void flip(int h) {
        for (hitboxframe hbf:hitbox) {
            hbf.flip(h);
        }
    }
}
