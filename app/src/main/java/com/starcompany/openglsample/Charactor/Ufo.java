package com.starcompany.openglsample.Charactor;

import com.starcompany.openglsample.GraphicUtil;

public class Ufo extends Charactor {
    private static final String TAG = Ufo.class.getSimpleName();

    public Ufo(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x, y, angle, size, speed, turnAngle);
    }

    private boolean dead = false;
    public void died(){
        x = 3.0f;
        y = 3.0f;
       dead = true;
    }//死んだ
    public boolean isDie(){
        return dead;
    }


    /**
     * 右端から左端へ移動する
     */
    @Override
    public void move() {

        if(dead == false){
            this.x = this.x - 0.014f;

        }
        if (this.x <= -2.0f) {
            died();
        }
    }


    @Override
    public void draw() {

        GraphicUtil.drawTexture(gl, x, y, 0.2f, 0.2f, texture, 1.0f, 1.0f, 1.0f, 1.0f);

    }
}
 