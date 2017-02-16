package com.starcompany.openglsample;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.Log;

import com.starcompany.openglsample.Charactor.Droidkun;
import com.starcompany.openglsample.Charactor.Enemy;
import com.starcompany.openglsample.Effect.ParticleSystem;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class DroidWars implements  GLSurfaceView.Renderer{
    private static final String TAG = DroidWars.class.getSimpleName();
    public static final int TARGET_NUM = 10;
    private static final int GAME_INTERVAL = 60;
    private int score;
    private Context context;

    private int width;
    private int height;
    

    private int bgTexture;
    private int enemyTexture;
    private int droidTexture;
    private int mNumberTexture;
    private int mGameOverTexture;//ゲームオーバー用テクスチャ
    private int mParticleTexture;//パーティクル用テクスチャ

    private ParticleSystem particleSystem;
    private Enemy[] targets = new Enemy[TARGET_NUM];
    private Droidkun droid = null;

    private long startTime;
    private boolean gameOverFlag;
    private Handler handler = new Handler();

    //private MySe mSe;

    public DroidWars(Context context) {
        this.context = context;
        this.particleSystem = new ParticleSystem(300, 30);

        startNewGame();
    }

    private void InitializeCharacter()
    {
        //Random rand = DWGlobal.rand;
        //標的の状態を初期化します
        /*
        for (int i = 0; i < TARGET_NUM; i++) {
            float x = rand.nextFloat() * 2.0f - 1.0f;
            float y = rand.nextFloat() * 2.0f - 1.0f;
            float angle = rand.nextInt(360);
            float size = rand.nextFloat() * 0.25f + 0.25f;
            float speed = rand.nextFloat() * 0.01f + 0.01f;
            float turnAngle = rand.nextFloat() * 4.0f - 2.0f;
            targets[i] = new Enemy(x, y, angle, size, speed, turnAngle);
        }*/
        droid = new Droidkun(0, -0.5f, 0f, 0.5f, 0.02f, 0);

        for (int i = 0; i < TARGET_NUM; i++) {

            targets[i] = new Enemy(0, 0, 0.3f, 0.3f, 0.02f, 0);
        }


    }

    public void startNewGame() {

        InitializeCharacter();
        this.score = 0;
        this.startTime = System.currentTimeMillis();
        this.gameOverFlag = false;
    }

    private void moveEnemy()
    {
        Random rand = DWGlobal.rand;
        Enemy[] targets = this.targets;

        for (int i = 0; i < TARGET_NUM; i++) {

            targets[i].move(0,0);
        }

    }

    //描画を行う部分を記述するメソッドを追加する
    public void renderMain(GL10 gl) {
        // 経過時間を計算する
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
        }

        moveEnemy();

        //背景
        GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 2.0f, 3.0f, bgTexture, 1.0f, 1.0f, 1.0f, 1.0f);
        /*
        // パーティクルを描画します
        mParticleSystem.update();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
        mParticleSystem.draw(gl, mParticleTexture);
        */

        // 標的を描画します
        for (int i = 0; i < TARGET_NUM; i++) {
            targets[i].draw(gl, enemyTexture);
        }
        //droid.draw(gl, droidTexture);
        GraphicUtil.drawTexture(gl, 0.0f, -1.0f, 0.2f, 0.2f, droidTexture, 1.0f, 1.0f, 1.0f, 1.0f);


        gl.glDisable(GL10.GL_BLEND);

        /*
        //得点を描画します
        GraphicUtil.drawNumbers(gl, -0.5f, 1.25f, 0.125f, 0.125f, mNumberTexture, mScore, 8, 1.0f, 1.0f, 1.0f, 1.0f);
        //残り時間を描画します
        GraphicUtil.drawNumbers(gl, 0.5f, 1.2f, 0.4f, 0.4f, mNumberTexture, remainTime, 2, 1.0f, 1.0f, 1.0f, 1.0f);
        //ゲームオーバーテクスチャを描画します。
        if (mGameOverFlag) {
            GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 2.0f, 0.5f, mGameOverTexture, 1.0f, 1.0f, 1.0f, 1.0f);
        }*/
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(-1.0f, 1.0f, -1.5f, 1.5f, 0.5f, -0.5f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        renderMain(gl);
    }

    //テクスチャを読み込むメソッド
    private void loadTextures(GL10 gl) {
        Resources res = context.getResources();
        // TODO テクスチャの設定
        this.enemyTexture = GraphicUtil.loadTexture(gl, res, R.drawable.fly);
        if (enemyTexture == 0) {
            Log.e(getClass().toString(), "load texture error! fly");
        }


        this.bgTexture = GraphicUtil.loadTexture(gl, res, R.drawable.circuit);
        if (bgTexture == 0) {
            Log.e(getClass().toString(), "load texture error! circuit");
        }

        this.droidTexture = GraphicUtil.loadTexture(gl, res, R.drawable.droid2);
        if(droidTexture == 0){
            Log.e(getClass().toString(), "load texture error! droid");
        }

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

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        DWGlobal.gl = gl;
        loadTextures(gl);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    //画面がタッチされたときに呼ばれるメソッド
    public void touched(float x, float y) {
        Log.i(getClass().toString(), String.format("touched! x = %f, y = %f", x, y));
        Enemy[] targets = this.targets;
        Random rand = DWGlobal.rand;

        if (!gameOverFlag) {
            // すべての標的との当たり判定をします
            for (int i = 0; i < TARGET_NUM; i++) {
                if (targets[i].isPointInside(x, y)) {
                    //パーティクルを放出します
                    for (int j = 0; j < 40; j++) {
                        float moveX = (rand.nextFloat() - 0.5f) * 0.05f;
                        float moveY = (rand.nextFloat() - 0.5f) * 0.05f;
                        particleSystem.add(targets[i].x, targets[i].y, 0.2f, moveX, moveY);
                    }
                    // 標的をランダムな位置に移動します
                    float dist = 2.0f;// 画面中央から2.0fはなれた円周上の点
                    float theta = (float) DWGlobal.rand.nextInt(360) / 180.0f * (float) Math.PI;// 適当な位置
                    targets[i].x = (float) Math.cos(theta) * dist;
                    targets[i].y = (float) Math.sin(theta) * dist;
                    score += 100;// 100点加算します
                    Log.i(getClass().toString(), "score = " + score);

                }
            }
        }
    }

    public void subtractPausedTime(long pausedTime) {
        startTime += pausedTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        score = score;
    }
}
 