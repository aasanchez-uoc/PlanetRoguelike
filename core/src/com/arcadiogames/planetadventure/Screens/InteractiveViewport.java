package com.arcadiogames.planetadventure.Screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Andres on 06/05/2017.
 */
public class InteractiveViewport extends ScreenViewport {

    public InteractiveViewport(OrthographicCamera camera){
        super(camera);
    }

    public void update (boolean centreCamera) {
        setScreenBounds(getScreenX(), getScreenY(), getScreenWidth(), getScreenHeight());
        setWorldSize(getScreenWidth() * getUnitsPerPixel(), getScreenHeight() * getUnitsPerPixel());
        apply(centreCamera);
    }

}