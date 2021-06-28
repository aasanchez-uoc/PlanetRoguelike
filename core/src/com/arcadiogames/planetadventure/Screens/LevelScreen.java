package com.arcadiogames.planetadventure.Screens;

import com.arcadiogames.planetadventure.Logic.Dir;
import com.arcadiogames.planetadventure.Logic.LaserPlayer;
import com.arcadiogames.planetadventure.Logic.Sala;
import com.arcadiogames.planetadventure.Logic.bulletFactory;
import com.arcadiogames.planetadventure.Logic.collision.LaserCollision;
import com.arcadiogames.planetadventure.Logic.collision.collision;
import com.arcadiogames.planetadventure.Logic.collision.collisionManager;
import com.arcadiogames.planetadventure.Logic.drawManager;
import com.arcadiogames.planetadventure.Logic.gamePlayer;
import com.arcadiogames.planetadventure.Logic.gameStatus;
import com.arcadiogames.planetadventure.Logic.items.ItemElement;
import com.arcadiogames.planetadventure.Logic.moveManager;
import com.arcadiogames.planetadventure.Logic.playerEntity;
import com.arcadiogames.planetadventure.dungeonGame;
import com.arcadiogames.planetadventure.planetGen.backgroundGenerator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


/**
 * Created by Andres on 01/05/2017.
 */
public class LevelScreen implements Screen {
    dungeonGame game;
    public gameStatus status;
    private com.arcadiogames.planetadventure.Logic.roomEnemyCreator room;

    private boolean rotating = false;
    private boolean look_left = false;
    private boolean firing = false;
    private boolean closed = true;
    private float last_shot, stage_fps;
    private boolean dead = false;

    private ArrayList<Door> doors = new ArrayList<Door>();
    private ArrayList<itemEntity> items = new ArrayList<itemEntity>();
    private  ArrayList<LaserPlayer> laseres = new ArrayList<LaserPlayer>();

    ////grafico////////
    private SpriteBatch batch;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas.AtlasRegion full_heart, empty_heart, half_heart;
    private Skin skin;
    private staticElement Altar;
    private Animation<TextureRegion> bullet_anim;
    private TextureAtlas atlas, item_atlas;
    private com.arcadiogames.planetadventure.Logic.playerEntity player;
    private com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox laser_hitbox;
    private Actor fadeActor = new Actor();
    private ShapeRenderer fadeRenderer;
    private backgroundGenerator starsRenderer;

    private Label debugLabel;

    private com.arcadiogames.planetadventure.Screens.minimapRenderer minimapa;

    private Stage stage = new Stage();
    private ImageButton jumpButton, fireButton, walkButton, walkLeftButton;
    private textMessage message;
    private Dialog deadMessage;
    private boolean ended_rotating = true;

    private boolean walkingLeft = false;
    private boolean walkingRight = false;
    //////////////////

    private static int ROTATE_V = 350;

