package com.arcadiogames.planetadventure.Logic.collision;


import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Andres on 07/02/2018.
 */
public class LaserCollision implements collision {
    Polygon pol;
    ArrayList<Float> vertices;
    private Stack<Float> points;
    private float height;
    private boolean hitFrame;
    private int dir = 1;

    public LaserCollision(float h){
        height = h;
        vertices = new ArrayList<Float>();
        points = new Stack<Float>();
    }

    @Override
    public Polygon[] getHitboxes() {
        return new Polygon[]{pol};
    }

    @Override
    public boolean overlaps(collision other) {
        boolean b = false;

        if(hitFrame) {
            for (Polygon r1 : this.getHitboxes()) {
                for (Polygon r2 : other.getHitboxes()) {
                    b = b || Intersector.overlapConvexPolygons(r1, r2);
                }

            }
        }

        return b;
    }

    @Override
    public void on_hit(int type, collision other) {
        hitFrame = false;
    }

    @Override
    public boolean toRemove() {
        return false;
    }

    @Override
    public float getDamage() {
        return 0.25f;
    }

    @Override
    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public void addPoint(float x, float y){
        vertices.add(x);
        vertices.add(y);
        points.add(y + height);
        points.add(x);
    }

    public void finish(){
        while (!points.empty()){
            vertices.add(points.pop());}
        float[] def = new float[vertices.size()];
       ;
        for (int i = 0; i < vertices.size(); i++) {
            def[i] = vertices.get(i).floatValue();
        }
        pol = new Polygon(def);
        vertices = new ArrayList<Float>();
    }

    public void setHitFrame(boolean hitFrame) {
        this.hitFrame = hitFrame;
    }
}
