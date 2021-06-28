package com.arcadiogames.planetadventure.Logic.enemyRooms;

import com.arcadiogames.planetadventure.Logic.enemies.generators.dronGunGen;
import com.arcadiogames.planetadventure.Logic.enemies.generators.flymonGen;
import com.arcadiogames.planetadventure.Logic.enemies.generators.robotGen;
import com.arcadiogames.planetadventure.Logic.enemies.generators.robotGunGen;
import com.arcadiogames.planetadventure.Logic.enemies.generators.robotNoheadGen;
import com.arcadiogames.planetadventure.Util.extRandom;
import com.arcadiogames.planetadventure.dungeonGame;

/**
 * Created by Andres on 06/11/2017.
 */
public class roomType1 extends roomGen{

    private final robotGen skel;
    private robotNoheadGen ru;
    private robotGunGen rg;
    //flymons
    flymonGen flymon;
    dronGunGen drongun;
    //moscas peques


    public roomType1(int min_x, int max_x, int min_y, int max_y, long seed, dungeonGame game) {
        super(min_x, max_x, min_y, max_y, seed);
        flymon = new flymonGen(game);
        skel = new robotGen(game);
        drongun = new dronGunGen(game);
        ru = new robotNoheadGen(game);
        rg = new robotGunGen(game);

    }


    public void generate() {
        extRandom random = new extRandom(seed);

        int flymons = random.randInt(2, 4);
        int y, x;

//        for(int j=0;j<flymons;j++){
//            y = random.randInt(min_y/10 , max_y/10) * 10 ;//!!
//            x = random.randInt(min_x, max_x);
//            flymon.genEnemy(x, y, seed);
//        }
//
//        for(int j=0;j<flymons;j++){
//        y = random.randInt(min_y / 10, max_y / 10) * 10;//!!
//        x = random.randInt(min_x, max_x);
//        drongun.genEnemy(x, y, seed);
//    }

        x = random.randInt(min_x, max_x);
        skel.genEnemy(x, 0,seed);


        x = random.randInt(min_x, max_x);
        rg.genEnemy(x, 0,seed);
        x = random.randInt(min_x, max_x);
        rg.genEnemy(x, 0,seed);

        int robs = random.randInt(2, 4);
        for(int j=0;j<robs;j++){
            x = random.randInt(min_x, max_x);
            ru.genEnemy(x, 0, seed);
        }

    }




}