    public LevelScreen(final dungeonGame game, gameStatus status){
        this.game = game;
        this.status = status;



        //////////////Load////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        room = new com.arcadiogames.planetadventure.Logic.roomEnemyCreator();
        batch = new SpriteBatch();

        atlas =   game.Assets.get("textures/stage.atlas", TextureAtlas.class);
        item_atlas =  game.Assets.get("textures/items.atlas", TextureAtlas.class);
        skin = game.Assets.get("skins/uiSkin.json", Skin.class);

        fadeRenderer = new ShapeRenderer();
        fadeActor.setColor(Color.CLEAR);
        Json json = new Json();

        laser_hitbox = json.fromJson(com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox.class, Gdx.files.internal("textures/fire.json"));
        ////
        laser_hitbox.flip(30);
        ////

        starsRenderer = new backgroundGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), (int) status.getGameSeed());

        full_heart = atlas.findRegion("full_heart");
        empty_heart = atlas.findRegion("empty_heart");
        half_heart = atlas.findRegion("half_heart");

        //bullet_reg = atlas.findRegion("laser");
        bullet_anim = new Animation(0.1f,atlas.findRegions("fire"), Animation.PlayMode.LOOP);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ///////////////////dead message////////////////////////////////////////////////////////////////////////////////////////////////////////
        deadMessage = new Dialog("", skin)
        {
            protected void result(Object object)
            {
                if (object.equals(1L))
                {

                    game.setScreen(new LoadingScreen(game));
                    dispose();
                }
                else if (object.equals(2L))
                {
                    game.setScreen(new MainMenuScreen(game));
                    dispose();
                }
                else {

                }
            };

            @Override
            public float getPrefWidth() {
                // force dialog width
                return 480f;
            }

            @Override
            public float getPrefHeight() {
                // force dialog height
                return 240f;
            }

        };
        deadMessage.text(new Label("You Died", skin, "red-kal"));
        deadMessage.button("Retry", 1L);
        deadMessage.button("Exit", 2L);
        //deadMessage.setSize(3 * Gdx.graphics.getWidth() / 4, 200);
        deadMessage.setResizable(false);
        deadMessage.setMovable(false);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //player = new com.arcadiogames.planetadventure.Logic.playerEntity(new Animation(0.25f, atlas.findRegions("idle"), Animation.PlayMode.LOOP), idle_hitbox, status.getPlayer());
        player = new playerEntity( status.getPlayer(), atlas,  game.Assets.get("textures/weapons.atlas", TextureAtlas.class));
        player.setPosition(Gdx.graphics.getWidth()/2 - (player.getRegionWidth()/2) , 200);


        game.planet.setRotateV(ROTATE_V);


        camera = new OrthographicCamera();
        camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, camera.position.z);
        viewport = new FitViewport(Gdx.graphics.getWidth(),  Gdx.graphics.getHeight(), camera);
        moveManager.getInstance().setMax_x((int) (game.planet.getRadius() * 2 * Math.PI));
        moveManager.getInstance().setRotate_v(-1 * ROTATE_V);
        moveManager.getInstance().setOrigin(game.planet.getC_x(), game.planet.getC_y(), game.planet.getRadius());
        collisionManager.getInstance().setPlayerEntity(player);

        status.creaMapa();

        minimapa = new com.arcadiogames.planetadventure.Screens.minimapRenderer(0, Gdx.graphics.getHeight() -285, 285, 285);
        //minimapa = new minimapRenderer(0,0,Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());

        last_shot = status.getPlayer().getTearDelay() + 1;

        createButtons();

