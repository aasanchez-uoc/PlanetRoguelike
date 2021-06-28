package com.arcadiogames.planetadventure.Logic;

import com.arcadiogames.planetadventure.Util.extRandom;
import com.arcadiogames.planetadventure.dungeonGame;

/**
 * Created by Andres on 18/05/2017.
 */
public class roomEnemyCreator {
    long roomSeed;
    public roomEnemyCreator(){

    }
    public void generate_room(long seed, int min_x, int max_x, int min_y, int max_y, dungeonGame game){
        roomSeed = seed;
        com.arcadiogames.planetadventure.Logic.enemyRooms.roomType1 room = new com.arcadiogames.planetadventure.Logic.enemyRooms.roomType1(min_x, max_x, min_y, max_y, seed, game);
        room.generate();
    }


}
