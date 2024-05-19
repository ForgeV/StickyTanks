package com.mygdx.game;

public class Enemy extends StickyTanks {
    public int x, y;
    public float targetX, targetY;
    public float realX, realY;
    public float stepX, stepY;
    public int steps = 15;
    public float width, height;
    public int sideCounter = 0;
    public long tics;
    public int enemyBasicAmmoCounter, enemyMachinegunAmmoCounter, enemyBigAmmoCounter;
    public boolean shootTrigger = false, isDestroyed = false;
    public int hitpoints, ID, reloading;
    public Enemy(int x, int y, float width, float height, int sideCounter, long tics, int enemyBasicAmmoCounter, int hitpoints, int enemyMachinegunAmmoCounter, int enemyBigAmmoCounter, int ID) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sideCounter = sideCounter;
        this.tics = tics;
        this.enemyBasicAmmoCounter = enemyBasicAmmoCounter;
        this.enemyBigAmmoCounter  = enemyBigAmmoCounter;
        this.enemyMachinegunAmmoCounter = enemyMachinegunAmmoCounter;
        this.hitpoints = hitpoints;
        this.ID = ID;
        realX = x*WIDTH;
        realY = y*HEIGHT;
        stepX = width/ steps;
        stepY = height/ steps;
        targetX = x;
        targetY = y;
    }
    void moveLeftX(){
       realX -= stepX;

    }
    void moveRightX(){
      realX += stepX;


    }
    void moveUpY() {
       realY += stepY;


    }
    void moveDownY(){
        realY -= stepY;


    }
}
