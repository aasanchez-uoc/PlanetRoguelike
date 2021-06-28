package com.arcadiogames.planetadventure.Screens;

import java.util.ArrayList;

/**
 * Created by Andres on 17/12/2017.
 */
public class clickableManager {
    private ArrayList<clickable> clickables;
    private static clickableManager ourInstance = new clickableManager();

    public static clickableManager getInstance() {
        return ourInstance;
    }

    private clickableManager() {
        clickables = new ArrayList<clickable>();
    }

    public void addClickable(clickable c){
        clickables.add(c);
    }

    public void clean(){
        clickables = new ArrayList<clickable>();
    }

    public ArrayList<clickable> getClickables() {
        return clickables;
    }

    public void remove(clickable e) {
        clickables.remove(e);
    }


}
