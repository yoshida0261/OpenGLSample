package com.starcompany.openglsample.Charactor;

import javax.microedition.khronos.opengles.GL10;

public abstract  class Charactor {
    private static final String TAG = Charactor.class.getSimpleName();
    public float angle;
    public float x, y;
    public float size;
    public float speed;
    public float turnAngle;

    public Charactor(float x, float y, float angle, float size, float speed, float turnAngle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.size = size;
        this.speed = speed;
        this.turnAngle = turnAngle;

    }
    abstract  public void move(float x, float y);
    abstract  public void draw(GL10 gl, int texture);




}
 