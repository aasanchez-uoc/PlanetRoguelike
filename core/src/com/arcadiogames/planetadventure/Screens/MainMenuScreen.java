package com.arcadiogames.planetadventure.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Andres on 01/05/2017.
 */
public class MainMenuScreen implements Screen {
    private Skin skin;
    private Stage stage = new Stage();
    private Table table = new Table();
    private com.arcadiogames.planetadventure.dungeonGame game;
    private TextButton buttonPlay, buttonLoad, buttonOptions, buttonExit;
    private Label title;
    private Texture backgroundTexture;

    //deletethis:

    public MainMenuScreen(com.arcadiogames.planetadventure.dungeonGame dungeonGame) {
        this.game = dungeonGame;
        skin = game.Assets.get("skins/uiSkin.json", Skin.class);
        buttonPlay = new TextButton(com.arcadiogames.planetadventure.Translator.getInstance().getBundle().get("play"), skin);
        buttonLoad = new TextButton(com.arcadiogames.planetadventure.Translator.getInstance().getBundle().get("load"), skin);
        buttonOptions = new TextButton(com.arcadiogames.planetadventure.Translator.getInstance().getBundle().get("options"), skin);
        buttonExit = new TextButton(com.arcadiogames.planetadventure.Translator.getInstance().getBundle().get("exit"), skin);
        title = new Label(com.arcadiogames.planetadventure.Translator.getInstance().getBundle().get("game"),skin);
        backgroundTexture = new Texture(Gdx.files.internal("textures/back.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.act();
        stage.draw();

    }
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {game.setScreen(new LoadingScreen(game));}
        });

        buttonLoad.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {game.setScreen(new LoadingScreen(game));}
        });

        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                // or System.exit(0);
            }
        });

        //The elements are displayed in the order you add them.
        //The first appear on top, the last at the bottom.
        table.add(title).padBottom(40).row();
        table.add(buttonPlay).size(300, 120).padBottom(20).row();
        table.add(buttonLoad).size(300, 120).padBottom(20).row();
        table.add(buttonOptions).size(300, 120).padBottom(20).row();
        table.add(buttonExit).size(300, 120).padBottom(20).row();

        table.setFillParent(true);
        stage.addActor(table);
        //game.sound.PlaySong("sounds/Aloittamis(mp3).mp3");
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
