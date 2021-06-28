package com.arcadiogames.planetadventure.Logic;

import com.arcadiogames.planetadventure.Logic.items.Item;
import com.arcadiogames.planetadventure.Logic.items.ItemElement;

import java.util.ArrayList;

public class Sala {

    public enum TipoSala{
        NORMAL, ITEM, BOSS;
    }
    private TipoSala tipo = TipoSala.NORMAL;
    private boolean visitada = false;
    private  boolean limpia = false;
    private int x,y;
    private ArrayList<ItemElement> items;


    public Sala(TipoSala tipo, int x, int y){
        this.tipo = tipo;
        this.x = x;
        this.y = y;
        items = new ArrayList<ItemElement>();
    }

    public Sala(int x,int y){
        this(TipoSala.NORMAL, x, y);
    }


    public boolean isVisitada() {
        return visitada;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }

    public void setLimpia(){limpia = true;}
    public boolean isLimpia() {
        return limpia;
    }

    public Point getCoord(){return new Point(x,y);}

    public TipoSala getTipo() {
        return tipo;
    }

    public void addItem(int x, int y, Item item){
        items.add(new ItemElement(item, x, y));
    }

    public ArrayList<ItemElement> getItems() {
        return items;
    }

    public void removeItem(ItemElement itemElem) {
        items.remove(itemElem);
    }

}
