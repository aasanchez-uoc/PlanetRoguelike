package com.arcadiogames.planetadventure.Logic.items.items;

import com.arcadiogames.planetadventure.Logic.items.Item;
import com.arcadiogames.planetadventure.Screens.LevelScreen;

/**
 * Created by Andres on 16/12/2017.
 */
public class energetic_drink extends Item {
    int amount = 20;
    public energetic_drink(int id){
        super(id, "Energetic Drink", "Move speed Up", "energetic_drink");
    }

    @Override
    public void doEffect(LevelScreen screen) {
        screen.status.getPlayer().increase_MoveSpeed(amount);
    }
}
