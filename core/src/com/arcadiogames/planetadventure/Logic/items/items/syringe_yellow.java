package com.arcadiogames.planetadventure.Logic.items.items;

import com.arcadiogames.planetadventure.Logic.items.Item;
import com.arcadiogames.planetadventure.Screens.LevelScreen;

/**
 * Created by Andres on 16/12/2017.
 */
public class syringe_yellow extends Item {
    int amount = 30;
    public syringe_yellow(int id){
        super(id, "Adrenaline Syringe", "Move speed Up", "syringe_yellow");
    }

    @Override
    public void doEffect(LevelScreen screen) {
        screen.status.getPlayer().increase_MoveSpeed(amount);
    }
}
