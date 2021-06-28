package com.arcadiogames.planetadventure.Logic.items.items;

import com.arcadiogames.planetadventure.Logic.items.Item;
import com.arcadiogames.planetadventure.Screens.LevelScreen;

/**
 * Created by Andres on 16/12/2017.
 */
public class bionic_eye extends Item {

    int aumento = 5;
    public bionic_eye(int id){
        super(id, "Bionic Eye", "Range UP", "bionic_eye");
    }

    @Override
    public void doEffect(LevelScreen screen) {
        screen.status.getPlayer().setRange(screen.status.getPlayer().getRange()+ aumento);
    }
}
