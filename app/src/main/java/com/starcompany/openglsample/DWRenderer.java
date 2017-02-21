package com.starcompany.openglsample;

import com.starcompany.openglsample.Charactor.Block;
import com.starcompany.openglsample.Charactor.Droidkun;
import com.starcompany.openglsample.Charactor.Enemy;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import static com.starcompany.openglsample.DWGlobal.gl;
import static com.starcompany.openglsample.DroidWars.BLOCK_NUM;
import static com.starcompany.openglsample.DroidWars.TARGET_NUM;

public class DWRenderer {
    private static final String TAG = DWRenderer.class.getSimpleName();

    private Droidkun droid;
    private Enemy[] enemies;
    private Block[] blocks;

    public DWRenderer(Droidkun droid, Enemy[] enemies, Block[] blocks){
        this.droid = droid;
        this.enemies = enemies;
        this.blocks = blocks;
    }

    public void renderMain(){

        //moveEnemy();
        for (int i = 0; i < TARGET_NUM; i++) {
            enemies[i].draw();
        }
        // todo enemis.shot

        for (int i = 0; i < BLOCK_NUM; i++) {
            blocks[i].draw();
        }

        droid.move();
        droid.draw();
        droid.getShot().move();
        droid.getShot().draw();
        gl.glDisable(GL10.GL_BLEND);
    }

    private void timeCount(){

    }

    private void maxScore(){
        //前回のスコアを取得
        //最高スコアなら更新
        //dbに格納するのは気が向いたら
    }

    private void timeOver(){
        /*
        TODO 後の課題
        int passedTime = (int) (System.currentTimeMillis() - startTime) / 1000;
        int remainTime = GAME_INTERVAL - passedTime;// 　残り時間を計算する
        if (remainTime <= 0) {
            remainTime = 0;// 残り時間がマイナスにならないようにする
            if (!gameOverFlag) {
                gameOverFlag = true;// ゲームオーバー状態にする
                // Global.mainActivity.showRetryButton()をUIスレッド上で実行する
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        DWGlobal.mainActivity.showRetryButton();
                    }
                });
            }
        }*/



    }

    /**
     * 左端から右端へ
     * 右端へ行った後は１キャラ分ススメて左端に戻る
     * 最後Droidkunとぶつかったら終わり
     */
    private void moveEnemy()
    {
        Random rand = DWGlobal.rand; // randamで弾だし
        Enemy[] enemies = this.enemies;

        for (int i = 0; i < TARGET_NUM; i++) {
            enemies[i].move();
        }
    }

}
 