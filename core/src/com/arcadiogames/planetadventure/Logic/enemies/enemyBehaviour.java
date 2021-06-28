package com.arcadiogames.planetadventure.Logic.enemies;

import java.util.ArrayList;

/**
 * Created by Andres on 03/03/2018.
 */
public abstract class enemyBehaviour {
    public ArrayList<enemyTrigger> triggers;
    protected enemy parent;
    public enemyBehaviour(enemy p){
        parent = p;
        triggers = new ArrayList<enemyTrigger>();
    }
    public abstract void start();
    public abstract void update(float delta);
    void check(){
        for (enemyTrigger t: triggers
             ) {
            t.check();
        }
    }
    //end()?
}
