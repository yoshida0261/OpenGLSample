package com.starcompany.openglsample.Charactor;

import javax.microedition.khronos.opengles.GL10;

public abstract  class Charactor {
    private static final String TAG = Charactor.class.getSimpleName();
    public Charactor(float x, float y, float angle, float size, float speed, float turnAngle){}
    abstract  public void move(float x, float y);
    abstract  public void draw(GL10 gl, int texture);




}
 