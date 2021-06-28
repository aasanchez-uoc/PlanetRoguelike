package com.arcadiogames.planetadventure;


import com.arcadiogames.planetadventure.Screens.MainMenuScreen;
import com.arcadiogames.planetadventure.planetGen.planetRenderer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class dungeonGame extends Game {
	private FPSLogger fpsLogger;
	public AssetManager Assets;
	public planetRenderer planet;

	public dungeonGame() {
		Assets = new AssetManager();
	}

	;
	
	@Override
	public void create () {
//		Settings.load();
//		Assets.load();
		Assets.load("skins/uiSkin.atlas" , TextureAtlas.class);
		Assets.load("skins/uiSkin.json", Skin.class, new SkinLoader.SkinParameter("skins/uiSkin.atlas" ));


		Assets.finishLoading();
//		sound = new SoundSubSytem();
		fpsLogger = new FPSLogger();
		Translator.getInstance();
//		status = new BasicStatus();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
		//fpsLogger.log();
	}
	
	@Override
	public void dispose () {

	}
}
