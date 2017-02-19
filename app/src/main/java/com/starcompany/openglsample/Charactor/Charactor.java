package com.starcompany.openglsample.Charactor;

import javax.microedition.khronos.opengles.GL10;

public abstract  class Charactor {
    private static final String TAG = Charactor.class.getSimpleName();
    public float angle;
    public float x, y;
    public float size;
    public float speed;
    public float turnAngle;
    public GL10  gl;
    public int   texture;

    public Charactor(float x, float y, float angle, float size, float speed, float turnAngle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.size = size;
        this.speed = speed;
        this.turnAngle = turnAngle;

    }

    public void setGraphic(GL10 gl, int texture){
        this.gl = gl;
        this.texture = texture;
    }

    /**
     * 当たり判定
     * x, y が自分の領域内かを判定する
     * @param x
     * @param y
     * @return
     */
    public boolean isPointInside(float x, float y)
    {
        // 標的とタッチされたポイントとの距離を計算します
        float dx = x - this.x;
        float dy = y - this.y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance <= size * 0.5f) {
            return true;
        }
        return false;
    }

    abstract  public void move();
    abstract  public void draw();




}
 