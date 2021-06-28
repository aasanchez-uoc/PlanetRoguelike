package com.arcadiogames.planetadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * Created by Andres on 01/05/2017.
 */
public class Translator {
    I18NBundle myBundle;
    private static Translator ourInstance = new Translator();

    public static Translator getInstance() {
        return ourInstance;
    }

    private Translator() {
        FileHandle baseFileHandle = Gdx.files.internal("i18n/common");
        myBundle = I18NBundle.createBundle(baseFileHandle);
    }

    public I18NBundle getBundle() {
        return myBundle;
    }


}
