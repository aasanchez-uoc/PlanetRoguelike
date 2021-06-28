package com.arcadiogames.planetadventure.Screens;

import com.arcadiogames.planetadventure.Logic.collision.collision;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Andres on 17/07/2017.
 */
public abstract class animEntity extends animatedSprite implements collision {
    //private aRectangle[][] rects;
    private com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox hitbox;
    private boolean debug = false;
    public animEntity(Animation animation, String hitbox_file) {
        super(animation);
        load_hitbox(hitbox_file);
    }

    public animEntity(Animation animation, com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox box){
        super(animation);
        this.hitbox = box;
    }




    //no estoy seguro de que esto deba ir aquí
    public void load_hitbox(String name){
        Json json = new Json();
        this.hitbox = json.fromJson(com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox.class, Gdx.files.internal(name) );
    }

    public void setHitbox( com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox hb){
        this.hitbox = hb;
    }

    @Override
    public Polygon[] getHitboxes() {
        int frame = getFrameIndex();
        //cuidado con esto chaval !!
        int size = hitbox.number_of_boxes(frame);
        Polygon[] ret = new Polygon[size];
        float x =  getX();
        float y = getY();
        float degrees = getRotation();
        for (int i = 0; i<size; i++) {
            ret[i] = hitbox.getFrame(frame).getBox(i).getGdxRectangle(x, y, getScaleX(), getScaleY(), degrees, getRegionWidth(), getRegionHeight(), mFlipX);
        }
        return ret;
    }

    @Override
    public boolean overlaps(collision other) {
        boolean b = false;

        for (Polygon r1: this.getHitboxes()) {
            for (Polygon r2:other.getHitboxes()) {
                b = b || Intersector.overlapConvexPolygons(r1, r2);
            }

        }

        return b;
    }

    public void setAnimation(Animation animation, com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox hitbox){
        setAnimation(animation);
        setHitbox(hitbox);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        if(debug){
            batch.end();
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (Polygon r1: this.getHitboxes()) {
                shapeRenderer.polygon(r1.getTransformedVertices());
            }
            shapeRenderer.end();
            batch.begin();
        }
    }

    public abstract float getDamage();

    public abstract boolean toRemove();

    public abstract int getDir();
}
