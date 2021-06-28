package com.arcadiogames.planetadventure.Logic.collision;

import com.badlogic.gdx.math.Polygon;


/**
 * Created by Andres on 17/06/2017.
 */
public class aRectangle {
    private float x, y, w, h;

    public aRectangle(){

    }
    public aRectangle(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Polygon getGdxRectangle(float x1, float y1, float scalex,float scaley, float degrees, float pw, float ph, boolean flipX){
        Polygon polygon;
        //polygon = new Polygon(new float[]{0,0,w*scalex,0,w*scalex,h*scaley,0,h*scaley});
        //polygon.setOrigin((pw ) / 2, ph  / 2);
        float mx;
        if(flipX)  mx = ((pw - x -w)*scalex + pw*(1-scalex)/2 + x1);
        else  mx = (x*scalex + pw*(1-scalex)/2 + x1);
        float my = (y*scaley + ph*(1-scaley)/2  + y1);
        //polygon.setPosition(mx , my);
        polygon = new Polygon(new float[]{mx,my,w*scalex + mx, my, w*scalex + mx ,h*scaley +my, mx,h*scaley + my});
        polygon.setOrigin(x1 + pw/2, y1 + ph/2);
        polygon.setRotation(degrees);
        return polygon;
    }

//    public Polygon getGdxRectangle(float x1, float y1, float scalex,float scaley){
//        return getGdxRectangle(x1, y1, scalex, scaley,0f);
//    }


    public void flip(int h1) {
        y = h1 -y - h;
    }
}
