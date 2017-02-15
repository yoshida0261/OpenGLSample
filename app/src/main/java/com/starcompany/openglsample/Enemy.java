package com.starcompany.openglsample;

import javax.microedition.khronos.opengles.GL10;

public class Enemy {
    private static final String TAG = Enemy.class.getSimpleName();
    public float angle;
    public float x, y;
    public float size;
    public float speed;
    public float turnAngle;

    public Enemy(float x, float y, float angle, float size, float speed, float turnAngle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.size = size;
        this.speed = speed;
        this.turnAngle = turnAngle;
    }

    //標的を移動させます
    public void move() {
        float theta = angle / 180.0f * (float)Math.PI;
        x = x + (float)Math.cos(theta) * speed;
        y = y + (float)Math.sin(theta) * speed;

        //ワープ処理
        if (x >=  2.0f) x -= 4.0f;
        if (x <= -2.0f) x += 4.0f;
        if (y >=  2.5f) y -= 5.0f;
        if (y <= -2.5f) y += 5.0f;
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

    //標的を描画します
    public void draw(GL10 gl, int texture) {
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
 