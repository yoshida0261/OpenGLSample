package com.starcompany.openglsample.Charactor;

import com.starcompany.openglsample.GraphicUtil;

public class Shot extends Charactor{
    private static final String TAG = Shot.class.getSimpleName();

    private float enemyType = -0.03f;
    private float droidType = 0.03f;
    private boolean isShotStart = false;
    private float shotType;



    public Shot(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x, y, angle, size, speed, turnAngle);
    }


    public boolean isconflicted(Charactor charactor) {
        return charactor.isPointInside(x,y);
    }

    public void enemyShot(float x, float y)
    {
        this.x = x;
        this.y = y+0.2f;
        this.isShotStart =true;
        this.shotType = enemyType;

    }

    public void droidShot(float x, float y)
    {
        this.x = x;
        this.y = y-0.2f;
        this.isShotStart = true;
        this.shotType = droidType;
    }


    @Override
    public void move() {

        if(isShotStart){
            this.y = this.y + this.shotType;
            if(y > 3.0f || y < -3.0f){
                isShotStart = false;
            }
            return;
        }
        x = 3.0f;
        y = 3.0f;
    }



    @Override
    public void draw() {


        GraphicUtil.drawTexture(gl, x, y, 0.1f, 0.1f, texture, 1.0f, 1.0f, 1.0f, 1.0f);

        //GraphicUtil.drawShot(gl,x,y);
    }


    public float getX(){
        return  this.x;
    }

    public float getY(){
        return  this.y;
    }

    public boolean isShotState()
    {
        return  this.isShotStart;
    }

    public void Hit(int bombTexture){
        this.isShotStart = false;

        GraphicUtil.drawTexture(gl, x, y, 0.3f, 0.3f, bombTexture, 1.0f, 1.0f, 1.0f, 1.0f);

        this.x = 3.0f;
        this.y = 3.0f;
        //弾けるエフェクト
        //敵、doroidはフェードアウトすること

    }

    public void Miss(){
        this.isShotStart = false;
    }
}
 