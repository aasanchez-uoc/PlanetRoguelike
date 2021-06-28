package com.arcadiogames.planetadventure.Logic;

/**
 * Created by Andres on 05/05/2017.
 */
public class Point {
    int x,y;

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public Point(int x, int y){
        this.x= x;
        this.y =y;
    }

    public int getX(){return x;}
    public int getY(){return y;}

    @Override
    public boolean equals(Object o){
        boolean result = false;
        if (o instanceof Point) {
            Point that = (Point) o;
            result = (this.getX() == that.getX() && this.getY() == that.getY());
        }
        return result;
    }

    public void setX(int x) {
        this.x = x;
    }
}
