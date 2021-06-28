package com.arcadiogames.planetadventure.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Andres on 18/12/2017.
 */
public class textMessage extends Table{
    Label titleLabel, descrpLabel;
    Table titleTable;
    boolean drawTitleTable = true;
    private messageStyle style;
    private float fade_duration = 0.2f;
    private float duration = 1f;
    private float  life = 0;

    public textMessage(String title, String description, Skin skin){
        this(title, description, skin.get(messageStyle.class));
    }

    public textMessage(String title, String description, messageStyle style){
        setTouchable(Touchable.disabled);
        setClip(true);

        titleLabel = new Label(title, new LabelStyle(style.titleFont, style.titleFontColor));
        titleLabel.setEllipsis(true);

        titleTable = new Table() {
            public void draw (Batch batch, float parentAlpha) {
                if (drawTitleTable) super.draw(batch, parentAlpha);
            }
        };
        titleTable.add(titleLabel).expand().padTop(50);
        addActor(titleTable);

        descrpLabel = new Label(description,new LabelStyle(style.descriptionFont, style.descriptionFontColor));
        descrpLabel.setEllipsis(true);
        add(descrpLabel).expand();

        setStyle(style);
        setWidth(150);
        setHeight(150);

        Color c = getColor();
        setColor(c.r, c.g, c.b, 0);
        addAction(Actions.fadeIn(fade_duration));

    }

    public void setStyle (messageStyle style) {
        if (style == null) throw new IllegalArgumentException("style cannot be null.");
        this.style = style;
        setBackground(style.background);
        titleLabel.setStyle(new LabelStyle(style.titleFont, style.titleFontColor));
        invalidateHierarchy();
    }

    public void draw (Batch batch, float parentAlpha) {
        Stage stage = getStage();


        super.draw(batch, parentAlpha);
        batch.setColor(Color.WHITE);
    }


    protected void drawBackground (Batch batch, float parentAlpha, float x, float y) {
        super.drawBackground(batch, parentAlpha*0.6f, x, y);

        // Manually draw the title table before clipping is done.
        titleTable.getColor().a = getColor().a;
        float padTop = getPadTop(), padLeft = getPadLeft();
        titleTable.setSize(getWidth() - padLeft - getPadRight(), padTop);
        titleTable.setPosition(padLeft, getHeight() - padTop);
        drawTitleTable = true;
        titleTable.draw(batch, parentAlpha);
        drawTitleTable = false; // Avoid drawing the title table again in drawChildren.
    }

    @Override
    public void act(float delta){
        super.act(delta);
        life += delta;
        if (life > duration) hide();
    }

    private void hide() {
        hide(Actions.fadeOut(fade_duration));
    }

    public void hide (Action action) {
        Stage stage = getStage();

        if (action != null) {

            addAction(Actions.sequence(action, Actions.removeActor()));
        } else
            remove();
    }

    static public class messageStyle {
        /** Optional. */
        public Drawable background;
        public BitmapFont titleFont, descriptionFont;
        /** Optional. */
        public Color titleFontColor = new Color(1, 1, 1, 1);
        public Color descriptionFontColor = new Color(1, 1, 1, 1);
        /** Optional. */
        public Drawable stageBackground;

        public messageStyle () {
        }

        public messageStyle (BitmapFont titleFont, Color titleFontColor, Drawable background, BitmapFont descriptionFont, Color descriptionFontColor) {
            this.background = background;
            this.titleFont = titleFont;
            this.titleFontColor.set(titleFontColor);
            this.descriptionFont = descriptionFont;
            this.descriptionFontColor.set(descriptionFontColor);
        }

        public messageStyle (messageStyle style) {
            this.background = style.background;
            this.titleFont = style.titleFont;
            this.descriptionFont = style.descriptionFont;
            this.descriptionFontColor = new Color(style.descriptionFontColor);
            this.titleFontColor = new Color(style.titleFontColor);
        }
    }
}
