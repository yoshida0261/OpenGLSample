package com.starcompany.openglsample.Charactor;

import com.starcompany.openglsample.GraphicUtil;

public class Enemy extends Charactor {
    private static final String TAG = Enemy.class.getSimpleName();

    public Enemy(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x,y,angle,size,speed,turnAngle);
    }
    private int line;

    /**
     * 自分の位置を指定
     * @param line
     */
    public void setLine(int line){
        this.line = line;
    }


    private boolean dead = false;
    public void died(){
        dead = true;
    }
    public boolean isDie(){
        return dead;
    }


    private int direction = 1;
    private int wait = 0;


    @Override
    public void move() {
        //float theta = angle / 180.0f * (float)Math.PI;
        float x = this.x;

        //this.x = this.x + 0.005f * direction;

        //this.y = this.y + (float)Math.sin(theta) * speed;
        if(dead){
            return;
        }

        //ワープ処理
        x = x + 0.015f * direction;
        if (x > 0.9f) {
            direction = -1;
            this.y -=  0.21f;
        }
        if (x < -0.9f){
            direction = 1;
            this.y -=  0.21f;
        }
        wait++;
        if(wait < 30){
            return;
        }
        wait = 0;


        this.x = x;


        //改行
        if (this.y <= -2.5f) this.y += 5.0f;
    }


    //標的を描画します
    @Override
    public void draw() {
        gl.glPushMatrix();
        {
            gl.glTranslatef(x, y, 0.0f);
            gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
            gl.glScalef(size, size, 1.0f);
            GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 1.0f, 1.0f, texture, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        gl.glPopMatrix();
    }


}
 