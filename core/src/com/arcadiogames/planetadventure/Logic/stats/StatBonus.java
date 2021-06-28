package com.arcadiogames.planetadventure.Logic.stats;

/**
 * Created by Andres on 13/05/2017.
 */
public class StatBonus extends baseStat{
    public StatBonus(float value, float multiplier) {
        super(value, multiplier);
    }
    public StatBonus(float value){
        super(value);
    }
    public StatBonus(){
        super(0);
    }
}
