package com.starcompany.openglsample.Charactor;

import com.starcompany.openglsample.GraphicUtil;

public class Enemy extends Charactor {
    private static final String TAG = Enemy.class.getSimpleName();
    private Shot shot = null;

    public Enemy(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x,y,angle,size,speed,turnAngle);
        shot = new Shot(x,y,angle, size, speed, turnAngle);

    }


    private boolean dead = false;
    public void died(){
        front = false;
        dead = true;
    }//死んだ
    public boolean isDie(){
        return dead;
    }

    private boolean front = false; //最前列にいると弾が出せる

    public boolean isFront(){
        return  front;
    }

    public void setFront(){
        front = true;
    }


    private int direction = 1;
    private int wait = 0;
    private boolean linefeed = false;

    public boolean isLinefeed()
    {
        return  linefeed;
    }

    public void LineFeed(){

        this.y -=  0.21f;
        this.linefeed = false;
        this.direction *= -1;
    }

    @Override
    public void move() {
        float x = this.x;
        float speed = 0.015f;

        if(dead){
            return;
        }

        //ワープ処理
        x = x + speed * direction;
        if (x > 0.9f || x < -0.9f) {
            linefeed = true;
        }

        //スピードダウン
        wait++;
        if(wait < 30){
            return;
        }
        wait = 0;

        this.x = x;

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

    public void attack(){
        if(front) {
            shot.enemyShot(x,y);
        }
    }


}
 