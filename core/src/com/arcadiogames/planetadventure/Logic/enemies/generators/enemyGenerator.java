package com.arcadiogames.planetadventure.Logic.enemies.generators;

/**
 * Created by Andres on 06/11/2017.
 * Aqui va la parte de crear cada tipo de enemigo concreto cada tipo de nemigo es una clase nueva
 * se ha separado en clases para poder desde aquí manejar la posibilidad de que haya variaciones de los enemigos
 * y las chances de estas variaciones y elegir una en funcion de la seed proporcionada
 */
public abstract class enemyGenerator {
    public abstract void genEnemy(int x, int y, long seed);
}
