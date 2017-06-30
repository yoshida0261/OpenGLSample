package com.starcompany.openglsample.Charactor;

import com.starcompany.openglsample.GraphicUtil;

public class Block extends Charactor {
    private static final String TAG = Block.class.getSimpleName();

    private int hp=2;
    public Block(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x, y, angle, size, speed, turnAngle);
    }

    public void breake(int texture){
        hp--;
        if(hp == 1){
            this.texture = texture;
        }
        if(hp == 0){
            x = 3.0f;
            y = 3.0f;
        }
    }

    @Override
    public void move() {

    }

    @Override
    public void draw() {

        GraphicUtil.drawTexture(gl, x, y, 0.2f, 0.2f, texture, 1.0f, 1.0f, 1.0f, 1.0f);

    }
}
 