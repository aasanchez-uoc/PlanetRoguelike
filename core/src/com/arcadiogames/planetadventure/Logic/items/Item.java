package com.arcadiogames.planetadventure.Logic.items;

import com.arcadiogames.planetadventure.Screens.LevelScreen;

/**
 * Created by Andres on 13/12/2017.
 */
public abstract class Item {
    protected int itemId;
    protected  String itemName, itemDescription, textureName;

    public Item(int id, String name, String description, String texture) {
        itemId = id;
        itemName = name;
        itemDescription = description;
        textureName = texture;
    }

    public abstract void doEffect(LevelScreen screen);


    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
