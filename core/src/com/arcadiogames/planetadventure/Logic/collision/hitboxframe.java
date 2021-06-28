package com.arcadiogames.planetadventure.Logic.collision;

import java.util.ArrayList;

/**
 * Created by Andres on 27/09/2017.
 */
public class hitboxframe {
    public ArrayList<aRectangle> frame;
    public hitboxframe(){
        frame = new ArrayList<aRectangle>();
        frame.add(new aRectangle());
    }

    public int number_of_boxes() {
        return frame.size();
    }

    public aRectangle getBox(int i) {
        return frame.get(i);
    }

    public void flip(int h) {
        for (aRectangle r:frame) {
            r.flip(h);
        }
    }
}
