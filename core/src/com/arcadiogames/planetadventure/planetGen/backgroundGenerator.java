package com.arcadiogames.planetadventure.planetGen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Created by Andres on 20/11/2017.
 */
public class backgroundGenerator {
    int width, height, count;
    float density = 0.005f;
    float brightness = 0.225f;
    private Texture stars;
    private Random ran;
    private SpriteBatch batch;

    public backgroundGenerator(int width, int height, int seed){
        this.width = width;
        this.height = height;
        count = Math.round(width*height*density);
        ran = new Random(seed);
        batch = new SpriteBatch();
        create_stars();
    }

    private void create_stars() {
        Pixmap pixmap = new Pixmap( width , height , Pixmap.Format.RGBA8888 );
        for(int i=0; i<count; i++){
            int r1 = (int) Math.floor(ran.nextFloat()*width);
            int r2 = (int) Math.floor(ran.nextFloat()*height);
            float c = (float)( Math.log(1 - ran.nextFloat()) * -brightness);
            Color color = new Color(c,c,c,1);
            pixmap.drawPixel(r1, r2, Color.rgba8888(color));
        }
        stars = new Texture(pixmap);
        pixmap.dispose();
    }

    public void render(float delta){

        batch.begin();
        batch.draw(stars, 0, 0);
        batch.end();
    }
}
