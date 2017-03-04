package com.starcompany.openglsample.Charactor;

import com.starcompany.openglsample.GraphicUtil;

public class Enemy extends Charactor {
    private static final String TAG = Enemy.class.getSimpleName();

    public Enemy(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x,y,angle,size,speed,turnAngle);
    }

    private boolean dead = false;
    public void died(){
        dead = true;
    }
    public boolean isDie(){
        return dead;
    }


    private int direction = 1;
    @Override
    public void move() {
        //float theta = angle / 180.0f * (float)Math.PI;
        this.x = this.x + 0.005f * direction;

        //this.y = this.y + (float)Math.sin(theta) * speed;
        if(dead){
            return;
        }

        //ワープ処理
        if (this.x > 0.9f) {
            direction = -1;
            this.y -=  0.21f;
        }
        if (this.x < -0.9f){
            direction = 1;
            this.y -=  0.21f;
        }
      //  if (this.x <= -2.0f) this.x += 4.0f;
      //  if (this.y >=  2.5f) this.y -= 5.0f;
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
 