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
    abstract  public void move();
    abstract  public void draw();




}
 