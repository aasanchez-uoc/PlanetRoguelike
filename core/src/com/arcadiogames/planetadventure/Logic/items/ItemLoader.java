package com.arcadiogames.planetadventure.Logic.items;

import com.arcadiogames.planetadventure.Logic.items.items.bionic_eye;
import com.arcadiogames.planetadventure.Logic.items.items.energetic_drink;
import com.arcadiogames.planetadventure.Logic.items.items.heavy_bullets;
import com.arcadiogames.planetadventure.Logic.items.items.lasergun;
import com.arcadiogames.planetadventure.Logic.items.items.robotic_hand;
import com.arcadiogames.planetadventure.Logic.items.items.space_berries;
import com.arcadiogames.planetadventure.Logic.items.items.syringe_blue;
import com.arcadiogames.planetadventure.Logic.items.items.syringe_red;
import com.arcadiogames.planetadventure.Logic.items.items.syringe_yellow;
import com.arcadiogames.planetadventure.Screens.LevelScreen;

import java.util.ArrayList;

/**
 * Created by Andrés on 14/12/2017.
 */
public class ItemLoader {

    public static ArrayList<Integer> defaultUnlocked() {
        ArrayList list = new ArrayList<Integer>();
        //list.add(1);
        for(int i=1; i<7;i++){
            list.add(i);
        }
        return list;
    }

    public static ArrayList<Integer> loadNormalPool() {
        ArrayList list = new ArrayList<Integer>();
        list.add(3);
//        for(int i=1; i<7;i++){
//            list.add(i);
//        }
        return list;
    }

    public static Item getItemByID(int id) {
        Item item = null;
        int k = 1;

        /////////////////////////////
        //ID: 1
        if( id == k) {

            item = new space_berries(id);
//            item  = new Item(id, "Space Berries", "Hp UP", "space_berries") {
//                   @Override
//
//                   public void doEffect(LevelScreen screen) {
//                       screen.status.getPlayer().increaseHearts(1);
//                       screen.status.getPlayer().heal(2);
//                   }
//
//            };
        }

        //////////////////////////////


        //ID: 2
        else if( id == ++k) {

            item  = new bionic_eye(id);
        }

        //ID: 3
        else if( id == ++k) {
            item  = new energetic_drink(id);
        }

        //ID: 4

        else if( id == ++k) {
            item  = new heavy_bullets(id);
        }

        //ID: 5
        else if( id == ++k) {
            item  = new lasergun(id);
        }

        //ID: 6
        else if( id == ++k) {
            item  = new robotic_hand(id);
        }

        //ID: 7
        else if( id == ++k) {
            item  = new syringe_blue(id);
        }

        //ID: 8
        else if( id == ++k) {
            item  = new syringe_red(id);
        }

        //ID: 9
        else if( id == ++k) {
            item  = new syringe_yellow(id);
        }

        return item;
    }
}
