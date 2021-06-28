package com.arcadiogames.planetadventure.Logic.stats;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andres on 13/05/2017.
 */
public class Stat extends baseStat {
    private List<StatBonus> rawBonuses;
    private List<StatBonus> finalBonuses;

    private float finalValue;

    public Stat(float startingValue)
    {
        super(startingValue);

        rawBonuses = new ArrayList<StatBonus>();
        finalBonuses = new ArrayList<StatBonus>();

        finalValue = baseValue;
    }

    public void addRawBonus(StatBonus bonus)
    {
        rawBonuses.add(bonus);
    }

    public void addFinalBonus(StatBonus bonus)
    {
        finalBonuses.add(bonus);
    }

    public void addBonus(StatBonus bonus, boolean raw){
        if(bonus.getBaseValue() != 0 || bonus.getBaseMultiplier() != 0) {
            if(raw) addRawBonus(bonus);
            else addFinalBonus(bonus);
        }

    }
    public void addBonus(StatBonus bonus){
        addBonus(bonus,true);
    }

    public void removeRawBonus(StatBonus bonus)
    {
        if (rawBonuses.indexOf(bonus) >= 0)
        {
            rawBonuses.remove(rawBonuses.indexOf(bonus));
        }
    }

    public void removeFinalBonus(StatBonus bonus)
    {
        if (finalBonuses.indexOf(bonus) >= 0)
        {
            finalBonuses.remove(finalBonuses.indexOf(bonus));
        }
    }

    public float calculateValue()
    {
        finalValue = baseValue;

        // raw
        float rawBonusValue = 0;
        float rawBonusMultiplier = 0;

        for (StatBonus bonus: rawBonuses)
        {
            rawBonusValue += bonus.baseValue;
            rawBonusMultiplier += bonus.getBaseMultiplier();
        }

        finalValue += rawBonusValue;
        finalValue *= (1 + rawBonusMultiplier);

        //final
        float finalBonusValue = 0;
        float finalBonusMultiplier = 0;

        for (StatBonus bonus: finalBonuses)
        {
            finalBonusValue += bonus.baseValue;
            finalBonusMultiplier += bonus.getBaseMultiplier();
        }

        finalValue += finalBonusValue;
        finalValue *= (1 + finalBonusMultiplier);

        return finalValue;
    }

    public float getFinalValue()
    {
        return calculateValue();
    }


    public void removeBonus(StatBonus bonus, boolean raw) {
        if (raw) removeRawBonus(bonus);
        else removeFinalBonus(bonus);
    }

}
