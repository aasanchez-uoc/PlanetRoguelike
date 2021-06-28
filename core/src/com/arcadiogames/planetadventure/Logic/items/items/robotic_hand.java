package com.arcadiogames.planetadventure.Logic.items.items;

import com.arcadiogames.planetadventure.Logic.gamePlayer;
import com.arcadiogames.planetadventure.Logic.items.Item;
import com.arcadiogames.planetadventure.Screens.LevelScreen;

/**
 * Created by Andres on 16/12/2017.
 */
public class robotic_hand extends Item {

    private int amonunt = 2;
    public robotic_hand(int id){
        super(id, "Robotic Hand", "Fire speed Up", "robotic_hand");
    }

    @Override
    public void doEffect(LevelScreen screen) {
        screen.status.getPlayer().decreaseFireSpeed(amonunt);
    }
}
