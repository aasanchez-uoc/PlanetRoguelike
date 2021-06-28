package com.arcadiogames.planetadventure.Logic.enemyRooms;

/**
 * Created by Andres on 06/11/2017.
 * Esta clase está para mantener la estrucutra de los creadores de habitacion
 * Cada tipo de habitacion sera un clase nueva heredada de esta
 * cada tipo de habitación tiene ciertos tipos de enemigos en unos rangos de cantidades
 */
abstract public class roomGen {
    protected int min_x, max_x, min_y, max_y;
    protected long seed;

    public roomGen(int min_x, int max_x, int min_y, int max_y, long seed) {
        this.min_x = min_x;
        this.max_x = max_x;
        this.min_y = min_y;
        this.max_y = max_y;
        this.seed = seed;
    }
}
