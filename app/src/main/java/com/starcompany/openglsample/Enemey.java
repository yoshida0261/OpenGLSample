package com.starcompany.openglsample;

import javax.microedition.khronos.opengles.GL10;

public class Enemey {
    private static final String TAG = Enemey.class.getSimpleName();
    public float mAngle;//標的の角度
    public float mX, mY;//標的の位置
    public float mSize;//標的のサイズ
    public float mSpeed;//標的の移動速度
    public float mTurnAngle;//標的の旋回角度

    public Enemey (float x, float y, float angle, float size, float speed, float turnAngle) {
        this.mX = x;
        this.mY = y;
        this.mAngle = angle;
        this.mSize = size;
        this.mSpeed = speed;
        this.mTurnAngle = turnAngle;
    }

    //標的を移動させます
    public void move() {
        float theta = mAngle / 180.0f * (float)Math.PI;
        mX = mX + (float)Math.cos(theta) * mSpeed;
        mY = mY + (float)Math.sin(theta) * mSpeed;

        //ワープ処理
        if (mX >=  2.0f) mX -= 4.0f;
        if (mX <= -2.0f) mX += 4.0f;
        if (mY >=  2.5f) mY -= 5.0f;
        if (mY <= -2.5f) mY += 5.0f;
    }

    //ポイントが当たり判定の範囲内かを返します
    public boolean isPointInside(float x, float y) {
        // 標的とタッチされたポイントとの距離を計算します
        float dx = x - mX;
        float dy = y - mY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance <= mSize * 0.5f) {
            return true;
        }
        return false;
    }

    //標的を描画します
    public void draw(GL10 gl, int texture) {
        gl.glPushMatrix();
        {
            gl.glTranslatef(mX, mY, 0.0f);
            gl.glRotatef(mAngle, 0.0f, 0.0f, 1.0f);
            gl.glScalef(mSize, mSize, 1.0f);
            GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 1.0f, 1.0f, texture, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        gl.glPopMatrix();
    }

}
 