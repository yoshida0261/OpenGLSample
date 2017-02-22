package com.starcompany.openglsample;

import android.content.Context;
import android.content.res.Resources;

import com.starcompany.openglsample.Charactor.Block;
import com.starcompany.openglsample.Charactor.Droidkun;
import com.starcompany.openglsample.Charactor.Enemy;
import com.starcompany.openglsample.Charactor.Shot;

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

    private int bgTexture;
    private int enemyTexture;
    private int droidTexture;
    private int blockTexture;
    private int ufoTexture;
    private int shotTexture;
    private int mNumberTexture;
    private int mGameOverTexture;//ゲームオーバー用テクスチャ
    private int mParticleTexture;//パーティクル用テクスチャ


    public DWRenderer(Droidkun droid, Enemy[] enemies, Block[] blocks){
        this.droid = droid;
        this.enemies = enemies;
        this.blocks = blocks;
    }

    public void setGraphicTexture(){

        for (int i = 0; i < TARGET_NUM; i++) {
            enemies[i].setGraphic(gl, this.enemyTexture);
        }
        for (int i = 0; i < BLOCK_NUM; i++) {
            blocks[i].setGraphic(gl, this.blockTexture);
        }
        droid.setGraphic(gl, this.droidTexture);
        droid.getShot().setGraphic(gl, this.shotTexture);

    }

    public void loadTextures(GL10 gl, Context context) {
        Resources res = context.getResources();
        this.enemyTexture = GraphicUtil.loadTexture(gl, res, R.drawable.fly);
        this.bgTexture = GraphicUtil.loadTexture(gl, res, R.drawable.circuit);
        this.droidTexture = GraphicUtil.loadTexture(gl, res, R.drawable.droid2);
        this.blockTexture = GraphicUtil.loadTexture(gl, res, R.drawable.block);
        this.shotTexture = GraphicUtil.loadTexture(gl, res, R.drawable.shot);

        /*
        this.mNumberTexture = GraphicUtil.loadTexture(gl, res, R.drawable.number_texture);
        if (mNumberTexture == 0) {
            Log.e(getClass().toString(), "load texture error! number_texture");
        }
        this.mGameOverTexture = GraphicUtil.loadTexture(gl, res, R.drawable.game_over);
        if (mGameOverTexture == 0) {
            Log.e(getClass().toString(), "load texture error! game_over");
        }
        this.mParticleTexture = GraphicUtil.loadTexture(gl, res, R.drawable.particle_blue);
        if (mParticleTexture == 0) {
            Log.e(getClass().toString(), "load texture error! particle_blue");
        }
        */
    }


    public void renderMain(){

        GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 2.0f, 3.0f, bgTexture, 1.0f, 1.0f, 1.0f, 1.0f);
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

    public void isPointInside(float x, float y)
    {
        Shot shot = droid.getShot();
        for (int i = 0; i < TARGET_NUM; i++) {

            if (shot.isShotState() == true && enemies[i].isPointInside(x, y)) {

                // enemies　フェードアウト
                enemies[i].x = 3.0f;
                enemies[i].y = 3.0f;

                shot.Hit();
            }
        }
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
 