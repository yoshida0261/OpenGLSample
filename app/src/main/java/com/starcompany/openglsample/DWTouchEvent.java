package com.starcompany.openglsample;

import com.starcompany.openglsample.Charactor.Droidkun;
import com.starcompany.openglsample.Charactor.Enemy;
import com.starcompany.openglsample.Charactor.Shot;

import static com.starcompany.openglsample.DroidWars.TARGET_NUM;

public class DWTouchEvent {
    private static final String TAG = DWTouchEvent.class.getSimpleName();


    private Droidkun droid;
    private Enemy[] enemies;

    public DWTouchEvent(Droidkun droid, Enemy[] enemies){
        this.droid = droid;
        this.enemies = enemies;
    }

    public void isPointInside(float x, float y)
    {
        Shot shot = droid.getShot();
        for (int i = 0; i < TARGET_NUM; i++) {

            if (shot.isShotState() == true && enemies[i].isPointInside(x, y)) {
                enemies[i].x = 3.0f;
                enemies[i].y = 3.0f;
                shot.Hit();
            }

        }

    }

    public void onTouch(float x, float y) {

        droid.move(x);

        Shot shot = droid.getShot();
        shot.droidShot(x, -1.0f); //droidと同じ高さ

        // すべての標的との当たり判定をします
       /* for (int i = 0; i < TARGET_NUM; i++) {

            if (enemies[i].isPointInside(shot.getX(), shot.getY())) {

                enemies[i].x = 3.0f;
                enemies[i].y = 3.0f;
            }


        }*/
        // enemyからのたまがdroidにあたっているかの判定も必要

/*            //パーティクルを放出します
            for (int j = 0; j < 40; j++) {
                float moveX = (rand.nextFloat() - 0.5f) * 0.05f;
                float moveY = (rand.nextFloat() - 0.5f) * 0.05f;
                particleSystem.add(enemies[i].x, enemies[i].y, 0.2f, moveX, moveY);
            }
            // 標的をランダムな位置に移動します
            float dist = 2.0f;// 画面中央から2.0fはなれた円周上の点
            float theta = (float) DWGlobal.rand.nextInt(360) / 180.0f * (float) Math.PI;// 適当な位置
            enemies[i].x = (float) Math.cos(theta) * dist;
            enemies[i].y = (float) Math.sin(theta) * dist;
            score += 100;// 100点加算します
            Log.i(getClass().toString(), "score = " + score);
 */

    }

}
 