package com.starcompany.openglsample.Charactor;

import com.starcompany.openglsample.GraphicUtil;


public class Droidkun  extends Charactor {
    private static final String TAG = Droidkun.class.getSimpleName();

    private Shot shot = null;

    public Droidkun(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x,y,angle,size,speed,turnAngle);

        shot = new Shot(x,y,angle, size, speed, turnAngle);

    }


    @Override
    public void move() {

    }

    @Override
    public void draw() {

        GraphicUtil.drawTexture(gl, 0.0f, -1.0f, 0.2f, 0.2f, texture, 1.0f, 1.0f, 1.0f, 1.0f);


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

    public void Attack(float x, float y){
        shot.droidShot(x,y);
    }

    public Shot getShot()
    {
        return  shot;
    }


}
 