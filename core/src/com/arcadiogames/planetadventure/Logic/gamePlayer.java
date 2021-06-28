package com.arcadiogames.planetadventure.Logic;

/**
 * Created by Andres on 05/05/2017.
 */
public class gamePlayer {
    private int max_hearts = 10;
    private int curr_half_hearts = 11;

    private float range;
    private float bullet_size;
    private int move_speed;
    //private int jump_size ?
    //private int gravity ???
    private float shot_rate;
    private int bullet_number;


    //efectos objetos
    private boolean linealBullets = false;
    private boolean backward_shot = false;

    private com.arcadiogames.planetadventure.Logic.stats.Stat Damage;

    private weaponType weaponT = weaponType.DEFAULT;

    public boolean isDead() {
        return curr_half_hearts <= 0;
    }

    public enum weaponType {DEFAULT, LASERGUN, LASERCANNON, REVOLVER,SHOTGUN;};

    private bulletType bulletT = bulletType.LASER;
    public enum bulletType {DEFAULT, LASER, CANNON};

    public gamePlayer(){
        range = 7.0f;  //es un porcentaje [0,100]
        bullet_size = 1f;
        shot_rate = 0f; // [-1,1]
        move_speed = 100; // velocidad añadida a la base, [1,200]
        bullet_number = 2;  //[1,5]
        Damage = new com.arcadiogames.planetadventure.Logic.stats.Stat(2.5f);
    }

    public int get_full_hearts(){
        return curr_half_hearts/2;
    }

    public boolean has_half_heart(){
        return curr_half_hearts%2 == 1;
    }

    public  int getMax_hearts(){return  max_hearts;}

    public void increaseHearts(int n){
        max_hearts += n;
        //poner un maximo de corazones + escudos!!
    }

    public float getTearDelayOld(){
        // es la formula del isaac, son frames a 30fps, voy a rehacerla un pelin
        return (float) (16f -(6f * Math.sqrt(shot_rate*1.3f +1f )));
    }

    public float getTearDelay(){
        if( shot_rate < -0.35) return  (1/2f -((1/5f)*shot_rate));
        else return (float) (1/2f -((1/5f) * Math.sqrt(shot_rate*1.4f +1f )));
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
        if (this.range > 100) this.range = 100;
    }

    public void decreaseFireSpeed(float k){
        shot_rate -= k;
        if(shot_rate > 1) shot_rate = 1;
        if(shot_rate < -1) shot_rate = -1;
    }

    public float getDamage() {
        return Damage.getFinalValue();
    }

    public void hurt(){
        curr_half_hearts--;
        if (curr_half_hearts <0) curr_half_hearts = 0;
    }

    public void heal (int halfs){
        curr_half_hearts += halfs;
        if (curr_half_hearts > max_hearts*2) curr_half_hearts = max_hearts*2;
    }

    public weaponType getWeaponType() {
        return weaponT;
    }

    public void setWeaponT(weaponType weaponT) {
        this.weaponT = weaponT;
    }

    public bulletType getBulletType() {
        return bulletT;
    }

    public void setBulletType(bulletType bulletT) {
        this.bulletT = bulletT;
    }

    public int getMove_speed() {
        return move_speed;
    }

    public void increase_MoveSpeed(int a) {
        this.move_speed += a;
        if(this.move_speed > 200) move_speed=200;
        if(this.move_speed < 1) move_speed = 1;
    }

    public boolean hasLinealBullets() {
        return linealBullets;
    }

    public void setLinealBullets(boolean linealBullets) {
        this.linealBullets = linealBullets;
    }

    public boolean isBackward_shot() {
        return backward_shot;
    }

    public void setBackward_shot(boolean backward_shot) {
        this.backward_shot = backward_shot;
    }

    public int getBullet_number() {
        return bullet_number;
    }

    public void setBullet_number(int bullet_number) {
        this.bullet_number = bullet_number;
        if(this.bullet_number > 5) this.bullet_number = 5;
        if(this.bullet_number < 1) this.bullet_number = 1;
    }
    public void increaseBullet_number(int k) {
        this.bullet_number +=  k;
        if(this.bullet_number > 5) this.bullet_number = 5;
        if(this.bullet_number < 1) this.bullet_number = 1;
    }
}
