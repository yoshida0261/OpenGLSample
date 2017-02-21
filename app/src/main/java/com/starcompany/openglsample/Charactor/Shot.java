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
        this.y = y;
        this.isShotStart =true;
        this.shotType = enemyType;

    }

    public void droidShot(float x, float y)
    {
        this.x = x;
        this.y = y;
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
        }
    }



    @Override
    public void draw() {


        GraphicUtil.drawTexture(gl, x, y, 0.1f, 0.1f, texture, 1.0f, 1.0f, 1.0f, 1.0f);

        //GraphicUtil.drawShot(gl,x,y);
    }

    @Override
    public boolean isPointInside(float x, float y) {
        this.x = x;
        this.y = y;
        return false;
    }

    public float getX(){
        return  this.x;
    }

    public float getY(){
        return  this.y;
    }


    public void Hit(){
        this.isShotStart = false;
        //弾けるエフェクト
        //敵、doroidはフェードアウトすること

    }

    public void Miss(){
        this.isShotStart = false;
    }
}
 