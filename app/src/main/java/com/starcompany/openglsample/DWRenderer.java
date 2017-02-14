package com.starcompany.openglsample;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.Log;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class DWRenderer implements  GLSurfaceView.Renderer{
    private static final String TAG = DWRenderer.class.getSimpleName();
    public static final int TARGET_NUM = 10;//標的の数
    private static final int GAME_INTERVAL = 60;//制限時間は60秒

    private int mScore;//得点

    // コンテキスト
    private Context mContext;

    private int mWidth;
    private int mHeight;

    // テクスチャ
    private int mBgTexture;
    private int mTargetTexture;//標的用テクスチャ
    private int mNumberTexture;
    private int mGameOverTexture;//ゲームオーバー用テクスチャ
    private int mParticleTexture;//パーティクル用テクスチャ

    private ParticleSystem mParticleSystem;//パーティクルシステム

    //標的
    private Enemey[] mTargets = new Enemey[TARGET_NUM];

    private long mStartTime;//開始時間
    private boolean mGameOverFlag;//ゲームオーバーであるか

    private Handler mHandler = new Handler();//ハンドラー

    //private MySe mSe;

    public DWRenderer(Context context) {
        this.mContext = context;
        this.mParticleSystem = new ParticleSystem(300, 30);//生成します

        startNewGame();
    }

    public void startNewGame() {
        Random rand = DWGlobal.rand;
        //標的の状態を初期化します
        for (int i = 0; i < TARGET_NUM; i++) {
            float x = rand.nextFloat() * 2.0f - 1.0f;
            float y = rand.nextFloat() * 2.0f - 1.0f;
            float angle = rand.nextInt(360);
            float size = rand.nextFloat() * 0.25f + 0.25f;
            float speed = rand.nextFloat() * 0.01f + 0.01f;
            float turnAngle = rand.nextFloat() * 4.0f - 2.0f;
            mTargets[i] = new Enemey(x, y, angle, size, speed, turnAngle);
        }

        this.mScore = 0;
        this.mStartTime = System.currentTimeMillis();//開始時間を保持します
        this.mGameOverFlag = false;//ゲームオーバー状態ではない
    }

    //描画を行う部分を記述するメソッドを追加する
    public void renderMain(GL10 gl) {
        // 経過時間を計算する
        int passedTime = (int) (System.currentTimeMillis() - mStartTime) / 1000;
        int remainTime = GAME_INTERVAL - passedTime;// 　残り時間を計算する
        if (remainTime <= 0) {
            remainTime = 0;// 残り時間がマイナスにならないようにする
            if (!mGameOverFlag) {
                mGameOverFlag = true;// ゲームオーバー状態にする
                // Global.mainActivity.showRetryButton()をUIスレッド上で実行する
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        DWGlobal.mainActivity.showRetryButton();
                    }
                });
            }
        }
        Random rand = DWGlobal.rand;
        Enemey[] targets = mTargets;
        // 全ての標的を1つずつ動かします
        for (int i = 0; i < TARGET_NUM; i++) {
            // ランダムなタイミングで方向転換するようにします
            if (rand.nextInt(100) == 0) {// 100回に1回の確率で方向転換させます
                // 旋回する角度を -2.0〜2.0の間でランダムに設定します。
                targets[i].mTurnAngle = rand.nextFloat() * 4.0f - 2.0f;
            }

            // ここで標的を旋回させます
            targets[i].mAngle = targets[i].mAngle + targets[i].mTurnAngle;
            // 標的を動かします
            targets[i].move();
            // パーティクルを使って軌跡を描画します
            float moveX = (rand.nextFloat() - 0.5f) * 0.01f;
            float moveY = (rand.nextFloat() - 0.5f) * 0.01f;
            mParticleSystem.add(targets[i].mX, targets[i].mY, 0.1f, moveX, moveY);
        }

        // 背景を描画する
        GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 2.0f, 3.0f, mBgTexture, 1.0f, 1.0f, 1.0f, 1.0f);
        /*
        // パーティクルを描画します
        mParticleSystem.update();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
        mParticleSystem.draw(gl, mParticleTexture);
        */

        // 標的を描画します
        for (int i = 0; i < TARGET_NUM; i++) {
            targets[i].draw(gl, mTargetTexture);
        }
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
        gl.glViewport(0, 0, mWidth, mHeight);
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
        Resources res = mContext.getResources();
        // TODO テクスチャの設定
        this.mTargetTexture = GraphicUtil.loadTexture(gl, res, R.drawable.fly);
        if (mTargetTexture == 0) {
            Log.e(getClass().toString(), "load texture error! fly");
        }


        this.mBgTexture = GraphicUtil.loadTexture(gl, res, R.drawable.circuit);
        if (mBgTexture == 0) {
            Log.e(getClass().toString(), "load texture error! circuit");
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
        this.mWidth = width;
        this.mHeight = height;

        DWGlobal.gl = gl;//GLコンテキストを保持する

        //テクスチャをロードする
        loadTextures(gl);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    //画面がタッチされたときに呼ばれるメソッド
    public void touched(float x, float y) {
        Log.i(getClass().toString(), String.format("touched! x = %f, y = %f", x, y));
        Enemey[] targets = mTargets;
        Random rand = DWGlobal.rand;

        if (!mGameOverFlag) {
            // すべての標的との当たり判定をします
            for (int i = 0; i < TARGET_NUM; i++) {
                if (targets[i].isPointInside(x, y)) {
                    //パーティクルを放出します
                    for (int j = 0; j < 40; j++) {
                        float moveX = (rand.nextFloat() - 0.5f) * 0.05f;
                        float moveY = (rand.nextFloat() - 0.5f) * 0.05f;
                        mParticleSystem.add(targets[i].mX, targets[i].mY, 0.2f, moveX, moveY);
                    }
                    // 標的をランダムな位置に移動します
                    float dist = 2.0f;// 画面中央から2.0fはなれた円周上の点
                    float theta = (float) DWGlobal.rand.nextInt(360) / 180.0f * (float) Math.PI;// 適当な位置
                    targets[i].mX = (float) Math.cos(theta) * dist;
                    targets[i].mY = (float) Math.sin(theta) * dist;
                    mScore += 100;// 100点加算します
                    Log.i(getClass().toString(), "score = " + mScore);

                }
            }
        }
    }

    public void subtractPausedTime(long pausedTime) {
        mStartTime += pausedTime;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }
}
 