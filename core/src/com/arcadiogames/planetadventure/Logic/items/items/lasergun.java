package com.arcadiogames.planetadventure.Logic.items.items;

import com.arcadiogames.planetadventure.Logic.gamePlayer;
import com.arcadiogames.planetadventure.Logic.items.Item;
import com.arcadiogames.planetadventure.Screens.LevelScreen;

/**
 * Created by Andres on 16/12/2017.
 */
public class lasergun extends Item {

    public lasergun(int id){
        super(id, "Lasergun", "Bzzzzzzzz", "lasergun");
    }

    @Override
    public void doEffect(LevelScreen screen) {
        screen.status.getPlayer().setWeaponT(gamePlayer.weaponType.LASERGUN);
        screen.status.getPlayer().setBulletType(gamePlayer.bulletType.LASER);
    }
}
