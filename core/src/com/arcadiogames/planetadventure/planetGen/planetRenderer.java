package com.arcadiogames.planetadventure.planetGen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Created by Andres on 16/11/2017.
 */
public class planetRenderer {

   // private int radius = 300; //peque
    private int radius = 1000; //mediano
    private int pixel_size = 10;
    private int C_x, C_y;
    private boolean rotating = false;
    private int v = 300;
    private float angle = 0;
    private long seed;

    private Paleta paleta;

    private boolean draw_atmosphere = true;
    private Color atmosphere_color = new Color(0xC8FCFCaa);
    private int atmosphere_offset = 50;
    private int atmosphere_radius = 30;
    private Texture planet_tex;
    private Texture atmosphere_tex;
    private SpriteBatch batch;
    private FastNoise noise_sea, noise;

    private Pixmap aux_pixmap, aux_pixmap2;

    public planetRenderer(int C_x, int C_y, long seed){
        batch = new SpriteBatch();
        this.C_x = C_x ;
        this.C_y = C_y - radius;
        this.seed = seed;
        int pick = new Random(seed).nextInt(Paleta.tipo.values().length);
        paleta = new Paleta( Paleta.tipo.values()[pick]);
        noise_sea = new FastNoise();
        noise_sea.SetNoiseType(FastNoise.NoiseType.Simplex);
        noise_sea.SetFractalOctaves(5);
        noise_sea.SetFrequency(0.004f);

        noise = new FastNoise((int) seed);
        noise.SetNoiseType(FastNoise.NoiseType.Perlin);
        noise.SetFrequency(0.055f);
        noise.SetInterp(FastNoise.Interp.Linear);

        //dibujar_atmosfera();
        //dibujar_planeta();


       // //dibujar_noise();
    }

    public void  newPlanet(long seed){
        this.seed = seed;
        angle = 0;
        int pick = new Random(seed).nextInt(Paleta.tipo.values().length);
        paleta = new Paleta( Paleta.tipo.values()[pick]);
        //dibujar_planeta();
    }

    public void setRotating(boolean b){
        rotating = b;
    }

    private void rotate(float delta) {
        float a = (float) (360*(delta*v/radius)/(Math.PI*2));
        angle -= a;
        if(angle > 360) angle -= 360;
        if(angle < 0) angle += 360;
    }

    public void render(float delta) {
        if (rotating) rotate(delta);
        if(draw_atmosphere){
            batch.begin();
            int m_radius = radius + atmosphere_radius;
            batch.draw(atmosphere_tex, C_x - m_radius - atmosphere_offset/2, C_y - m_radius);
            batch.end();
        }

        batch.begin();
        //batch.draw(planet_tex, C_x - radius, C_y - radius);
        batch.draw(planet_tex, C_x - radius, C_y - radius,radius  , radius , planet_tex.getWidth(), planet_tex.getHeight(),1,1, angle , 0, 0, planet_tex.getWidth(), planet_tex.getHeight(), false, false);
        batch.end();

    }

    public void dibujar_atmosfera() {
        int m_radius = radius +atmosphere_radius;
        Pixmap pixmap = new Pixmap( 2*m_radius + atmosphere_offset , 2 * m_radius +atmosphere_offset , Pixmap.Format.RGBA8888 );
        pixmap.setColor(atmosphere_color);
        circulo(pixmap, m_radius + atmosphere_offset / 2, m_radius + atmosphere_offset, m_radius);
        aux_pixmap2 = BlurUtils.blur(pixmap, 15 ,2, true);
    }

    public void finish_atmosfera(){
        atmosphere_tex = new Texture( aux_pixmap2 );
        aux_pixmap2.dispose();
    }

    public void dibujar_planeta() {
        aux_pixmap = new Pixmap( 2*radius, 2 * radius, Pixmap.Format.RGBA8888 );
        aux_pixmap.setColor(paleta.getColor1());
        circuloPlanetaSavy2(radius, radius, radius);

    }

    public void finish_dibujar(){
        planet_tex = new Texture( aux_pixmap );
        aux_pixmap.dispose();
    }

