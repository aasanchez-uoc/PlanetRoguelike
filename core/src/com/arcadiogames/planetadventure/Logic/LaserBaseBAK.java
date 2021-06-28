package com.arcadiogames.planetadventure.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

// Borro esta línea porque no se usa y da error.

/**
 * Created by Andres on 30/12/2017.
 */
public class LaserBaseBAK extends drawable{

    private final float damage;
    private final int range;
    protected int x, y;
    private Color color,curr_color;
    private float time;
    private boolean flipX;
    private float angle, angle_rads;
    protected float initial_angle, radius;
    private boolean lineal = false;
    private boolean sinusoidal = true;
    private double ox, oy, lastx, lasty;

    //
    private TextureAtlas atlas;
    private TextureAtlas.AtlasRegion start_b, start_o, mid_b, mid_o, end_b, end_o;


    public LaserBaseBAK(int x, int y, int range, float damage, float angle, TextureAtlas atlas) {

        this.x = x;
        this.y = y;

        this.range = range;
        this.damage = damage;



        this.atlas = atlas;

        this.color = Color.RED;
        curr_color = color;

        update_radius();

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
        //b_collider
        //b_collider.setScale(2);
        //collisionManager.getInstance().add(b_collider,type);
    }

    protected void update_radius() {
        float kx = x - moveManager.getInstance().getCx();
        float ky = y - moveManager.getInstance().getCy();

        initial_angle = (kx == 0) ? (float) Math.PI/2 : (float) Math.atan( ky /kx );
        radius = (float) Math.sqrt( Math.pow(kx, 2) + Math.pow(ky, 2) );
    }


    public void draw(Batch batch){
        double nx, ny;
        nx = getRealX(0, 0);
        ny = getRealY(0, 0);

        if(nx != lastx){
            Gdx.app.log("test-x", "x: " + String.valueOf(lastx) + "x: " + String.valueOf(lastx) + "x: " + String.valueOf(lastx));}
        if(ny != lasty){
            Gdx.app.log("test-y", "y: " + String.valueOf(lasty));
        }

        lastx = nx;
        lasty= ny;

        batch.end();
        int srcFunc = batch.getBlendSrcFunc();
        int dstFunc = batch.getBlendDstFunc();
        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        batch.begin();

        Color b = batch.getColor();
        batch.setColor(curr_color);

        batch.draw(start_b, (float) nx, (float) ny,0, 0, start_b.getRegionWidth(), start_b.getRegionHeight(), 1, 1, angle);
        //



        batch.setColor(Color.WHITE);
        batch.draw(start_o, (float) nx, (float) ny, 0, 0, start_o.getRegionWidth(), start_o.getRegionHeight(), 1, 1, angle);
        double ky = 0;
        for(int i = 0; i<range; i++) {
            ky = (sinusoidal) ? (6 * Math.sin(.25f * (time * i))) : 0;
            nx = getRealX((start_b.getRegionWidth() + i), ky);
            ny = getRealY((start_b.getRegionWidth() + i), ky);

            batch.setColor(curr_color);
            batch.draw(mid_b, (float) nx, (float) ny, 0, 0, mid_b.getRegionWidth(), mid_b.getRegionHeight(), 1, 1, angle);

            batch.setColor(Color.WHITE);
            batch.draw(mid_o,(float )nx, (float)ny, 0, 0, mid_o.getRegionWidth(), mid_o.getRegionHeight(), 1, 1, angle);
        }
        nx = getRealX(start_b.getRegionWidth() + range, ky);
        ny = getRealY(start_b.getRegionWidth() + range, ky);

        batch.setColor(curr_color);

        batch.draw(end_b, (float )nx, (float) ny, 0, 0, end_b.getRegionWidth(), end_b.getRegionHeight(), 1, 1, angle);
        //
        batch.setColor(Color.WHITE);
        batch.draw(end_o, (float )nx, (float)ny , 0, 0, end_o.getRegionWidth(), end_o.getRegionHeight(),1,1, angle);

        //finish
        batch.setColor(b);
        batch.end();
        batch.setBlendFunction(srcFunc, dstFunc);
        batch.begin();
    }

    @Override
    public boolean toRemove() {
        return false;
    }

    public void update(float delta){
        time += delta;
        setAngle( angle +1);
        if( time > 0.2f){
            curr_color = Color.WHITE;
        }

        if(time > 0.25f) {
            curr_color = color;
            time = 0;
        }
    }

