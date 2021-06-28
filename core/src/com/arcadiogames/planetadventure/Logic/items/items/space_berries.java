package com.arcadiogames.planetadventure.Logic.items.items;

import com.arcadiogames.planetadventure.Logic.items.Item;
import com.arcadiogames.planetadventure.Screens.LevelScreen;

/**
 * Created by Andres on 16/12/2017.
 */
public class space_berries extends Item {


    public space_berries(int id){
        super(id, "Space Berries", "Hp UP", "space_berries");
    }

    @Override
    public void doEffect(LevelScreen screen) {
        screen.status.getPlayer().increaseHearts(1);
        screen.status.getPlayer().heal(2);
    }
}
