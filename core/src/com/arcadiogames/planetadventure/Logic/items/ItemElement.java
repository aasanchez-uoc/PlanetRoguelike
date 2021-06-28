package com.arcadiogames.planetadventure.Logic.items;

/**
 * Created by Andres on 13/12/2017.
 */
public class ItemElement {
    private Item m_item;
    private int x, y;

    public ItemElement(Item m_item, int x, int y) {
        this.m_item = m_item;
        this.x = x;
        this.y = y;
    }

    public Item getItem() {
        return m_item;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
