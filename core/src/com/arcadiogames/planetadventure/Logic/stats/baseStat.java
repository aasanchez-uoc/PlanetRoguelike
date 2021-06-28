package com.arcadiogames.planetadventure.Logic.stats;

/**
 * Created by Andres on 13/05/2017.
 */
public class baseStat {
    protected float  baseValue;
    private float baseMultiplier;

    public baseStat(float value, float multiplier){
        baseValue = value;
        baseMultiplier = multiplier;
    }

    public baseStat(float value){
        this(value, 0f);
    }

    public float getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(float baseValue) {
        this.baseValue = baseValue;
    }

    public float getBaseMultiplier() {
        return baseMultiplier;
    }

    public void setBaseMultiplier(float baseMultiplier) {
        this.baseMultiplier = baseMultiplier;
    }
}
