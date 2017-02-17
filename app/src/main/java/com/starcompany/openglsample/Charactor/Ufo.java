package com.starcompany.openglsample.Charactor;

import com.starcompany.openglsample.GraphicUtil;

import javax.microedition.khronos.opengles.GL10;

public class Ufo extends Charactor {
    private static final String TAG = Ufo.class.getSimpleName();

    public Ufo(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x, y, angle, size, speed, turnAngle);
    }

    /**
     * 右端から左端へ移動する
     */
    @Override
    public void move() {
        this.x = this.x + 0.03f;
        if (this.x >=  2.0f) this.x -= 4.0f;
    }

    @Override
    public void setGraphic(GL10 gl, int texture) {
        this.gl = gl;
        this.texture = texture;
    }

    @Override
    public void draw() {

        GraphicUtil.drawTexture(gl, 0.0f, 1.0f, 0.2f, 0.2f, texture, 1.0f, 1.0f, 1.0f, 1.0f);

    }
}
 