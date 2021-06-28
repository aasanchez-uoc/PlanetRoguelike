package com.arcadiogames.planetadventure.Logic;

import com.arcadiogames.planetadventure.Logic.collision.LaserCollision;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Andres on 30/12/2017.
 */
public class LaserBase extends drawable{

    private final float damage;
    private int range;
    protected int x, y;
    protected boolean hit_frame = false;
    private Color color,curr_color;
    private float time;
    private boolean flipX;
    private float angle, angle_rads;
    protected float initial_angle, radius;
    private boolean lineal = false;
    private boolean sinusoidal = false;
    private boolean debug = false;
    private double ox, oy, lastx, lasty;
    private boolean toRemove = false;
    private final int MAX_REJILLA = 50;
    private int n_rejilla;

    private LaserCollision collision;

    //
    private TextureAtlas atlas;
    protected TextureAtlas.AtlasRegion start_b, start_o, mid_b, mid_o, end_b, end_o;


    public LaserBase(int x, int y, int range, float damage, float angle, TextureAtlas atlas) {

        this.x = x;
        this.y = y;



        this.damage = damage;



        this.atlas = atlas;

        this.color = Color.RED;
        curr_color = color;

        update_radius();

        n_rejilla = MAX_REJILLA * range / 100;
        this.range = (int) (range *4*radius/ 100);

        drawManager.getInstance().add(this);

        start_b = atlas.findRegion("laser_start_b");
        start_o = atlas.findRegion("laser_start_o");
        mid_b =  atlas.findRegion("laser_mid_b");
        mid_o =  atlas.findRegion("laser_mid_o");
        end_b  =  atlas.findRegion("laser_end_b");
        end_o =  atlas.findRegion("laser_end_o");

        //change angle;
        setAngle(angle);

        //lenght = (int) (range/ start_b.getRegionWidth());

        //COLISION
        //scale!
        collision = new LaserCollision(start_b.getRegionHeight());
    }

    protected void update_radius() {
        float kx = x - moveManager.getInstance().getCx();
        float ky = y - moveManager.getInstance().getCy();


        radius = (float) Math.sqrt( Math.pow(kx, 2) + Math.pow(ky, 2) );
    }


    public void draw(Batch batch){
        double nx, ny;
        //nx = (flipX) ? x - start_b.getRegionWidth() :x;
        nx = x;
        ny = y;
        if(!lineal){
            double alpha = getAlpha(x, radius);
            ny =  (moveManager.getInstance().getCy()   + (radius)* Math.sin(alpha));
        }

        float cx = (isFlip()) ? start_b.getRegionWidth() : 0;
        collision.addPoint((float)nx + cx, (float)ny);

        lastx = nx;
        lasty= ny;

        //activamos el blend para el laser
        batch.end();
        int srcFunc = batch.getBlendSrcFunc();
        int dstFunc = batch.getBlendDstFunc();
        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        batch.begin();
        ////////////////////////////////////

        float temp_angle = (flipX) ? angle +180 : angle;
        int flip = (flipX) ? -1 : 1;

        Color b = batch.getColor();
        batch.setColor(curr_color);
        batch.draw(start_b, (float) nx, (float) ny, start_b.getRegionWidth() / 2, start_b.getRegionHeight() / 2, start_b.getRegionWidth(), start_b.getRegionHeight(), 1, 1, temp_angle);

        batch.setColor(Color.WHITE);
        batch.draw(start_o, (float) nx, (float) ny, start_o.getRegionWidth()/2, start_o.getRegionHeight()/2, start_o.getRegionWidth(), start_o.getRegionHeight(), 1, 1, temp_angle);

        int m_range = (this.range > 4*radius ) ? (int) (4 * radius): range;
        double ky = 0;

        int rejilla_k = 0;

        for(int i = 0; i<m_range; i++) {
            ky = (sinusoidal) ? (6 * Math.sin(.25f * (time * i))) : 0;

            if(!lineal) {ky += flip;}

            nx = getRealX((start_b.getRegionWidth()/2 + i)*flip, ky );
            ny = getRealY((start_b.getRegionWidth()/2 + i)*flip, ky);

            if(!lineal) {
                rejilla_k += 1;
                if(rejilla_k == range/n_rejilla){
                    rejilla_k = 0;
                    collision.addPoint((float)nx, (float)ny);
                }
            }
            batch.setColor(curr_color);
            batch.draw(mid_b, (float) nx, (float) ny, mid_b.getRegionWidth()/2 , mid_b.getRegionHeight()/2, mid_b.getRegionWidth(), mid_b.getRegionHeight(), 1, 1, temp_angle);

            batch.setColor(Color.WHITE);
            batch.draw(mid_o,(float )nx, (float)ny, mid_o.getRegionWidth()/2, mid_o.getRegionHeight()/2 , mid_o.getRegionWidth(), mid_o.getRegionHeight(), 1, 1, temp_angle);
        }

        float x0 = (start_b.getRegionWidth()/2 + m_range)*flip;
        x0 = (flipX) ? x0 - end_b.getRegionWidth() : x0;
       // if(!lineal && flipX) {ky -= end_b.getRegionHeight()/2;}
        nx  = rotate2X(getNX2(x0, ky), getNY2(x0, ky ));
        ny = rotateY(getNX2(x0, ky), getNY2(x0, ky));
        //nx = (flipX) ? nx - end_b.getRegionWidth() : nx;

        cx = (flipX) ? 0 : start_b.getRegionWidth() ;
        collision.addPoint((float)nx + cx, (float)ny);
        batch.setColor(curr_color);
        batch.draw(end_b, (float )nx, (float) ny, end_b.getRegionWidth()/2, end_b.getRegionHeight()/2, end_b.getRegionWidth(), end_b.getRegionHeight(), 1, 1, temp_angle);
        //
        batch.setColor(Color.WHITE);
        batch.draw(end_o, (float) nx, (float) ny, end_o.getRegionWidth() / 2, end_b.getRegionHeight() / 2, end_o.getRegionWidth(), end_o.getRegionHeight(), 1, 1, temp_angle);

        //finish
        collision.finish();
        batch.setColor(b);
        batch.end();
        batch.setBlendFunction(srcFunc, dstFunc);
        batch.begin();


        if(debug){
            batch.end();
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (Polygon r1: collision.getHitboxes()) {
                shapeRenderer.polygon(r1.getTransformedVertices());
            }
            shapeRenderer.end();
            batch.begin();
        }
    }

