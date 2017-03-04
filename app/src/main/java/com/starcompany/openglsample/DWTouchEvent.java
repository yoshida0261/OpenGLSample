package com.starcompany.openglsample;

import com.starcompany.openglsample.Charactor.Droidkun;
import com.starcompany.openglsample.Charactor.Enemy;

public class DWTouchEvent {
    private static final String TAG = DWTouchEvent.class.getSimpleName();


    private Droidkun droid;
    private Enemy[] enemies;

    public DWTouchEvent(Droidkun droid, Enemy[] enemies){
        this.droid = droid;
        this.enemies = enemies;
    }

    public void onTouch(float x, float y) {
        droid.attack();
        droid.move(x);
    }

}
 