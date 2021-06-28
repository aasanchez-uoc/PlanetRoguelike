package com.arcadiogames.planetadventure.Screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by Andres on 09/11/2017.
 */
public class InputDetector extends GestureDetector {
    OrthographicCamera camera;



    public interface DirectionListener {
        void onLeft();

        void onRight();

        void onUp();

        void onDown();
    }

    public InputDetector(DirectionListener directionListener,  OrthographicCamera camera) {
        super(new DirectionGestureListener(directionListener));
        this.camera = camera;
    }

    private static class DirectionGestureListener extends GestureDetector.GestureAdapter {
        DirectionListener directionListener;

        public DirectionGestureListener(DirectionListener directionListener){
            this.directionListener = directionListener;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if(Math.abs(velocityX)>Math.abs(velocityY)){
                if(velocityX>0){
                    directionListener.onRight();
                }else{
                    directionListener.onLeft();
                }
            }else{
                if(velocityY>0){
                    directionListener.onDown();
                }else if (velocityY<0){
                    directionListener.onUp();
                }
            }
            return super.fling(velocityX, velocityY, button);
        }

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        Vector3 v = new Vector3(screenX,screenY,0);
        camera.unproject(v);
        ArrayList<clickable> elements = clickableManager.getInstance().getClickables();
        for(int i = 0; i < elements.size(); i++)
        {
            if(elements.get(i).contains(v.x, v.y))
            {
                elements.get(i).setSelected(true);
            }
        }
        return super.touchDown(screenX,screenY,pointer,button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        Vector3 v = new Vector3(screenX,screenY,0);
        camera.unproject(v);
        ArrayList<clickable> elements = clickableManager.getInstance().getClickables();
        for(int i = 0; i < elements.size(); i++)
        {
            if(elements.get(i).contains(v.x, v.y) && elements.get(i).isSelected())
            {
                elements.get(i).onClick();
            }
            elements.get(i).setSelected(false);
        }
        return super.touchUp(screenX,screenY,pointer,button);
    }

}