//        debugLabel = new Label("Hola", skin);
//        debugLabel.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() - debugLabel.getHeight());
//        stage.addActor(debugLabel);

        //create_room(status.getRoomSeed());
        create_doors();
    }

    private void createButtons() {
        jumpButton = new ImageButton (new TextureRegionDrawable( atlas.findRegion("jump_button") ),new TextureRegionDrawable( atlas.findRegion("jump_button_hig") ));
        fireButton = new ImageButton (new TextureRegionDrawable( atlas.findRegion("fire_button") ),new TextureRegionDrawable( atlas.findRegion("fire_button_hig") ));
        walkButton = new ImageButton (new TextureRegionDrawable( atlas.findRegion("walk_button") ),new TextureRegionDrawable( atlas.findRegion("walk_button_hig") ));
        walkLeftButton = new ImageButton (new TextureRegionDrawable( atlas.findRegion("walk_button_left") ),new TextureRegionDrawable( atlas.findRegion("walk_button_left_hig") ));

        fireButton.setPosition(Gdx.graphics.getWidth() - fireButton.getWidth(), 0);
        jumpButton.setPosition(0, walkButton.getHeight());
        walkButton.setPosition(walkLeftButton.getWidth(),0);

        walkButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                look_left = false;
                rotate();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ended_rotating = true;
                super.touchUp(event, x, y, pointer, button);
            }
        });

        walkLeftButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                look_left = true;
                rotate();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ended_rotating = true;
                super.touchUp(event, x, y, pointer, button);
            }
        });

        jumpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jump();
            }
        });
        fireButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                firePress();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                fireUp();
                super.touchUp(event, x, y, pointer, button);
            }
        });


        stage.addActor(jumpButton);
        stage.addActor(fireButton);
        stage.addActor(walkButton);
        stage.addActor(walkLeftButton);
        stage.addActor(fadeActor);
    }

    private void jump() {
        player.jump();
    }

    private void firePress(){
        if(status.getPlayer().getBulletType() == gamePlayer.bulletType.LASER) {


            int k = status.getPlayer().getBullet_number();
            if (k%2 == 1){
                laseres.add(new LaserPlayer(player, status.getPlayer(), 0, game.Assets.get("textures/bullets.atlas", TextureAtlas.class)));
                k-=1;
            }
            k = k/2;
            for(int i = 1; i<=k; i++) {
                    laseres.add(new LaserPlayer( player, status.getPlayer(), 30*i,  game.Assets.get("textures/bullets.atlas", TextureAtlas.class)));
                    laseres.add(new LaserPlayer( player,  status.getPlayer(), -30*i,  game.Assets.get("textures/bullets.atlas", TextureAtlas.class) ));
            }

        }
        else firing = true;
    }
    private void fireUp(){

        firing = false;
        for (LaserPlayer l:laseres
             ) {
            l.setToRemove(true);
        }
        laseres = new ArrayList<LaserPlayer>();
    }

    private void fire() {

        if( last_shot > status.getPlayer().getTearDelay()  && status.getPlayer().getBulletType() == gamePlayer.bulletType.DEFAULT){

                int k = status.getPlayer().getBullet_number();
                if (k%2 == 1){
                    if(look_left) {
                        new com.arcadiogames.planetadventure.Logic.bullet(-player.getRegionWidth() / 4, (int) player.getY(), status.getPlayer(), -1200, 0, bullet_anim, laser_hitbox, 1).setFlip(true, false);
                    }
                    else{
                        new com.arcadiogames.planetadventure.Logic.bullet(player.getRegionWidth()/4  , (int) player.getY() , status.getPlayer(), 1200, 0, bullet_anim, laser_hitbox,1);
                    }
                    k-=1;
                }

                k = k/2;
                for(int i = 1; i<=k; i++) {
                    if(look_left) {
                        new com.arcadiogames.planetadventure.Logic.bullet(-player.getRegionWidth() / 4, (int) player.getY(), status.getPlayer(), -1200, 30 * i , bullet_anim, laser_hitbox, 1).setFlip(true, false);
                        new com.arcadiogames.planetadventure.Logic.bullet(-player.getRegionWidth() / 4, (int) player.getY(), status.getPlayer(), -1200, -30 * i , bullet_anim, laser_hitbox, 1).setFlip(true, false);
                    }
                    else {

                        new com.arcadiogames.planetadventure.Logic.bullet(player.getRegionWidth()/4  , (int) player.getY() , status.getPlayer(), 1200, 30*i , bullet_anim, laser_hitbox,1);
                        new com.arcadiogames.planetadventure.Logic.bullet(player.getRegionWidth()/4  , (int) player.getY() , status.getPlayer(), 1200, -30*i, bullet_anim, laser_hitbox,1);
                    }
                }

            last_shot = 0;
        }

    }

    private void rotate() {
        if(! rotating){
            player.setFlip(look_left, false);
            if(look_left){
                //
                moveManager.getInstance().setRotate_v(ROTATE_V + status.getPlayer().getMove_speed());
                game.planet.setRotateV(ROTATE_V + status.getPlayer().getMove_speed());
            }
            else {
                moveManager.getInstance().setRotate_v(-1 * ROTATE_V - status.getPlayer().getMove_speed());
                game.planet.setRotateV(-1*ROTATE_V - status.getPlayer().getMove_speed());
            }
            moveManager.getInstance().starRotating();

            player.changeAnimation(playerEntity.animStatus.WALK);

            rotating = true;
            game.planet.setRotating(true);
            ended_rotating = false;
        }
    }

    private void create_room(long seed){

        int min_x = Gdx.graphics.getWidth()/2;
        int max_x = (int) (game.planet.getRadius()*2*Math.PI);
        int min_y = 50;
        int max_y = 300;
        room.generate_room(seed, min_x, max_x, min_y, max_y, game);


    }

    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new InputDetector(new InputDetector.DirectionListener() {
            @Override
            public void onLeft() {

            }

            @Override
            public void onRight() {

            }

            @Override
            public void onUp() {
                cross_door();
            }

            @Override
            public void onDown() {

            }
        }, camera));
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

    }

    private void cross_door() {
        if(!closed && doorFront()){
            //anim de entrar
            //setFade(0.5f);
            //anim entrar
            //!!
            if(getDoorFront().getTipo() == Door.Tipo.NORMAL) status.changeRoom(getDoorFrontDir());
            else status.changeLevel();

            //borrar los bichos, balas, puertas... que haya
            moveManager.getInstance().removeAll();
            collisionManager.getInstance().removeAll();
            doors.removeAll(doors);
            //

            closed = true;
            game.planet.newPlanet(status.getRoomSeed());
            game.planet.setAngleRads(status.getAnguloInicial());

            create_doors();



            //si la sala a la que entramos no está limpia generamos enemigos
            if(!status.getSalaActual().isLimpia() && status.getSalaActual().getTipo() == Sala.TipoSala.NORMAL){
                create_room(status.getRoomSeed());
            }

            if(status.getSalaActual().getTipo() == Sala.TipoSala.ITEM){
                dibujar_itemRoom();
            }

            load_items();

            game.setScreen(new LoadingScreenIngame(game,this));

        }
    }

    private void load_items() {
        items = new ArrayList<itemEntity>();
        for(ItemElement it : status.getSalaActual().getItems()){
            Sprite item_sprite = new Sprite(item_atlas.findRegion(it.getItem().getTextureName()));
            items.add( new itemEntity(this, it, item_sprite ));
        }
    }

    private void dibujar_itemRoom() {
        Dir altarPos = status.getAltarPos();
        //dibujar altar
        Sprite altar_sprite = new Sprite(atlas.findRegion("altar"));
        Altar = new staticElement( (int) (suma_rads(Dir.toAngle(altarPos), game.planet.getAngleRads())*game.planet.getRadius() ) , -50, altar_sprite);
    }



    private Dir getDoorFrontDir() {
        Dir dir = null;
        for(Door d: doors){
            if(d.getX() < Gdx.graphics.getWidth()/2 + d.getWidth()/2 && d.getX() > Gdx.graphics.getWidth()/2 -d.getWidth() && d.getY() >0){
                dir = d.getDir();
            }
        }
        return dir;
    }

    private Door getDoorFront() {
        Door door = null;
        for(Door d: doors){
            if(d.getX() < Gdx.graphics.getWidth()/2 + d.getWidth()/2 && d.getX() > Gdx.graphics.getWidth()/2 -d.getWidth() && d.getY() >0){
                door = d;
            }
        }
        return door;
    }

    private boolean doorFront() {
        boolean b = false;
        for(Door d: doors){
            //b = b || (d.getXbase() < d.getWidth()/2 && d.getXbase() >  -d.getWidth()/2 && d.getY() >0);
            b = b || (d.getX() < Gdx.graphics.getWidth()/2 + d.getWidth()/2 && d.getX() > Gdx.graphics.getWidth()/2 -d.getWidth() && d.getY() >0);
        }
        return b;
    }

    @Override
    public void render(float delta) {

        //!!!delete this/////////////////////////////////////////////////////
        InputEvent event1 = new InputEvent();                            ////
        event1.setType(InputEvent.Type.touchDown);                       ////
                                                                         ////
        InputEvent event2 = new InputEvent();
        event2.setType(InputEvent.Type.touchUp);
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){fire();}
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            jumpButton.fire(event1);
            jumpButton.fire(event2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            walkButton.fire(event1);
            walkingRight = true;
        }
        else{
            if(walkingRight){
                walkingRight = false;
                walkButton.fire(event2);
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            walkLeftButton.fire(event1);
            walkingLeft = true;

        }
        else{
            if(walkingLeft){
                walkingLeft = false;
                walkLeftButton.fire(event2);
            }

        }


        /////!!!!!//////////////////////////////////////////////////////////



        //String debug_string = Float.toString(planet.getAngleDegs());
       // debugLabel.setText( Float.toString(delta));

        if (delta > 0.03) delta = 0.03f;



        //logic update?
        bulletFactory.getInstance().update(delta);
        moveManager.getInstance().update(delta);
        drawManager.getInstance().update(delta);
        collisionManager.getInstance().update();


        //la colision de los laseres
        ArrayList<collision> lc = new ArrayList<collision>();
        for (LaserPlayer l: laseres
             ) {
            lc.add(l.getCollision());
        }
        collisionManager.getInstance().checkExtra(lc, 1);
        lc = null;
        ////////////


        last_shot += delta;
        if(firing) fire();
        if(closed && collisionManager.getInstance().isEmptyRoom()) {
            open_doors();
            status.getSalaActual().setLimpia();
        }
        if(!closed  && ! collisionManager.getInstance().isEmptyRoom()) close_doors();

        //
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);

        starsRenderer.render(delta);



        if(!rotating) {
           // create_doors(batch);
            game.planet.render(delta);
        }
        else{
            game.planet.render(delta);

//            suelo_anim.draw(batch);
//
           if(rotating && ended_rotating){
               moveManager.getInstance().stopRotating();
               rotating = false;
               game.planet.setRotating(false);
               player.changeAnimation(playerEntity.animStatus.IDLE);

           }

        }

        for (LaserPlayer l:laseres) {
            if(l.isFlip() != look_left) l.setFlip(look_left);
        }

        batch.begin();

        for(Door d : doors){
            d.draw(batch);
        }

        if(status.getSalaActual().getTipo() == Sala.TipoSala.ITEM){
            Altar.draw(batch);
        }

        ///items///////////////////////////
        ArrayList<itemEntity> toRemove = new ArrayList<itemEntity>();
        for(itemEntity it : items){
            if(! it.toRemove()) {
                it.draw(batch);
            }
            else{
                toRemove.add(it);
                status.getSalaActual().removeItem(it.getItemElem());
                clickableManager.getInstance().remove(it);
            }
        }
        items.removeAll(toRemove);
        toRemove = null;
        ///////////////////////////////////

        if(! status.getPlayer().isDead()) { //esto es un apaño, lo suyo seri hacer una amimacion yde muerte y luego dibujo de muerto o algo pero weno
            player.draw(batch);
        }
        moveManager.getInstance().draw(batch);
        drawManager.getInstance().draw(batch);

        draw_hearts();
        batch.end();

        minimapa.render(status, game.planet.getAngleDegs());

        if(status.getPlayer().isDead() && !dead){
            // deadMessage.show(stage);
            dead = true;
            deadMessage.show(stage);
        }

        stage.getViewport().apply();
        stage.act();
        stage.draw();



        float alpha = fadeActor.getColor().a;
        if (alpha != 0){
            Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
            fadeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            fadeRenderer.setColor(0, 0, 0, alpha);
            fadeRenderer.rect(-1, -1, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //full screen rect message/ identity matrix
            fadeRenderer.end();
        }

    }

    private void open_doors() {
        for(Door d: doors){
            if(d.getTipo() == Door.Tipo.NORMAL) d.open(new Animation(0.25f, atlas.findRegions("door_anim"), Animation.PlayMode.LOOP));
            else if (d.getTipo() == Door.Tipo.BOSS) d.open(new Animation(0.1f, atlas.findRegions("portal_anim"), Animation.PlayMode.LOOP));
        }


        closed = false;
    }

    private void close_doors(){

    }


    private void create_doors() {
        ArrayList< Dir> dirs = new ArrayList<Dir>();
        dirs.add(Dir.N);
        dirs.add(Dir.E);
        dirs.add(Dir.S);
        dirs.add(Dir.W);

        for(Dir d: dirs){
            if (status.hasDoor(d)){
                doors.add(new Door((int) (suma_rads(Dir.toAngle(d), game.planet.getAngleRads())*game.planet.getRadius() ) , -30, new Animation(0.25f, atlas.findRegions("door_open"), Animation.PlayMode.NORMAL), d));
            }
        }

        if(status.getSalaActual().getTipo() == Sala.TipoSala.BOSS){
            Dir d = status.getLevelPortalPos();
            Door bossD = new Door((int) (suma_rads(Dir.toAngle(d), game.planet.getAngleRads())*game.planet.getRadius() ) , -30, new Animation(0.25f, atlas.findRegions("portal_open"), Animation.PlayMode.NORMAL), d);
            bossD.setTipo(Door.Tipo.BOSS);
            doors.add(bossD);
        }
    }

    private float suma_rads(float a, float b){
        float s = a + b;
        if ( s < 0) s += 2 * Math.PI;
        if(s > 2*Math.PI) s -= Math.PI;
        return  s;
    }

    private void draw_hearts() {
        int startX  = Gdx.graphics.getWidth()/2 - (full_heart.getRegionWidth()*3);
        int startY = full_heart.getRegionHeight();
        int fh = status.getPlayer().get_full_hearts();
        int mh = status.getPlayer().getMax_hearts();
        int i = 0;
        while (i < fh){
            int row = i/6;
            batch.draw(full_heart, startX + (i -6*row)*full_heart.getRegionWidth() , startY + row*full_heart.getRegionHeight());
            i++;
        }
        if(status.getPlayer().has_half_heart()){
            int row = i/6;
            batch.draw(half_heart, startX + (i -6*row)*full_heart.getRegionWidth() , startY + row*full_heart.getRegionHeight());
            i++;
        }
        while (i < mh ){
            int row = i/6;
            batch.draw(empty_heart, startX +  (i -6*row)*full_heart.getRegionWidth() , startY + row*full_heart.getRegionHeight());
            i++;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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

        moveManager.getInstance().removeAll();
        collisionManager.getInstance().removeAll();
        drawManager.getInstance().removeAll();

        bulletFactory.getInstance().removeAll();
        clickableManager.getInstance().clean();
        stage.dispose();
        batch.dispose();
//        skin.dispose();
//        atlas.dispose();
//        item_atlas.dispose();
        fadeRenderer.dispose();
//
//
//
////        private com.arcadiogames.planetadventure.Logic.playerEntity player;
////        private com.arcadiogames.planetadventure.Logic.collision.AnimationHitbox laser_hitbox;
////        private backgroundGenerator starsRenderer;
////        private staticElement Altar;
////        private com.arcadiogames.planetadventure.Screens.minimapRenderer minimapa;

    }

    public void setFade (float duration) {
        fadeActor.clearActions();
        fadeActor.setColor(Color.CLEAR);
        fadeActor.addAction(Actions.sequence(
                Actions.fadeIn(duration/2f),
                Actions.fadeOut(duration / 2f)
        ));
    }

    public void showLabel(String name, String description) {
        //
        message = new textMessage(name, description, skin);
        message.setSize(3*Gdx.graphics.getWidth()/4, 200);
        message.setPosition(Gdx.graphics.getWidth() / 2 - message.getWidth()/2 , Gdx.graphics.getHeight() / 2 - message.getHeight()/2);
        stage.addActor(message);
    }
}
