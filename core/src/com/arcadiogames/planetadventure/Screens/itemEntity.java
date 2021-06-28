package com.arcadiogames.planetadventure.Screens;

import com.arcadiogames.planetadventure.Logic.items.Item;
import com.arcadiogames.planetadventure.Logic.items.ItemElement;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;

/**
 * Created by Andres on 17/12/2017.
 */
public class itemEntity extends staticElement implements clickable{
    private ItemElement m_item;
    private LevelScreen screen;
    private float anim_t;
    private final float size = 0.2f;
    private boolean selected = false;
    private boolean toRemove = false;
    private boolean debug = false;

    public itemEntity(LevelScreen screen, ItemElement item, Sprite sprite) {
        super((int) (item.getX() + screen.game.planet.getAngleRads()*screen.game.planet.getRadius()), item.getY(), sprite);
        anim_t = 0;
        m_item = item;
        this.screen = screen;
        clickableManager.getInstance().addClickable(this);
    }

    @Override
    public void update(float delta){
        super.update(delta);
        anim_t += delta;
        if(anim_t > 2* Math.PI) anim_t = 0;
        sprite.setOrigin(0, 0);



    }


    @Override
    public void draw(Batch batch){
        float s = (float) (size * Math.abs(Math.sin(anim_t)));
        float x = getX();
        float y = getY();
        //no se si toda esta mierda sobra pero me he rayado mazo
        sprite.setScale(1);
        sprite.setOrigin(0, 0);
        sprite.setRotation(0);
        sprite.setPosition((x + s*sprite.getWidth()/2), (y));
        sprite.setRotation((float) (-360 * getAngle() / (2 * Math.PI)));

        sprite.setOriginCenter();
        sprite.setScale((1 + s));

        sprite.draw(batch);

        if(debug){
            batch.end();
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            Polygon p = new Polygon( new float[] {0, 0, 0, getWidth(), getHeight(), getWidth(),  getHeight(), 0} );
            p.setOrigin(0, 0);
            p.setPosition(getX(), getY());
            p.setRotation((float) (-360 * getAngle() / (2 * Math.PI)));

            shapeRenderer.polygon(p.getTransformedVertices());

            shapeRenderer.end();
            batch.begin();
        }
    }

    @Override
    public boolean contains(float x, float y) {

        Polygon p = new Polygon( new float[] {0, 0, 0, getWidth(), getHeight(), getWidth(),  getHeight(), 0} );
        p.setOrigin(0,0);
        p.setPosition(getX(), getY());
        p.setRotation((float) (-360 * getAngle() / (2 * Math.PI)));
        return p.contains(x, y);
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    public boolean toRemove() {
        return toRemove;
    }

    @Override
    public boolean isDead() {
        return toRemove;
    }

    @Override
    public void onClick() {
        m_item.getItem().doEffect(screen);
        screen.showLabel(m_item.getItem().getItemName(), m_item.getItem().getItemDescription());
        toRemove = true;

    }

    public ItemElement getItemElem() {
        return m_item;
    }
}
