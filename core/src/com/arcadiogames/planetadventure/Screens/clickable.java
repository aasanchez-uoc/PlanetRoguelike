package com.arcadiogames.planetadventure.Screens;

/**
 * Created by Andres on 17/12/2017.
 */
public interface clickable {
    boolean contains(float x, float y);
    void setSelected(boolean selected);
    boolean isSelected();
    boolean toRemove();
    void onClick();
}
