package com.arcadiogames.planetadventure.Logic.enemies;

/**
 * Created by Andres on 03/03/2018.
 */
public abstract class enemyTrigger {
    protected  enemy parent;
    private  boolean neg = false;
    public enemyTrigger(enemy p){
        parent = p;
    }
    abstract protected boolean condition();
    abstract protected void trigger();

    public void check(){
        boolean c = (neg) ? !condition(): condition();
        if(c){
            trigger();
        }
    }

    public void setNot(boolean neg) {
        this.neg = neg;
    }
}
