package com.starcompany.openglsample.Charactor;

import com.starcompany.openglsample.GraphicUtil;

import javax.microedition.khronos.opengles.GL10;

public class Enemy extends Charactor {
    private static final String TAG = Enemy.class.getSimpleName();

    public Enemy(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x,y,angle,size,speed,turnAngle);
    }

    @Override
    public void move() {
        //float theta = angle / 180.0f * (float)Math.PI;
        this.x = this.x + 0.03f;
        //this.y = this.y + (float)Math.sin(theta) * speed;

        //ワープ処理
        if (this.x >=  2.0f) this.x -= 4.0f;
        if (this.x <= -2.0f) this.x += 4.0f;
        if (this.y >=  2.5f) this.y -= 5.0f;
        if (this.y <= -2.5f) this.y += 5.0f;
    }

    //ポイントが当たり判定の範囲内かを返します
    public boolean isPointInside(float x, float y) {
        // 標的とタッチされたポイントとの距離を計算します
        float dx = x - x;
        float dy = y - this.y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance <= size * 0.5f) {
            return true;
        }
        return false;
    }
    @Override
    public void setGraphic(GL10 gl, int texture) {
        this.gl = gl;
        this.texture = texture;
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
 