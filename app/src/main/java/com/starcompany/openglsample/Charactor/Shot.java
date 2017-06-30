package com.starcompany.openglsample.Charactor;

import com.starcompany.openglsample.GraphicUtil;

public class Shot extends Charactor{
    private static final String TAG = Shot.class.getSimpleName();

    private float enemyType = -0.015f;
    private float droidType = 0.03f;
    private boolean isShotDrawStart = false;
    private float shotType;
    private int bombTexture;
    private boolean isFinal = false;


    public Shot(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x, y, angle, size, speed, turnAngle);
    }

    public void enemyShot(float x, float y)
    {
        this.x = x;
        this.y = y;
        this.isShotDrawStart =true;
        this.shotType = enemyType;

    }

    public void droidShot(float x, float y)
    {
        this.x = x;
        this.y = y-0.2f; // 自分の位置手前から撃つ
        this.isShotDrawStart = true;
        this.shotType = droidType;
    }


    @Override
    public void move() {

        if(isShotDrawStart){
            this.y = this.y + this.shotType;
            if(y > 1.5f || y < -1.5f){
                isShotDrawStart = false;
            }
            return;
        }
        x = 3.0f;
        y = -3.0f;
    }



    @Override
    public void draw() {
        GraphicUtil.drawTexture(gl, x, y, 0.1f, 0.1f, texture, 1.0f, 1.0f, 1.0f, 1.0f);

    }


    public float getX(){
        return  this.x;
    }

    public float getY(){
        return  this.y;
    }

    public boolean isShotState()
    {
        return  this.isShotDrawStart;
    }

    public void setBombTexture(int bombTexture){
        this.bombTexture = bombTexture;
    }

    public void Hit(){
        this.isShotDrawStart = false;

        GraphicUtil.drawTexture(gl, x, y, 0.3f, 0.3f, bombTexture, 1.0f, 1.0f, 1.0f, 1.0f);

        this.x = 3.0f;
        this.y = -3.0f;

    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public boolean isFinal(){
        return this.isFinal;
    }

}
 