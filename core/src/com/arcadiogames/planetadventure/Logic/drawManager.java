package com.arcadiogames.planetadventure.Logic;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

/**
 * Created by Andres on 02/01/2018.
 */
public class drawManager {
    private ArrayList<drawable> list;
    private static drawManager ourInstance = new drawManager();
    public static drawManager getInstance() {
        return ourInstance;
    }

    public drawManager(){
        list = new ArrayList<drawable>();
    }

    public void add(drawable e){
        list.add(e);
    }


    public void update(float delta){
        ArrayList<drawable> toRemove = new ArrayList<drawable>();
        for (drawable e:list) {
            e.update(delta);
            if (e.toRemove()){
                toRemove.add(e);
            }
        }
        list.removeAll(toRemove);
        toRemove = null;
    }

    public  void draw(Batch batch) {
        for (drawable e:list) {
            //if visible, en la pantalla
            e.draw(batch);
        }
    }

    public void removeAll(){
        ArrayList<drawable> toRemove = new ArrayList<drawable>();
        for (drawable e:list) {
            toRemove.add(e);
        }
        list.removeAll(toRemove);
    }

}
