package com.starcompany.openglsample.Charactor;

import com.starcompany.openglsample.GraphicUtil;


public class Droidkun  extends Charactor {
    private static final String TAG = Droidkun.class.getSimpleName();


    public Droidkun(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x,y,angle,size,speed,turnAngle);
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

}
 