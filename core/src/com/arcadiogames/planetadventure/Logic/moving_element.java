package com.arcadiogames.planetadventure.Logic;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Andres on 06/05/2017.
 */
public abstract class moving_element {
    protected float v_x, v_y, kv_x, kv_y, a2; //kv es para el efecto de knockback, podria añadirse una lista de velocidades o algo para tener N pero no le veo necesario
    protected int rotate_v = 0;
    private float offset;
    protected float max_x;
    private float m_x,m_y;
    private float angle = 0;
    protected float cx, cy, radius;
    protected int trayectoria = 1; //1 recta, 2 seno? nu se
    private boolean rotating, lineal = false;
    private boolean drawable = true;
    protected float A,k1,k2;


    public moving_element(int x, int y){
        m_x = x;
        m_y = y;
        moveManager.getInstance().add(this);

        ///
        A = 0;
        k1 = 1;
        k2 = 1;
        //
        a2 =  900;//aceleracion del knockback
    }

    private float calcular_trayectoria(float x){
        float v = v_y;
        if(trayectoria == 1){
           v = A*x;
        }
        else if(trayectoria == 2){
            v = (float) ( k1*k2*v_x*Math.cos(k2 * x + A));
        }
        return v;
    }

    public void update(float delta){
        v_y = calcular_trayectoria(m_x);
        update_knockback(delta);

        m_x = m_x + delta*(v_x) + kv_x;
        m_y = m_y + delta*(v_y + kv_y);
        offset = offset + delta * rotate_v;
        angle = (m_x + offset) / (radius);
    }

    private void update_knockback(float delta) {
        kv_x = kv_x*0.9f;
        kv_y = kv_y*0.9f;
    }

    protected void start_knockback(int dir) {
            if(dir != 0) dir = dir/ Math.abs(dir);
            kv_x = dir*10;
            kv_y = -10*dir;

    }

    public abstract void draw(Batch batch);

    public float getX() {
        if (lineal) return get_linealX();
        //float recol = (float) (Math.cos(5*Math.PI/4 + angle) - Math.cos(5*Math.PI/4));
        else return (float) (cx -getWidth()/2 + (m_y + radius + getHeight()/2)* Math.sin(angle));
    }

    public float get_linealX() {return (m_x + offset);
    }

    public float getXbase() {return m_x;
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public float getY() {
        if (lineal) return getLinealY();
        else return (float) (cy - getHeight()/2 + (m_y + radius + getHeight()/2)* Math.cos(angle));
    }

    public  float getLinealY(){return m_y;}

    abstract public float getWidth();
    abstract public float getHeight();

    public void setX(float i) {
        m_x = i;
    }

    public void adjustPolarCoords(){
        angle = (m_x + offset) / (radius);
        setX(getX());
        m_y = getY();

    }

    public boolean isLineal() {
        return lineal;
    }

    public void setLineal(boolean lineal) {
        this.lineal = lineal;
    }

    public void setLineal(){
        adjustPolarCoords();
        setLineal(true);
    }

    public float getAngle() {
        return angle;
    }

    public void addV(int v){
        v_x += v;
    }

    public void subsV(int v) {
        v_x -= v;
    }

    public void setMax_x(float max_x) {
        this.max_x = max_x;
    }

    public abstract boolean isDead();

    public float getV_x() {
        return v_x;
    }

    public void setV_x(int v_x) {
        this.v_x = v_x;
    }

    public float getV_y() {
        return v_y;
    }

    public void setV_y(int v_y) {
        this.v_y = v_y;
    }

    public float getA() {
        return A;
    }

    public void setA(float a) {
        A = a;
    }

    public float getK1() {
        return k1;
    }

    public void setK1(float k1) {
        this.k1 = k1;
    }

    public float getK2() {
        return k2;
    }

    public void setK2(float k2) {
        this.k2 = k2;
    }

    public int getTrayectoria() {
        return trayectoria;
    }

    public void setTrayectoria(int trayectoria) {
        this.trayectoria = trayectoria;
    }

    public int getRotate_v() {
        return rotate_v;
    }

    public void setRotate_v(int rotate_v) {
        this.rotate_v = rotate_v;
    }

    public void setOrigin(float cx, float cy, float radius) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
    }

    public boolean isDrawable() {
        return drawable;
    }

    public void setDrawable(boolean drawable) {
        this.drawable = drawable;
    }
}
