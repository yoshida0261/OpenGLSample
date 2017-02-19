package com.starcompany.openglsample.Charactor;

import com.starcompany.openglsample.GraphicUtil;

public class Shot extends Charactor{
    private static final String TAG = Shot.class.getSimpleName();

    public Shot(float x, float y, float angle, float size, float speed, float turnAngle) {
        super(x, y, angle, size, speed, turnAngle);
    }


    public boolean isconflicted(Charactor charactor) {
        return charactor.isPointInside(x,y);
    }

    public void enemyShot()
    {
        this.y = this.y - 0.03f;

        //if (this.y <= -2.5f) this.y += 5.0f;

    }

    public void droidShot()
    {
        this.y = this.y + 0.03f;

      //  if (this.y >=  2.5f) this.y -= 5.0f;
    }

    @Override
    public void move() {

    }

    @Override
    public void draw() {

        GraphicUtil.drawSquare(gl);
    }

    @Override
    public boolean isPointInside(float x, float y) {
        return false;
    }
}
 