package com.arcadiogames.planetadventure.Logic;


import com.arcadiogames.planetadventure.Logic.items.ItemPool;
import com.arcadiogames.planetadventure.Util.extRandom;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by Andres on 05/05/2017.
 */
public class gameStatus {
    private int level;
    private gamePlayer player;
    private com.arcadiogames.planetadventure.Logic.Sala salaActual;
    private extRandom random;
    private long gameSeed;
    private int rooms, total_rooms, N;

    private com.arcadiogames.planetadventure.Logic.Dir dirInicial;
    private com.arcadiogames.planetadventure.Logic.Sala[][] Mapa;
    private ItemPool pool;

    public gameStatus(){
        dirInicial = Dir.N;
        level = 1;
        player = new gamePlayer();
        gameSeed = new Random().nextLong();
        random = new extRandom(gameSeed);
        pool = new ItemPool(gameSeed);
        setNumberRooms(level);
          }

    public void changeLevel(){
        //cuidado con la seed
        dirInicial = Dir.N;
        level++;
        gameSeed = gameSeed + (level -1);
        random = new extRandom(gameSeed);
        setNumberRooms(level);
        creaMapa();
    }

    public void setNumberRooms(int level){
        rooms = 7;  ///meter variaciones aqui en func del nivel actual
        total_rooms = rooms + 1 + 1; //boss y sala de objetos
        N = total_rooms/2 +1;
    }

    public com.arcadiogames.planetadventure.Logic.Sala[][] getMapa() {
        return Mapa;
    }

    public gamePlayer getPlayer(){
        return player;
    }


    public com.arcadiogames.planetadventure.Logic.Sala getSalaActual() {
        return salaActual;
    }

    public void setSalaActual(com.arcadiogames.planetadventure.Logic.Sala sala) {
        sala.setVisitada(true);
        this.salaActual = sala;
    }

    public long getGameSeed() {
        return gameSeed;
    }

    public void creaMapa(){
        int x,y;
        Point p;
        HashSet<Point> posibles = new HashSet<Point>();
        HashSet<Point> especiales = new HashSet<Point>();
        ////////////////////
        Mapa = new com.arcadiogames.planetadventure.Logic.Sala[N][N];
        int i=0;
        //inicializarlo??
        x = N/2;
        y = N/2;

        //Mapa[x][y] = new Sala(x,y);
        //setSalaActual(Mapa[x][y]);
        setSalaActual(new com.arcadiogames.planetadventure.Logic.Sala(x,y));
        Mapa[x][y] = salaActual;

        posibles.remove(new Point(x,y));
        if (x + 1 < N) {
            posibles.add(new Point(x + 1, y));
        }
        if (x - 1 >= 0) {
            posibles.add(new Point(x - 1, y));
        }
        if (y + 1 < N) {
            posibles.add(new Point(x, y + 1));
        }
        if (y - 1 >= 0) {
            posibles.add(new Point(x, y - 1));
        }
        i++;

        while(i < rooms || posibles.isEmpty()) {

            p = pickPoint(posibles);
            x = p.x;
            y = p.y;


            Mapa[x][y] = new com.arcadiogames.planetadventure.Logic.Sala(x,y);
            posibles.remove(p);
            if (x + 1 < N && Mapa[x+1][y] == null) {
                posibles.add(new Point(x + 1, y));
            }
            if (x - 1 >= 0 && Mapa[x-1][y] == null) {
                posibles.add(new Point(x - 1, y));
            }
            if (y + 1 < N && Mapa[x][y+1] == null) {
                posibles.add(new Point(x, y + 1));
            }
            if (y - 1 >= 0&& Mapa[x][y-1] == null) {
                posibles.add(new Point(x, y - 1));
            }
            i++;
        }

        //ahora las salas especiales:

        //lista en la que puede haber salas especiales
        for(Point pos:posibles){
            x = pos.x;
            y = pos.y;
            int ady = 0;
            if (x + 1 < N && Mapa[x+1][y] != null) {
                ady++;
            }
            if (x - 1 >= 0 && Mapa[x-1][y] != null) {
               ady++;
            }
            if (y + 1 < N && Mapa[x][y+1] != null) {
                ady++;
            }
            if (y - 1 >= 0&& Mapa[x][y-1] != null) {
               ady++;
            }
            if(ady == 1) especiales.add(pos);
        }

        p = pickPoint(especiales);
        x = p.x;
        y = p.y;
        Mapa[x][y] = new com.arcadiogames.planetadventure.Logic.Sala(Sala.TipoSala.ITEM, x,y);
        Mapa[x][y].addItem((int) (Dir.toAngle(getAltarPos(Mapa[x][y])) * moveManager.getInstance().getRadius()), 180, pool.getItem());

        //actualizar especiales: quitamos la seleccionada y todas las adyacentes a la seleccionada
        especiales.remove(p);
        Point paux = new Point(x+1, y);
        if( especiales.contains(paux)) especiales.remove(paux);
        paux = new Point(x-1, y);
        if( especiales.contains(paux)) especiales.remove(paux);
        paux = new Point(x, y+1);
        if( especiales.contains(paux)) especiales.remove(paux);
        paux = new Point(x, y-1);
        if( especiales.contains(paux)) especiales.remove(paux);

        p = pickPoint(especiales);
        x = p.x;
        y = p.y;
        Mapa[x][y] = new com.arcadiogames.planetadventure.Logic.Sala(Sala.TipoSala.BOSS, x,y);
    }

