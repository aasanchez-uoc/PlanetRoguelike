package com.arcadiogames.planetadventure.Screens;

import com.arcadiogames.planetadventure.Logic.Sala;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Andres on 06/05/2017.
 */
public class minimapRenderer {
    private InteractiveViewport viewport;
    private SpriteBatch batch;
    private TextureAtlas.AtlasRegion visited, unvisited, current, flecha, item, boss;
    private TextureAtlas atlas;

    public minimapRenderer(int screenX,int screenY,int screenWidth,int screenHeight){
        this.batch = new SpriteBatch();
        atlas = new TextureAtlas(Gdx.files.internal("textures/minimap.atlas"));
        visited = atlas.findRegion("visited");
        unvisited = atlas.findRegion("unvisited");
        current = atlas.findRegion("current");
        flecha = atlas.findRegion("N");
        item = atlas.findRegion("object");
        boss = atlas.findRegion("boss");
        this.viewport = new InteractiveViewport(new OrthographicCamera());
        this.viewport.setScreenBounds(screenX, screenY, screenWidth, screenHeight);
        this.viewport.update(true);
    }

    public void render( com.arcadiogames.planetadventure.Logic.gameStatus status, float angle){
        viewport.update(false);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        com.arcadiogames.planetadventure.Logic.Sala sala;
        for(int i = 0; i < status.getMapa().length; i++){
            for(int j = 0; j < status.getMapa()[0].length; j++){
                sala = status.getMapa()[i][j];
                if ( sala != null){
                    if(sala.getCoord().equals(status.getSalaActual().getCoord())){
                        batch.draw(current, i*current.getRegionWidth(),j*current.getRegionHeight());
                        if(sala.getTipo().equals(Sala.TipoSala.ITEM))batch.draw(item, i*current.getRegionWidth(),j*current.getRegionHeight());
                        if(sala.getTipo().equals(Sala.TipoSala.BOSS))batch.draw(boss, i*current.getRegionWidth(),j*current.getRegionHeight());
                        render_flecha(angle ,i*current.getRegionWidth(),j*current.getRegionHeight());
                    }
                    else{
                        if(sala.isVisitada()){
                            batch.draw(visited, i*current.getRegionWidth(),j*current.getRegionHeight());
                        }
                        else{
                            batch.draw(unvisited, i*current.getRegionWidth(),j*current.getRegionHeight());
                        }
                        if(sala.getTipo().equals(Sala.TipoSala.ITEM))batch.draw(item, i*current.getRegionWidth(),j*current.getRegionHeight());
                        if(sala.getTipo().equals(Sala.TipoSala.BOSS))batch.draw(boss, i*current.getRegionWidth(),j*current.getRegionHeight());
                    }


                }
            }
        }
        batch.end();
    }

    private void render_flecha(float angle,int x, int y) {
        //batch.draw(flecha,x,y);
        batch.draw(flecha, x ,y, flecha.getRegionWidth()/2, flecha.getRegionHeight()/2, flecha.getRegionWidth(),flecha.getRegionHeight(),1,1,angle);
    }
}
