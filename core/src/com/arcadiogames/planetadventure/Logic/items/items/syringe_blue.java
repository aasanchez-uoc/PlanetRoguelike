package com.arcadiogames.planetadventure.Logic.items.items;

import com.arcadiogames.planetadventure.Logic.items.Item;
import com.arcadiogames.planetadventure.Screens.LevelScreen;

/**
 * Created by Andres on 16/12/2017.
 */
public class syringe_blue extends Item {

    public syringe_blue(int id){
        super(id, "Oxygen Syringe", "Hp Up", "syringe_blue");
    }

    @Override
    public void doEffect(LevelScreen screen) {
        screen.status.getPlayer().increaseHearts(1);
        screen.status.getPlayer().heal(2);
    }
}