    Point pickPoint(HashSet<Point> posibles){
        int size = posibles.size();
        int item = random.nextInt(size);
        int i = 0;
        for(Point p : posibles)
        {
            if (i == item)
                return p;
            i++;
        }
        return null;
    }



    public boolean hasDoor(com.arcadiogames.planetadventure.Logic.Dir dir, Sala sala){
        boolean b = false;
        Point p = sala.getCoord();
        switch (dir) {
            case S:
                if (p.y - 1 >= 0){
                    b = Mapa[p.x][p.y-1] != null;
                }
                break;
            case E:
                if (p.x + 1 < Mapa[0].length){
                    b = Mapa[p.x + 1][p.y] != null;
                }
                break;
            case N:
                if (p.y + 1 < Mapa.length){
                    b = Mapa[p.x][p.y+1] != null;
                }
                break;
            case W:
                if (p.x - 1 >= 0){
                    b = Mapa[p.x - 1][p.y] != null;
                }
                break;
        }
        return b;
    }

    public boolean hasDoor(com.arcadiogames.planetadventure.Logic.Dir dir) {
        return hasDoor(dir, salaActual);
    }

    public void changeRoom(Dir dir){

        Point p = salaActual.getCoord();
        switch (dir) {
            case S:
                if (p.y - 1 >= 0){
                    setSalaActual(Mapa[p.x][p.y-1]);
                    dirInicial = com.arcadiogames.planetadventure.Logic.Dir.N;
                }
                break;
            case E:
                if (p.x + 1 < Mapa[0].length){
                    setSalaActual( Mapa[p.x + 1][p.y]);
                    dirInicial = com.arcadiogames.planetadventure.Logic.Dir.W;
                }
                break;
            case N:
                if (p.y + 1 < Mapa.length){
                    setSalaActual(Mapa[p.x][p.y+1]);
                    dirInicial = com.arcadiogames.planetadventure.Logic.Dir.S;
                }
                break;
            case W:
                if (p.x - 1 >= 0){
                    setSalaActual(Mapa[p.x - 1][p.y]);
                    dirInicial = com.arcadiogames.planetadventure.Logic.Dir.E;
                }
                break;
        }
    }

    public float getAnguloInicial(){
        return Dir.toAngle(dirInicial);
    }

    public long getRoomSeed() {
       return (getGameSeed() +  getSalaActual().getCoord().getX())*(getSalaActual().getCoord().getY());
    }

    public  long getInitialRoomSeed(){
        return  (getGameSeed() + N/2)*N/2;
    }

    public Dir getAltarPos(Sala salaIem){
        Dir altarPos = Dir.N;
        if (hasDoor(Dir.N, salaIem)){
            altarPos = Dir.E;
        }
        if (hasDoor(Dir.E, salaIem)){
            altarPos = Dir.S;
        }
        if (hasDoor(Dir.S, salaIem)){
            altarPos = Dir.W;
        }
        if (hasDoor(Dir.W, salaIem)){
            altarPos = Dir.N;
        }
        return altarPos;
    }

    public Dir getAltarPos(){
        return getAltarPos(salaActual);
    }

    public Dir getLevelPortalPos(Sala salaIem){
        Dir altarPos = Dir.N;
        if (hasDoor(Dir.N, salaIem)){
            altarPos = Dir.S;
        }
        if (hasDoor(Dir.E, salaIem)){
            altarPos = Dir.W;
        }
        if (hasDoor(Dir.S, salaIem)){
            altarPos = Dir.N;
        }
        if (hasDoor(Dir.W, salaIem)){
            altarPos = Dir.E;
        }
        return altarPos;
    }
    public Dir getLevelPortalPos(){
        return getLevelPortalPos(salaActual);
    }
}
