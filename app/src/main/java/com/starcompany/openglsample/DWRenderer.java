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
import static com.starcompany.openglsample.R.drawable.shot;

public class DWRenderer {
    private static final String TAG = DWRenderer.class.getSimpleName();

    private Droidkun droid;
    private int score;
    private Enemy[] enemies = new Enemy[TARGET_NUM];
    private Block[] blocks = new Block[BLOCK_NUM];

    public Droidkun getDroidInstance(){
        return  droid;
    }

    public Enemy[] getEnemyInstance(){
        return enemies;
    }

    public Block[] getBlocksInstance(){
        return blocks;
    }


    private int bgTexture;
    private int enemyTexture;
    private int droidTexture;
    private int blockTexture;
    private int ufoTexture;
    private int shotTexture;
    private int numberTexture;
    private int gameOverTexture;//ゲームオーバー用テクスチャ
    private int mParticleTexture;//パーティクル用テクスチャ
    private int bombTexture;



    public void initializeCharacter()
    {
        // drawでy座標を固定描画しているのでここでの変更は無意味
        droid = new Droidkun(0, -0.9f, 0f, 0.5f, 0.02f, 0);

        float y = 0.7f;
        int count = 0;

        for (int i = 0; i < TARGET_NUM; i++) {
            float posX = -0.8f +(0.33f * count);
            enemies[i] = new Enemy(posX, y, 0.2f, 0.2f, 0.02f, 0);
            count += 1;
            if(i == 5 || i == 11){
                count = 0;
                y += 0.21;
            }
        }

        blocks[0] = new Block(-0.7f, -0.8f, 0, 0.3f, 0.02f, 0);
        blocks[1] = new Block(-0.3f, -0.8f, 0, 0.3f, 0.02f, 0);
        blocks[2] = new Block( 0.3f, -0.8f, 0, 0.3f, 0.02f, 0);
        blocks[3] = new Block( 0.7f, -0.8f, 0, 0.3f, 0.02f, 0);
        score = 0;

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
        this.enemyTexture = GraphicUtil.loadTexture(gl, res, R.drawable.octocat);
        this.bgTexture = GraphicUtil.loadTexture(gl, res, R.drawable.circuit);
        this.droidTexture = GraphicUtil.loadTexture(gl, res, R.drawable.droid2);
        this.blockTexture = GraphicUtil.loadTexture(gl, res, R.drawable.block);
        this.shotTexture = GraphicUtil.loadTexture(gl, res, shot);
        this.numberTexture = GraphicUtil.loadTexture(gl, res, R.drawable.number);
        this.bombTexture = GraphicUtil.loadTexture(gl, res, R.drawable.bomb);
        this.gameOverTexture = GraphicUtil.loadTexture(gl, res, R.drawable.game_over);

        /*
        this.mParticleTexture = GraphicUtil.loadTexture(gl, res, R.drawable.particle_blue);
        if (mParticleTexture == 0) {
            Log.e(getClass().toString(), "load texture error! particle_blue");
        }
        */
    }


    /**
     * Game Clear画像表示後、
     * エンディング
     */
    private void gameClear(){

        GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 2.0f, 0.5f, gameOverTexture, 1.0f, 1.0f, 1.0f, 1.0f);

    }

    public void renderMain(){

       // GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 2.0f, 3.0f, bgTexture, 1.0f, 1.0f, 1.0f, 1.0f);
        GraphicUtil.drawNumbers(gl, -0.5f, 1.25f, 0.125f, 0.125f, numberTexture, score, 4);

        moveEnemy();
        int die = 0;
        for (int i = 0; i < TARGET_NUM; i++) {
            enemies[i].draw();
            if(enemies[i].isDie()==true){
                die++;
            }
            if(TARGET_NUM == die){
                gameClear();

            }
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

    /**
     * 当たり判定
     *
     *
     *
     * @param x
     * @param y
     */
    public void isShotPointInside(float x, float y)
    {
        Shot shot = droid.getShot();

        for(int i=0; i< BLOCK_NUM; i++){
            if (shot.isShotState() == true && blocks[i].isPointInside(x, y)) {

                // blocks　３回位で消えるようにする（予定）
                blocks[i].x = 3.0f;
                blocks[i].y = 3.0f;

                shot.Hit(this.bombTexture);
                //壁を壊したからそこまで
                return;
            }
        }
        for (int i = 0; i < TARGET_NUM; i++) {

            if (shot.isShotState() == true && enemies[i].isPointInside(x, y)) {

                // enemies　フェードアウト
                enemies[i].x = 3.0f;
                enemies[i].y = 3.0f;
                enemies[i].died();
                score += 10;

                shot.Hit(this.bombTexture);
            }
        }
    }

    public void isDroidPointInside(){
        float x = droid.getX();

        for (int i = 0; i < TARGET_NUM; i++) {
            if (enemies[i].isPointInside(x, -1.2f)) {
                gameClear();
                return;//多分ここにはこない


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

        // wait
        for (int i = 0; i < TARGET_NUM; i++) {
            enemies[i].move();
        }
    }

}
 