    private void setAngle(float ang) {
        this.angle = ang;
        if(angle < 0) angle=  360 + angle;
        if(angle >= 360) angle-= 360;
        angle_rads = (float) (2* Math.PI * angle/360);

        if(0 < angle_rads && angle_rads <= Math.PI/2){
            // (cos,sen) = (+,+)
            ox = -start_b.getRegionHeight() * Math.sin(angle_rads);
            oy = 0;
        }
        else if(Math.PI/2 < angle_rads && angle_rads <= Math.PI){
            // (cos,sen) = (-,+)
            ox = start_b.getRegionWidth()*Math.cos(angle_rads) -start_b.getRegionHeight() * Math.sin(angle_rads);
            oy = start_b.getRegionHeight() * Math.cos(angle_rads);
        }
        else if(Math.PI < angle_rads && angle_rads <= 3*Math.PI/2){
            // (cos,sen) = (-,-)
            ox = start_b.getRegionWidth()*Math.cos(angle_rads);
            oy = start_b.getRegionWidth()*Math.sin(angle_rads) + start_b.getRegionHeight() * Math.cos(angle_rads);
        }
        else{
            ox= 0;
            oy = start_b.getRegionWidth()*Math.sin(angle_rads) ;
        }

        // ox =  Math.min(0, Math.min(-start_b.getRegionHeight() * Math.sin(angle_rads), Math.min(start_b.getRegionWidth()*Math.cos(angle_rads)  -start_b.getRegionHeight() * Math.sin(angle_rads), start_b.getRegionWidth()*Math.cos(angle_rads))));
        //oy =  Math.min(0, Math.min(start_b.getRegionHeight() * Math.cos(angle_rads), Math.min(start_b.getRegionWidth()*Math.sin(angle_rads) + start_b.getRegionHeight() * Math.cos(angle_rads), start_b.getRegionWidth()*Math.sin(angle_rads))));

    }

    public boolean isFlipX() {
        return flipX;
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;

    }

    private float calculateAngleDif(double x1, double y1, double x2, double y2){
        return (float) Math.acos( (x2 - x1) / Math.sqrt( Math.pow(x2 - x1 ,2) + Math.pow(y2 - y1, 2) ) );
    }

    protected double getNX(double x0, double y0){
        double x1 = x  + x0 ;
        return x1;
    }

    protected double getNY(double x0, double y0){
        double y1 = y +  y0;

        if (lineal) return y1;
        else {
            double x1 = getNX(x0, y0);
            y1 = radius +  y0;


            y1 = Math.abs(y - moveManager.getInstance().getCy()) +y0;
            double alpha = getBeta(x1, y1);
            return  (moveManager.getInstance().getCy()   + (y1)* Math.sin(alpha));
        }
    }

    protected double rotateX(double nx, double ny){
        double k = (nx == x) ? 0 : Math.sqrt( Math.pow(nx -x, 2) + Math.pow(ny -y, 2) );
        double phi = (nx == x) ? 0 : Math.acos((nx -x)/k);
        return x + k* Math.cos(angle_rads - phi);
    }

    protected double rotateY(double nx, double ny){
        double k = (nx == x) ? 0 : Math.sqrt( Math.pow(nx -x, 2) + Math.pow(ny -y, 2) );
        double phi = (nx == x) ? 0 : Math.acos((nx -x)/k);
        return y + k* Math.sin(angle_rads - phi);
    }

    protected double getRealX(double x0, double y0){
        //return rotateX(getNX(x0, y0), getNY(x0, y0)) + Math.sin(angle_rads)*start_b.getRegionHeight();
        return rotateX(getNX(x0, y0), getNY(x0, y0))  ;
    }

    protected double getRealY(double x0, double y0){
        return rotateY(getNX(x0, y0), getNY(x0, y0))  ;
        //return rotateY(getNX(x0, y0), getNY(x0, y0)) +  Math.cos(angle_rads)*start_b.getRegionWidth()/2;
    }


    protected  double getAlpha(double x1, double y1){
        return (y1 == y) ? 0 : Math.acos((x1 - moveManager.getInstance().getCx()) / (y1));
    }
    protected  double getBeta(double x1, double y1){
        return (y1 == y) ? 0 : Math.acos((x1 - moveManager.getInstance().getCx()) / (y1));
    }

    ////////
    ///!!!!
    public static boolean intersectSegmentCircle (Vector2 start, Vector2 end, Vector2 center, float squareRadius) {

        Vector3 d = new Vector3();
        Vector3 f = new Vector3();
        Vector3 tmp2 = new Vector3();
        Vector3 tmp3 = new Vector3();

        d.set(end.x - start.x, end.y - start.y, 0);
        f.set(start.x - center.x, start.y - center.y, 0);

        float a = d.dot(d);
        float b = 2 * f.dot(d);
        float c = f.dot(f) - squareRadius;

        float discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            // no intersection
            return  false;
        } else {
            discriminant = (float) Math.sqrt(discriminant);
            float t1 = (-b - discriminant) / (2 * a);
            float t2 = (-b + discriminant) / (2 * a);
            // 3x HIT cases:
            //          -o->             --|-->  |            |  --|->
            // Impale(t1 hit,t2 hit), Poke(t1 hit,t2>1), ExitWound(t1<0, t2 hit),

            // 3x MISS cases:
            //       ->  o                     o ->              | -> |
            // FallShort (t1>1,t2>1), Past (t1<0,t2<0), CompletelyInside(t1<0, t2>1)

            if (t1 >= 0 && t1 <= 1) {
                // Impale, Poke
                return true;
            }
            // here t1 didn't intersect so we are either started
            // inside the sphere or completely past it
            if (t2 >= 0 && t2 <= 1) {
                // ExitWound
                return true;
            }
            // no intn: FallShort, Past, CompletelyInside
            return false;
        }
    }


}
