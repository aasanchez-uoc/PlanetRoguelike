package com.arcadiogames.planetadventure.planetGen;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Andres on 27/11/2017.
 */
public class Paleta {
    private Color color3 ;
    private Color color2;
    private Color color1 ;
    private Color sea_color;
    private Color sea_color2;

    enum tipo{TIERRA,LAVA;}

    public Paleta(){
            color3 = new Color(0x56C029ff);
            color2 = new Color(0x74D64Bff);
            color1 = new Color(0x92e56dff);
            sea_color = new Color(0x58A5F0ff);
            sea_color2 = new Color(0x0663BDff);
    }

    public Paleta(tipo p){
        if( p == tipo.LAVA){
            color3 = new Color(0x404040ff);
            color2 = new Color(0x262626ff);
            color1 = new Color(0x1a1a1aff);
            sea_color = new Color(0xF70000ff);
            sea_color2 = new Color(0xFFE920ff);
        }
        else {
            color3 = new Color(0x56C029ff);
            color2 = new Color(0x74D64Bff);
            color1 = new Color(0x92e56dff);
            sea_color = new Color(0x58A5F0ff);
            sea_color2 = new Color(0x0663BDff);
        }
    }


    public Color getSea_color2() {
        return sea_color2;
    }

    public void setSea_color2(Color sea_color2) {
        this.sea_color2 = sea_color2;
    }

    public Color getSea_color() {
        return sea_color;
    }

    public void setSea_color(Color sea_color) {
        this.sea_color = sea_color;
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    public Color getColor3() {
        return color3;
    }

    public void setColor3(Color color3) {
        this.color3 = color3;
    }
}
