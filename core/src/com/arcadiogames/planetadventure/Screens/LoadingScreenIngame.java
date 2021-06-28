package com.arcadiogames.planetadventure.Screens;

import com.arcadiogames.planetadventure.Logic.gameStatus;
import com.arcadiogames.planetadventure.dungeonGame;
import com.arcadiogames.planetadventure.planetGen.planetRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;

/**
 * Created by Andres on 20/12/2017.
 */
public class LoadingScreenIngame implements Screen {
    dungeonGame game;
    LevelScreen screen;
    private Skin skin;
    private Stage stage = new Stage();
    private Label loading_label;
    private float time = 0;
    private float dot_time = 0.5f;
    private int count = 0;
    private gameStatus status;

    AsyncExecutor asyncExecutor = new AsyncExecutor(10);

    AsyncResult<Void> task_planet;

    public LoadingScreenIngame(final dungeonGame game, LevelScreen screen){
        this.game = game;
        this.screen = screen;
        stage = new Stage();
        skin = game.Assets.get("skins/uiSkin.json", Skin.class);
        loading_label = new Label("Loading", skin);
        //loading_label.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);


        status = new gameStatus();


        task_planet = asyncExecutor.submit(new AsyncTask<Void>() {
            public Void call() {
                game.planet.dibujar_planeta();
                return null;
            }
        });



    }

    @Override
    public void show() {
        stage.addActor(loading_label);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        time += delta;

        if (time > dot_time){
            time = 0;
            count++;
            loading_label.setText( loading_label.getText() +".");
        }

        if (count >3){
            count = 0;
            loading_label.setText("Loading");
        }

        if (game.Assets.update() && task_planet.isDone()){
            game.planet.finish_dibujar();
            game.setScreen(screen);
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
