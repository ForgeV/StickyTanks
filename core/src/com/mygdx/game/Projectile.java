package com.mygdx.game;

public class Projectile extends StickyTanks{
    public float x,y;
    public float antiEnemyDamage, antiWallDamage;
    public float width, height;
    public float speed;
   public int sideCounter = 0;
   public boolean hit, isShooted = false, whose;
   int livTime = 300;
    public Projectile(float x, float y, int antiWallDamage, float width, float height, float speed, int sideCounter, boolean hit,boolean whose, boolean isShooted) {
        this.x = x;
        this.y = y;
        this.antiWallDamage = antiWallDamage;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.sideCounter = sideCounter;
        this.hit =hit;
        this.isShooted = isShooted;
        this.whose = whose;
    }
    void reset(){
        x = SCR_WIDTH+10000;
        y = SCR_HEIGHT+10000;
        isShooted = false;
        hit = false;
        livTime = 120;
    }
    void hit(){
     //  x = SCR_WIDTH+1000;
     //  y = SCR_HEIGHT+1000;
        hit = true;
    }
    void moveLeftX(){
        x -= speed;
    }
    void moveRightX(){

        x += speed;
    }
    void moveUpY(){
        y += speed;
    }
    void moveDownY(){
        y -= speed;
    }
}
