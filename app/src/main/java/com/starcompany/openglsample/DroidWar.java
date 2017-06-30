package com.starcompany.openglsample;

import android.content.Context;
import android.content.res.Resources;

import com.starcompany.openglsample.Charactor.Block;
import com.starcompany.openglsample.Charactor.Droidkun;
import com.starcompany.openglsample.Charactor.EnemyManager;
import com.starcompany.openglsample.Charactor.Shot;

import javax.microedition.khronos.opengles.GL10;

import static com.starcompany.openglsample.DWGlobal.gl;
import static com.starcompany.openglsample.DWRenderer.BLOCK_NUM;

public class DroidWar {
    private static final String TAG = DroidWar.class.getSimpleName();

    private Droidkun droid;
    private int score;
    private EnemyManager enemyManager = new EnemyManager();
    private Block[] blocks = new Block[BLOCK_NUM];
    private int blockBreakTexture;
    private int numberTexture;
    private int gameOverTexture;//ゲームオーバー用テクスチャ

    public void initializeCharacter()
    {
        // drawでy座標を固定描画しているのでここでの変更は無意味
        droid = new Droidkun(0, -0.9f, 0f, 0.5f, 0.02f, 0);
        enemyManager.initializeCharacter();;
        blocks[0] = new Block(-0.7f, -0.8f, 0, 0.3f, 0.02f, 0);
        blocks[1] = new Block(-0.3f, -0.8f, 0, 0.3f, 0.02f, 0);
        blocks[2] = new Block( 0.3f, -0.8f, 0, 0.3f, 0.02f, 0);
        blocks[3] = new Block( 0.7f, -0.8f, 0, 0.3f, 0.02f, 0);
        score = 0;

    }

    public void setGraphicTexture(Resources res){

        enemyManager.setGraphicTexture(res, R.drawable.enemy_line1, R.drawable.enemy_line2, R.drawable.enemy_line3, R.drawable.bomb,  R.drawable.shot);
        enemyManager.setUfoGraphicTexture(res, R.drawable.enemy_apple, R.drawable.enemy_windows, R.drawable.ufo_bomb);
        for (int i = 0; i < BLOCK_NUM; i++) {
            blocks[i].setGraphic(gl, res, R.drawable.block);
        }
        droid.setGraphic(gl, res, R.drawable.mydroid);
        droid.getShot().setGraphic(gl, res, R.drawable.shot);
        droid.getShot().setBombTexture(GraphicUtil.loadTexture(gl, res, R.drawable.bomb));

    }

    public void loadTextures(GL10 gl, Context context) {
        Resources res = context.getResources();
        this.blockBreakTexture = GraphicUtil.loadTexture(gl, res, R.drawable.block_break);
        this.numberTexture = GraphicUtil.loadTexture(gl, res, R.drawable.number);
        this.gameOverTexture = GraphicUtil.loadTexture(gl, res, R.drawable.game_over);
    }

    /**
     * 敵とぶつかるか、敵のたまとぶつかるとゲームオーバー
     */
    private void gameOver(){
        enemyManager.gameOver();
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

        enemyManager.move();

        if(enemyManager.isGameClear() == true){
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
     */
    public void isShotPointInside()
    {
        isDroidShotPointInside();
        isEnemyShotPointInside();
        if(enemyManager.isEnemyShotPointInside(droid.getX(), droid.getY()) == true){
            gameOver();
        }
    }

    /**
     * droidくんが撃った玉の当たり判定
     */
    private void isDroidShotPointInside()
    {
        Shot shot = droid.getShot();
        float x = shot.getX();
        float y = shot.getY();

        for(int i=0; i< BLOCK_NUM; i++){
            if (shot.isShotState() == true && blocks[i].isPointInside(x, y) == true) {

                //壁を壊すまで
                blocks[i].breake(this.blockBreakTexture);
                shot.Hit();
                return;
            }
        }
        if(enemyManager.isShotPointInside(shot,x,y) == true){
            score+=10;
        }

    }

    public void setNextShotStart() {
        Shot shot = droid.getShot();
        float y = shot.getY();
        if (y > 0 && y < 1.2) {
            shot.setFinal(true);
        }
        shot.setFinal(false);
    }

    /**
     * テキの弾の当たり判定
     */
    private void isEnemyShotPointInside(){

        for(int i=0; i< BLOCK_NUM; i++){
            if (enemyManager.isEnemyShotPointInside(blocks[i].x, blocks[i].y)) {

                blocks[i].breake(this.blockBreakTexture);
                return;
            }
        }
    }

    public void onTouched(float x, float y)
    {
        droid.attack();
        droid.move(x);
    }
}
 