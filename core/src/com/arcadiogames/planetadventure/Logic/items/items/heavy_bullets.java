package com.arcadiogames.planetadventure.Logic.items.items;

import com.arcadiogames.planetadventure.Logic.items.Item;
import com.arcadiogames.planetadventure.Screens.LevelScreen;

/**
 * Created by Andres on 16/12/2017.
 */
public class heavy_bullets extends Item {

    public heavy_bullets(int id){
        super(id, "Heavy bullets", "Bullets  now affected by gravity", "heavy_bullet");
    }

    @Override
    public void doEffect(LevelScreen screen) {
        screen.status.getPlayer().setLinealBullets(false);
    }
}
