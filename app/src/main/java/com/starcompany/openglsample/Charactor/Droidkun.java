package com.starcompany.openglsample.Charactor;

import android.util.Log;

import com.starcompany.openglsample.GraphicUtil;


public class Droidkun  extends Charactor {
    private static final String TAG = Droidkun.class.getSimpleName();

    private float moveX;
    private Shot shot = null;

    public float getX(){
        return  x;
    }
    public float getY(){
        return  y -0.2f;
    }

    public Droidkun(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x,y,angle,size,speed,turnAngle);

        shot = new Shot(x,y,angle, size, speed, turnAngle);

    }


    /**
     * 自分の位置からx分移動する
     *
     */
    @Override
    public void move() {

        if(moveX < 0){
            this.x = this.x - 0.01f;
            if(moveX > this.x){
                moveX = 0.0f;
            }

        }else if(moveX > 0){
            this.x = this.x + 0.01f;
            if(moveX < this.x){
                moveX = 0.0f;
            }

        }
    }

    public void move(float x){
        this.moveX = x;
        Log.i(TAG, String.valueOf(x));
    }

    @Override
    public void draw() {

        GraphicUtil.drawTexture(gl, x, -1.2f, 0.2f, 0.2f, texture, 1.0f, 1.0f, 1.0f, 1.0f);


        /*
        gl.glPushMatrix();
        {
            GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 2.0f, 3.0f, texture, 1.0f, 1.0f, 1.0f, 1.0f);
            gl.glTranslatef(x, y, 0.0f);
            gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
            gl.glScalef(1.0f, 1.0f, 1.0f);

        }
        gl.glPopMatrix();
*/
    }

    /**
     * 撃った弾があたったかの判定
     * @param x
     * @param y
     * @return
     */
    @Override
    public boolean isPointInside(float x, float y){
        return shot.isPointInside(x,y);
    }

    public void attack(){
        shot.droidShot(x,y);
    }

    public Shot getShot()
    {
        return  shot;
    }


}
 