    @Override
    public boolean toRemove() {
        return toRemove;
    }

    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }

    public void update(float delta){
        time += delta;
        //setAngle( angle +1);
        if( time > 0.2f){
            curr_color = Color.WHITE;
            collision.setHitFrame(true);
        }

        if(time > 0.25f) {
            curr_color = color;
            time = 0;
            collision.setHitFrame(false);
        }
    }

    protected void setAngle(float ang) {
        this.angle = ang;
        if(angle < 0) angle=  360 + angle;
        if(angle >= 360) angle-= 360;
        angle_rads = (float) (2* Math.PI * angle/360);
    }

    public boolean isFlip() {
        return flipX;
    }

    public void setFlip(boolean flipX) {
        this.flipX = flipX;
        collision.setDir  (( flipX ) ?  -1 : 1);
    }

    private float calculateAngleDif(double x1, double y1, double x2, double y2){
        return (float) Math.acos( (x2 - x1) / Math.sqrt( Math.pow(x2 - x1 ,2) + Math.pow(y2 - y1, 2) ) );
    }

    protected double getNX(double x0, double y0){
        double x1 = x  + x0 ;
        if (!lineal){
            if(x0 >= radius && x0 < 3*radius){
                double x02 = 2 * radius - x0;
                x1 = x + x02;
            }
            else if(x0 >= 3*radius){
                double x02 = x0 - 4*radius;
                x1 = x + x02;
            }
        }
        return x1;
    }

    protected double getNX2(double x0, double y0){
        double kx = end_b.getRegionWidth()/2;
        double x1 = x  + x0 + kx ;
        if (!lineal){
            if(x0 >= radius && x0 < 3*radius){
                double x02 = 2 * radius - x0;
                x1 = x + x02 + kx;
            }
            else if(x0 >= 3*radius){
                double x02 = x0 - 4*radius;
                x1 = x + x02 + kx;
            }
        }
        return x1;
    }

    protected double getNY(double x0, double y0){
        double y1 = y +  y0;

        if (lineal) return y1;
        else {
            int flip = (flipX) ? -1 : 1;
            double x1 = getNX(x0, y0);
            y1 = radius;
            double alpha = getAlpha(x1, y1);
            if(x0 < 0) x0 += 4*radius; //?
            if(x0 >= radius && x0 < 3*radius) alpha *= -1;
            return  (moveManager.getInstance().getCy()   + (y1)* Math.sin(alpha)) + y0*flip;
        }
    }
    protected double getNY2(double x0, double y0){
        double y1 = y +  y0;

        if (lineal) return y1;
        else {
            int flip = (flipX) ? -1 : 1;
            double x1 = getNX2(x0, y0);
            y1 = radius + end_b.getRegionHeight()/4;
            double alpha = getAlpha(x1, y1);
            if(x0 < 0) x0 += 4*radius; //?
            if(x0 >= radius && x0 < 3*radius) alpha *= -1;
            return  (moveManager.getInstance().getCy()   + (y1)* Math.sin(alpha)) + y0*flip;
        }
    }

    protected double rotateX(double nx, double ny){
        double kx = x  ;
        double k = (nx == x) ? 0 : Math.sqrt( Math.pow(nx -kx, 2) + Math.pow(ny -y, 2) );;
        double phi = (nx == kx) ? 0 : Math.acos((nx -kx)/k);
        return kx + start_b.getRegionWidth()/2 + k* Math.cos(angle_rads - phi);
    }

    protected double rotate2X(double nx, double ny){
        double kx = x  ;
        double k = (nx == x) ? 0 : Math.sqrt( Math.pow(nx -kx, 2) + Math.pow(ny -y, 2) );
        double phi = (nx == kx) ? 0 : Math.acos((nx -kx)/k);
        return kx  + k* Math.cos(angle_rads - phi);
    }

    protected double rotateY(double nx, double ny){
        double kx = x ;
        double k = (nx == x) ? 0 : Math.sqrt( Math.pow(nx -kx, 2) + Math.pow(ny -y, 2) );
        double phi = (nx == kx) ? 0 : Math.acos((nx -kx)/k);
        return y + k* Math.sin(angle_rads - phi) ;
    }


    protected double getRealX(double x0, double y0){
        return rotateX(getNX(x0, y0), getNY(x0, y0));
    }

    protected double getRealY(double x0, double y0) {
        return rotateY(getNX(x0, y0), getNY(x0, y0));
    }

    protected  double getAlpha(double x1, double y1){
        double kx = x1 - moveManager.getInstance().getCx();
        double alph = (y1 == y) ? 0 : Math.acos((kx) / (y1));
        return  alph;
    }

    public void setSinusoidal(boolean sinusoidal) {
        this.sinusoidal = sinusoidal;
    }

    public void setLineal(boolean lineal) {
        this.lineal = lineal;
    }

    public void setX(int x) {
        this.x = x;
        update_radius();
    }

    public LaserCollision getCollision() {
        return collision;
    }
}
