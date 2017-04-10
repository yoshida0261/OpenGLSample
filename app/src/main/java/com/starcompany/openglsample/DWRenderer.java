package com.starcompany.openglsample;

import android.content.Context;
import android.content.res.Resources;

import com.starcompany.openglsample.Charactor.Block;
import com.starcompany.openglsample.Charactor.Droidkun;
import com.starcompany.openglsample.Charactor.Enemy;
import com.starcompany.openglsample.Charactor.EnemyManager;
import com.starcompany.openglsample.Charactor.Shot;

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
    private EnemyManager enemmyManager = new EnemyManager();
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
    private int enemy2Texture;
    private int enemy3Texture;


    private int droidTexture;
    private int blockTexture;
    private int blockBreakTexture;

    private int ufoTexture;
    private int ufoTexture2;
    private int ufoBombTexture;
    private int shotTexture;
    private int numberTexture;
    private int gameOverTexture;//ゲームオーバー用テクスチャ
    private int bombTexture;



    public void initializeCharacter()
    {
        // drawでy座標を固定描画しているのでここでの変更は無意味
        droid = new Droidkun(0, -0.9f, 0f, 0.5f, 0.02f, 0);
        enemmyManager.initializeCharacter();;
        blocks[0] = new Block(-0.7f, -0.8f, 0, 0.3f, 0.02f, 0);
        blocks[1] = new Block(-0.3f, -0.8f, 0, 0.3f, 0.02f, 0);
        blocks[2] = new Block( 0.3f, -0.8f, 0, 0.3f, 0.02f, 0);
        blocks[3] = new Block( 0.7f, -0.8f, 0, 0.3f, 0.02f, 0);
        score = 0;

    }

    public void setGraphicTexture(){

        enemmyManager.setGraphicTexture(this.enemyTexture, this.enemy2Texture, this.enemy3Texture, bombTexture,  this.shotTexture);
        enemmyManager.setUfoGraphicTexture(this.ufoTexture, this.ufoTexture2, this.ufoBombTexture);
        for (int i = 0; i < BLOCK_NUM; i++) {
            blocks[i].setGraphic(gl, this.blockTexture);
        }
        droid.setGraphic(gl, this.droidTexture);
        droid.getShot().setGraphic(gl, this.shotTexture);
        droid.getShot().setBombTexture(this.bombTexture);

    }

    public void loadTextures(GL10 gl, Context context) {
        Resources res = context.getResources();
        this.enemyTexture = GraphicUtil.loadTexture(gl, res, R.drawable.enemy_line1);
        this.enemy2Texture = GraphicUtil.loadTexture(gl, res, R.drawable.enemy_line2);
        this.enemy3Texture = GraphicUtil.loadTexture(gl, res, R.drawable.enemy_line3);
        this.ufoTexture = GraphicUtil.loadTexture(gl, res, R.drawable.enemy_apple);
        this.ufoTexture2 = GraphicUtil.loadTexture(gl, res, R.drawable.enemy_windows);
        this.ufoBombTexture = GraphicUtil.loadTexture(gl, res, R.drawable.ufo_bomb);

        this.bgTexture = GraphicUtil.loadTexture(gl, res, R.drawable.circuit);
        this.droidTexture = GraphicUtil.loadTexture(gl, res, R.drawable.mydroid);
        this.blockTexture = GraphicUtil.loadTexture(gl, res, R.drawable.block);
        this.blockBreakTexture = GraphicUtil.loadTexture(gl, res, R.drawable.block_break);

        this.shotTexture = GraphicUtil.loadTexture(gl, res, shot);
        this.numberTexture = GraphicUtil.loadTexture(gl, res, R.drawable.number);
        this.bombTexture = GraphicUtil.loadTexture(gl, res, R.drawable.bomb);

        this.gameOverTexture = GraphicUtil.loadTexture(gl, res, R.drawable.game_over);
    }

    /**
     * 敵とぶつかるか、敵のたまとぶつかるとゲームオーバー
     */
    private void gameOver(){
        enemmyManager.gameOver();
        GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 2.0f, 0.5f, gameOverTexture, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    /**
     * Game Clear画像表示後、
     * エンディング
     */
    private void gameClear(){

        GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 2.0f, 0.5f, gameOverTexture, 1.0f, 1.0f, 1.0f, 1.0f);

        // TODO エンディング処理
    }

    public void renderMain(){

        GraphicUtil.drawNumbers(gl, -0.5f, 1.25f, 0.125f, 0.125f, numberTexture, score, 4);

        enemmyManager.move();

        if(enemmyManager.isGameClear() == true){
            gameClear();
        }

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
        isDroidShotPointInside(x,y);
        isEnemyShotPointInside(x,y);
        isWorldPointInside(x,y);

        if(enemmyManager.isEnemyShotPointInside(droid.getX(), droid.getY()) == true){
            gameOver();
        }
    }

    /**
     * droidくんが撃った玉の当たり判定
     * @param x
     * @param y
     */
    private void isDroidShotPointInside(float x, float y)
    {
        Shot shot = droid.getShot();

        for(int i=0; i< BLOCK_NUM; i++){
            if (shot.isShotState() == true && blocks[i].isPointInside(x, y) == true) {

                //壁を壊すまで
                blocks[i].breake(this.blockBreakTexture);
                shot.Hit();
                shot.setFinal(true);
                return;
            }
        }
        if(enemmyManager.isShotPointInside(shot,x,y) == true){
            score+=10;
            shot.setFinal(true);
        }

    }

    private void isWorldPointInside(float x, float y) {
        Shot shot = droid.getShot();
        if (shot.getY() > 0 && shot.getY() < 1.0) {
            shot.setFinal(true);
        }
    }

    /**
     * テキの弾の当たり判定
     * @param x
     * @param y
     */
    private void isEnemyShotPointInside(float x, float y){

        for(int i=0; i< BLOCK_NUM; i++){
            if (enemmyManager.isEnemyShotPointInside(blocks[i].x, blocks[i].y)) {

                blocks[i].breake(this.blockBreakTexture);
                return;
            }
        }
    }


    public void isDroidPointInside(){
        float x = droid.getX();

        if(enemmyManager.isDroidPointInsite(x) == true){
            gameOver();
        }
    }


    private void timeCount(){

    }

    private void maxScore(){
        //前回のスコアを取得
        //最高スコアなら更新

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


}
 