    private void circulo(Pixmap pixmap, int cx,int cy,int radius){
        for(int y=-radius; y<=radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                if (x * x + y * y <= radius * radius) {
                    pixmap.drawPixel(cx + x, cy +y);
                }
            }
        }
    }

    private void circuloPlaneta(Pixmap pixmap, int cx, int cy, int radius){
        byte [][] noise = perlinNoiseGenerator.generateArrayHeightMap(2 * radius, 2 * radius, 20, 200, 8, seed);
        //byte [][] noise_sea = perlinNoiseGenerator.generateArraySoft(2 * radius,  2 * radius, 40, 180, 1);

        for(int y=-radius/pixel_size; y<radius/pixel_size; y++) {
            for (int x = -radius/pixel_size; x < radius/pixel_size; x++) {
                if (pixel_size*pixel_size*x * x + pixel_size*pixel_size*y * y <= radius * radius + radius) {
                    Color c = paleta.getColor1();
                    if(noise[radius + x*pixel_size][radius + y*pixel_size] < 1) {
                        float a = (noise_sea.GetNoise( x*pixel_size, y*pixel_size) + 1)/2;
                        c = new Color(a,a,a,1);
                        c.r = paleta.getSea_color().r*(1-a) + paleta.getSea_color2().r*a;
                        c.g = paleta.getSea_color().g*(1-a) + paleta.getSea_color2().g*a;
                        c.b = paleta.getSea_color().b*(1-a) + paleta.getSea_color2().b*a;

                    }
                    else if(noise[radius + x*pixel_size][radius + y*pixel_size] < 100) c = paleta.getColor3();
                    else if(noise[radius + x*pixel_size][radius + y*pixel_size] < 120) c = paleta.getColor2();
                    else  c = paleta.getColor1();
                    //float a = (noise_sea.GetNoise(radius +x, radius +y) + 1)/2;
                    //c.set(a, a, a ,1);
                    //c.set(noise_sea[radius + x][radius + y]/255f, noise_sea[radius + x][radius + y]/255f,noise_sea[radius + x][radius + y]/255f,1);
                    for(int k = 0; k < pixel_size; k ++){
                        for(int k2 = 0; k2 < pixel_size; k2 ++){
                            pixmap.drawPixel(cx + pixel_size*x + k, cy + pixel_size*y + k2, Color.rgba8888(c));
                        }
                    }

                }
            }
        }

    }

    private void circuloPlanetaSavy(Pixmap pixmap, int cx, int cy, int radius){
        int pixel_save = 10;
        byte [][] noise = perlinNoiseGenerator.generateArrayHeightMap(2 * radius/pixel_save, 2 * radius/pixel_save, 20, 200, 4, seed);
        //byte [][] noise_sea = perlinNoiseGenerator.generateArraySoft(2 * radius,  2 * radius, 40, 180, 1);

        for(int y=-radius/pixel_size; y<radius/pixel_size; y++) {
            for (int x = -radius/pixel_size; x < radius/pixel_size; x++) {
                if (pixel_size*pixel_size*x * x + pixel_size*pixel_size*y * y <= radius * radius + radius) {
                    Color c = paleta.getColor1();
                    if(noise[(radius + x*pixel_size)/pixel_save][(radius + y*pixel_size)/pixel_save] < 1) {
                        float a = (noise_sea.GetNoise( x*pixel_size, y*pixel_size) + 1)/2;
                        c = new Color(a,a,a,1);
                        c.r = paleta.getSea_color().r*(1-a) + paleta.getSea_color2().r*a;
                        c.g = paleta.getSea_color().g*(1-a) + paleta.getSea_color2().g*a;
                        c.b = paleta.getSea_color().b*(1-a) + paleta.getSea_color2().b*a;

                    }
                    else if(noise[(radius + x*pixel_size)/pixel_save][(radius + y*pixel_size)/pixel_save] < 80) c = paleta.getColor3();
                    else if(noise[(radius + x*pixel_size)/pixel_save][(radius + y*pixel_size)/pixel_save] < 100) c = paleta.getColor2();
                    else  c = paleta.getColor1();
                    //float a = (noise_sea.GetNoise(radius +x, radius +y) + 1)/2;
                    //c.set(a, a, a ,1);
                    //c.set(noise[(radius + x)/pixel_save][(radius + y)/pixel_save]/255f, noise[(radius + x)/pixel_save][(radius + y)/pixel_save]/255f,noise[(radius + x)/pixel_save][(radius + y)/pixel_save]/255f,1);
                    for(int k = 0; k < pixel_size; k ++){
                        for(int k2 = 0; k2 < pixel_size; k2 ++){
                            pixmap.drawPixel(cx + pixel_size*x + k, cy + pixel_size*y + k2, Color.rgba8888(c));
                        }
                    }

                }
            }
        }
    }

    private void circuloPlanetaSavy2(int cx, int cy, int radius){
        int pixel_save = 10;

        for(int y=(int) (Math.sqrt(2)*Math.abs(C_y + radius)/(2* pixel_size)); y<radius/pixel_size; y++) {
            for (int x =(int) (Math.sqrt(2)*Math.abs(C_y + radius)/(2* pixel_size)); x < radius/pixel_size; x++) {
                if (pixel_size*pixel_size*x * x + pixel_size*pixel_size*y * y <= radius * radius + radius) {

                    dibujar_pixel (x, y, pixel_save, cx, cy);
                    dibujar_pixel (-x, y, pixel_save, cx, cy);
                    dibujar_pixel (x, -y, pixel_save, cx, cy);
                    dibujar_pixel (-x, -y, pixel_save, cx, cy);

                }
            }

            for (int x =0; x < (int) (Math.sqrt(2)*Math.abs(C_y + radius)/(2* pixel_size)); x++) {
                if (pixel_size*pixel_size*x * x + pixel_size*pixel_size*y * y <= radius * radius + radius) {
                    dibujar_pixel (x, y, pixel_save, cx, cy);
                    dibujar_pixel (-x, y, pixel_save, cx, cy);
                    dibujar_pixel (x, -y, pixel_save, cx, cy);
                    dibujar_pixel (-x, -y, pixel_save, cx, cy);
                    dibujar_pixel (y, x, pixel_save, cx, cy);
                    dibujar_pixel (-y, x, pixel_save, cx, cy);
                    dibujar_pixel (y, -x, pixel_save, cx, cy);
                    dibujar_pixel (-y, -x, pixel_save, cx, cy);

                }
            }
        }


    }

    private void circuloPlanetaSavy3(int cx, int cy, int radius){
        int pixel_save = 10;

        for(int y=0; y<radius/pixel_size; y++) {
            for (int x =0; x < radius/pixel_size; x++) {
                if (pixel_size*pixel_size*x * x + pixel_size*pixel_size*y * y <= radius * radius + radius) {

                    dibujar_pixel (x, y, pixel_save, cx, cy);
                    dibujar_pixel (-x, y, pixel_save, cx, cy);
                    dibujar_pixel (x, -y, pixel_save, cx, cy);
                    dibujar_pixel (-x, -y, pixel_save, cx, cy);

                }
            }

        }
    }
    private void dibujar_pixel(int x, int y, int pixel_save, int cx, int cy) {
        Color c;
        float n1 = noise.GetNoise((radius + x*pixel_size)/pixel_save, (radius + y*pixel_size)/pixel_save);
        float n = n1*180 + 20;
        if(n < 1) {
            //float a = (noise_sea.GetNoise( x*pixel_size, y*pixel_size) + 1)/2;
            float a = (n1 +1)/2 ;
            c = new Color(a,a,a,1);
            c.r = paleta.getSea_color().r*(1-a) + paleta.getSea_color2().r*a;
            c.g = paleta.getSea_color().g*(1-a) + paleta.getSea_color2().g*a;
            c.b = paleta.getSea_color().b*(1-a) + paleta.getSea_color2().b*a;

        }
        else if(n < 30) c = paleta.getColor3();
        else if(n < 70) c = paleta.getColor2();
        else  c = paleta.getColor1();

        for(int k = 0; k < pixel_size; k ++){
            for(int k2 = 0; k2 < pixel_size; k2 ++){
                aux_pixmap.drawPixel(cx + pixel_size*x + k, cy + pixel_size*y + k2, Color.rgba8888(c));
            }
        }
    }

    private void dibujar_noise() {
//        int m_radius = radius +atmosphere_radius;
//        Pixmap pixmap = perlinNoiseGenerator.generatePixmap(2*radius, 2*radius,0, 100, 10);
//        planet_tex = new Texture( pixmap );
//        pixmap.dispose();


    }

    public void setRotateV(int rotateV) {
        this.v = rotateV;
    }

    public int getRadius() {
        return radius;
    }

    public float getAngleDegs() {
        return -angle;
    }

    public  float getAngleRads(){ return (float) (-2* Math.PI * angle/360);}

    public void setAngleDegs(float angle) {
        this.angle = angle;
    }

    public void setAngleRads(float angle){
        this.angle = (float) (360 * angle/ (2*Math.PI));
    }

    public int getC_x() {
        return C_x;
    }

    public int getC_y() {
        return C_y;
    }

}
