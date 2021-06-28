package com.arcadiogames.planetadventure.Logic.items;

import com.arcadiogames.planetadventure.Util.extRandom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Andres on 13/12/2017.
 */
public class ItemPool {

    private ArrayList<Integer> items;
    private ArrayList<Integer> unlockedItems;
    private extRandom random;


    public ItemPool(long seed) {
        items = ItemLoader.loadNormalPool();
        unlockedItems = ItemLoader.defaultUnlocked();
        random = new extRandom(seed);

    }



    private Item getItemByID(int id){
        return ItemLoader.getItemByID(id);
    }

    public Item getItem(){
        Item ret = null;
        int k = random.randInt(0, items.size()-1);
        if (unlockedItems.contains(items.get(k))){
            ret = getItemByID(items.get(k));
        }
        else{
            //esta parte em da miedo!! probar
            extRandom r2 = new extRandom(1917);
            int k2 = r2.randInt(0, items.size() - 1);
            while (!unlockedItems.contains(items.get(k2)))
            {
                k2 = r2.randInt(0, items.size() - 1);
            }
            ret = getItemByID(unlockedItems.get(k2));
        }
        return ret;
    }

//    public void removeFromPool(Item item){
//        items.remove(item);
//    }
//    public void addToPool(Item item){
//        items.add(item);
//    }

}
