package com.arcadiogames.planetadventure.Logic.collision;

import com.arcadiogames.planetadventure.Screens.animEntity;

import java.util.ArrayList;

/**
 * Created by Andres on 30/09/2017.
 */
public class collisionManager {
    private animEntity playerEntity; //tipo 0
    private ArrayList<collision> player_bullets; //tipo = 1
    private ArrayList<collision> enemy_bullets; //tipo = 2
    private ArrayList<collision> enemies; //tipo = 3
    private ArrayList<collision> other; //tipo = resto


    private static collisionManager ourInstance = new collisionManager();

    public static collisionManager getInstance() {
        return ourInstance;
    }

    public collisionManager() {
        player_bullets = new ArrayList<collision>();
        enemy_bullets = new ArrayList<collision>();
        enemies = new ArrayList<collision>();
        other = new ArrayList<collision>();
    }

    public void setPlayerEntity(animEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void add(collision e, int tipo){
        switch (tipo){
            case 1: player_bullets.add(e);
                break;
            case 2:
                enemy_bullets.add(e);
                break;
            case 3:
                enemies.add(e);
                break;
            default:
                other.add(e);
        }
    }

    public void update(){

        //primero borramos los que ya no valen
        ArrayList<collision> toRemove = new ArrayList<collision>();
        for (collision e:player_bullets) {
            if (e.toRemove()){
                toRemove.add(e);
                e = null;
            }
        }
        player_bullets.removeAll(toRemove);

        toRemove = new ArrayList<collision>();
        for (collision e:enemy_bullets) {
            if (e.toRemove()){
                toRemove.add(e);
                e = null;
            }
        }
        enemy_bullets.removeAll(toRemove);

        toRemove = new ArrayList<collision>();
        for (collision e:enemies) {
            if (e.toRemove()){
                toRemove.add(e);
                e = null;
            }
        }
        enemies.removeAll(toRemove);

        toRemove = new ArrayList<collision>();
        for (collision e:other) {
            if (e.toRemove()){
                toRemove.add(e);
                e = null;
            }
        }
        other.removeAll(toRemove);
        toRemove = null;

        //buscamos colisiones de las balas del jugador con los enemigos
        for (collision bullet:player_bullets) {
            for (collision enemy: enemies) {
                if (bullet.overlaps(enemy)){
                    bullet.on_hit(3, enemy);
                    enemy.on_hit(1, bullet);
                }
            }
        }

        //colisiones de las balas enemigas con el jugador
        for(collision bullet:enemy_bullets){
            if(playerEntity.overlaps(bullet)){
                playerEntity.on_hit(2, bullet);
                bullet.on_hit(0, playerEntity);
            }
        }

        //colisiones de enemigos con jugador
        for(collision enemy: enemies){
            if(playerEntity.overlaps(enemy)){
                playerEntity.on_hit(3, enemy);
                enemy.on_hit(0, playerEntity);
            }
        }

        //faltan colisiones con other!!!
    }

    public void checkExtra (ArrayList<collision> lista, int tipo){
        //if(tipo == 1){
        for (collision c:lista) {
            for (collision enemy: enemies) {
                if (c.overlaps(enemy)){
                    c.on_hit(3, enemy);
                    enemy.on_hit(1, c);
                }
            }
        }
        //}
    }

    public boolean isEmptyRoom() {
        return enemies.size() == 0;
    }

    public void  removeAll(){
        ArrayList<collision> toRemove = new ArrayList<collision>();
        for (collision e:player_bullets) {
                toRemove.add(e);
                e = null;
        }
        player_bullets.removeAll(toRemove);

        toRemove = new ArrayList<collision>();
        for (collision e:enemy_bullets) {
                toRemove.add(e);
                e = null;
        }
        enemy_bullets.removeAll(toRemove);

        toRemove = new ArrayList<collision>();
        for (collision e:enemies) {
                toRemove.add(e);
                e = null;

        }
        enemies.removeAll(toRemove);

        toRemove = new ArrayList<collision>();
        for (collision e:other) {
                toRemove.add(e);
                e = null;
        }
        other.removeAll(toRemove);
    }
}
