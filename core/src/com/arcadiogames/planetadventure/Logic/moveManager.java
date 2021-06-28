package com.arcadiogames.planetadventure.Logic;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

/**
 * Created by Andres on 07/05/2017.
 */
public class moveManager {
    private ArrayList<moving_element> list;
    private static moveManager ourInstance = new moveManager();
    private boolean rotating;
    private int max_x;
    private float cx,cy, radius;
    private int rotate_v;

    public static moveManager getInstance() {
        return ourInstance;
    }

    public moveManager(){
        list = new ArrayList<moving_element>();
    }
    
    public void add(moving_element e){
        e.setMax_x(max_x);
        e.setOrigin(cx,cy, radius);
        list.add(e);
    }

    public void setOrigin(float cx, float cy, float radius){
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
    }

    public void update(float delta){
        ArrayList<moving_element> toRemove = new ArrayList<moving_element>();
        for (moving_element e:list) {
            update(delta, e);
            if (e.isDead()){
                toRemove.add(e);
            }
        }
        list.removeAll(toRemove);
        toRemove = null;
    }

    private void update(float delta, moving_element e) {
        if(rotating) e.setRotate_v(rotate_v);
        else e.setRotate_v(0);
        e.update(delta);
        check_boundaries(e);

    }

    private void check_boundaries(moving_element e){

        if( (e.getOffset()) < 0){  //!!??
            e.setOffset(e.getOffset() + max_x);
        }
        if(e.getOffset() > max_x){
            e.setOffset(e.getOffset() - max_x);
        }

        if( (e.get_linealX() - e.getWidth()) < 0){
            e.setX(e.get_linealX() + max_x);
            e.setOffset(0);
        }
        if(e.get_linealX() > max_x){
            e.setX(e.get_linealX() - max_x);
            e.setOffset(0);
        }
    }

    public void starRotating(){
        rotating=true;
    }

    public void stopRotating(){rotating = false;}

    public void  setMax_x(int max_x){
        this.max_x = max_x;
    }

    public void setRotate_v(int rotate_v){
        this.rotate_v = rotate_v;
    }

    public  void draw(Batch batch) {
        for (moving_element e:list) {
            //if visible, en la pantalla
            if(e.isDrawable()) e.draw(batch);
        }
    }

    public void removeAll(){
        ArrayList<moving_element> toRemove = new ArrayList<moving_element>();
        for (moving_element e:list) {
            toRemove.add(e);
            e = null;
        }
        list.removeAll(toRemove);
    }

    public float getRadius() {
        return radius;
    }

    public float getCx() {
        return cx;
    }

    public float getCy() {
        return cy;
    }
}
