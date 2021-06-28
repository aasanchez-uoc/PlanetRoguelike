package com.arcadiogames.planetadventure.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Andres on 05/05/2017.
 */

public class animatedSprite extends Sprite {

    private Animation<TextureRegion> animation;
    private float time;
    private boolean playing = true;

    private boolean keepSize;
    private boolean centerFrames;

    private int frameIndex = 0;
    protected boolean mFlipY, mFlipX;

    public animatedSprite(Animation animation) {
        this(animation, false);
    }

    public animatedSprite(Animation animation, boolean keepSize) {

        super((TextureRegion) animation.getKeyFrame(0));
        this.animation = animation;
        this.keepSize = keepSize;
    }

    public void update() {
        update(Gdx.graphics.getDeltaTime());
    }

    public void update(float delta) {
        if(playing) {
            setRegion(animation.getKeyFrame(time += delta));
            frameIndex = animation.getKeyFrameIndex(time);
            if(!keepSize)
                setSize(getRegionWidth(), getRegionHeight());
        }
    }

    public void setFrame(int index) {
        if(playing) {
            setRegion(animation.getKeyFrames()[index]);
            frameIndex = index;
            if(!keepSize)
                setSize(getRegionWidth(), getRegionHeight());
        }
    }

    @Override
    public void draw(Batch batch) {
        if(centerFrames && !keepSize) {
            float x = getX(), y = getY(), width = getWidth(), height = getHeight(), originX = getOriginX(), originY = getOriginY();

            update();

            float differenceX = width - getRegionWidth(), differenceY = height - getRegionHeight();
            setOrigin(originX - differenceX / 2, originY - differenceY / 2);
            setBounds(x + differenceX / 2, y + differenceY / 2, width - differenceX, height - differenceY);

            super.draw(batch);

            setOrigin(originX, originY);
            setBounds(x, y, width, height);
            return;
        }

        update();
        super.setFlip(mFlipX, mFlipY);
        super.draw(batch);
    }

    public void play() {
        playing = true;
    }

    public void pause() {
        playing = false;
    }

    public void stop() {
        playing = false;
        time = 0;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getTime() {
        return time;
    }


    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        time = 0;
        this.animation = animation;
    }


    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void setPlayMode(Animation.PlayMode playmode){
        animation.setPlayMode(playmode);
    }

    public Animation.PlayMode getPlayMode(){return  animation.getPlayMode();}

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(time);
    }
    public boolean isAnimationFinished(float t) {
        return animation.isAnimationFinished(t);
    }


    public boolean isKeepSize() {
        return keepSize;
    }

    public void setKeepSize(boolean keepSize) {
        this.keepSize = keepSize;
    }


    public boolean isCenterFrames() {
        return centerFrames;
    }

    public void setCenterFrames(boolean centerFrames) {
        this.centerFrames = centerFrames;
    }

    public int getFrameIndex() {
        return frameIndex;
    }

    public void draw_first_frame(SpriteBatch batch) {
        draw_frame(batch,0);
    }

    public void draw_last_frame(SpriteBatch batch) {
        draw_frame(batch, getAnimation().getKeyFrames().length -1);
    }

    private void draw_frame(SpriteBatch batch, int n){
        if(centerFrames && !keepSize) {
            float x = getX(), y = getY(), width = getWidth(), height = getHeight(), originX = getOriginX(), originY = getOriginY();

            setRegion(animation.getKeyFrame(n));
            frameIndex = n;
            if(!keepSize) setSize(getRegionWidth(), getRegionHeight());

            float differenceX = width - getRegionWidth(), differenceY = height - getRegionHeight();
            setOrigin(originX - differenceX / 2, originY - differenceY / 2);
            setBounds(x + differenceX / 2, y + differenceY / 2, width - differenceX, height - differenceY);

            super.draw(batch);

            setOrigin(originX, originY);
            setBounds(x, y, width, height);
            return;
        }

        setRegion(animation.getKeyFrame(n));
        frameIndex = n;
        if(!keepSize) setSize(getRegionWidth(), getRegionHeight());

        super.draw(batch);
    }

    @Override
    public void setFlip(boolean x, boolean y){
        mFlipX = x;
        mFlipY = y;
    }

    public void switchFlipX(){
        mFlipX = ! mFlipX;
    }

    public float getFrameDuration(){
        return animation.getFrameDuration();
    }


}