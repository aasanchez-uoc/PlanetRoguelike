package com.arcadiogames.planetadventure.Util;

import java.util.Random;

/**
 * Created by Andres on 05/11/2017.
 */
public class extRandom extends Random{
    public extRandom(long gameSeed) {
        super(gameSeed);
    }
    public extRandom(){super();}

    public int randInt(int min, int max) {
        int randomNum = nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
