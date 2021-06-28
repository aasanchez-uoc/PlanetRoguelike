package com.arcadiogames.planetadventure.Logic.items.items;

import com.arcadiogames.planetadventure.Logic.items.Item;
import com.arcadiogames.planetadventure.Screens.LevelScreen;

/**
 * Created by Andres on 16/12/2017.
 */
public class syringe_red extends Item {

    public syringe_red(int id){
        super(id, "Blood Syringe", "Hp Up", "syringe_red");
    }

    @Override
    public void doEffect(LevelScreen screen) {
        screen.status.getPlayer().increaseHearts(1);
        screen.status.getPlayer().heal(2);
    }
}
