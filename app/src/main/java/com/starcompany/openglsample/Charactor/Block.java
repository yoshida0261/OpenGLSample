package com.starcompany.openglsample.Charactor;

import com.starcompany.openglsample.GraphicUtil;

import javax.microedition.khronos.opengles.GL10;

public class Block extends Charactor {
    private static final String TAG = Block.class.getSimpleName();

    public Block(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x, y, angle, size, speed, turnAngle);
    }
    @Override
    public void setGraphic(GL10 gl, int texture) {
        this.gl = gl;
        this.texture = texture;
    }

    @Override
    public void move() {

    }

    @Override
    public void draw() {

        GraphicUtil.drawTexture(gl, x, y, 0.2f, 0.2f, texture, 1.0f, 1.0f, 1.0f, 1.0f);

    }
}
 