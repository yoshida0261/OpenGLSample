package com.starcompany.openglsample;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.Log;

import com.starcompany.openglsample.Charactor.Block;
import com.starcompany.openglsample.Charactor.Droidkun;
import com.starcompany.openglsample.Charactor.Enemy;
import com.starcompany.openglsample.Charactor.Shot;
import com.starcompany.openglsample.Effect.ParticleSystem;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class DroidWars implements  GLSurfaceView.Renderer{
    private static final String TAG = DroidWars.class.getSimpleName();
    public static final int TARGET_NUM = 1;
    public static final int BLOCK_NUM = 6;
    private static final int GAME_INTERVAL = 60;
    private int score;
    private Context context;
    private int width;
    private int height;

    private int bgTexture;
    private int enemyTexture;
    private int droidTexture;
    private int blockTexture;
    private int ufoTexture;

    private int mNumberTexture;
    private int mGameOverTexture;//ゲームオーバー用テクスチャ
    private int mParticleTexture;//パーティクル用テクスチャ

    private ParticleSystem particleSystem;
    private Enemy[] enemies = new Enemy[TARGET_NUM];
    private Droidkun droid = null;
    private Block[] blocks = new Block[BLOCK_NUM];
    private DWTouchEvent touchEvent;
    private DWRenderer renderer;

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
        droid = new Droidkun(0, -0.5f, 0f, 0.5f, 0.02f, 0);
        for (int i = 0; i < TARGET_NUM; i++) {
            enemies[i] = new Enemy(0, 0, 0.3f, 0.3f, 0.02f, 0);
        }
        for(int i =0;i<BLOCK_NUM;i++){
            blocks[i] = new Block(-0.75f + 0.3f * i , -0.8f, 0, 0.3f, 0.02f, 0);
        }
    }

    public void startNewGame() {
        InitializeCharacter();
        this.score = 0;
        this.startTime = System.currentTimeMillis();
        this.gameOverFlag = false;
        this.touchEvent = new DWTouchEvent(droid, enemies);
        this.renderer = new DWRenderer(droid, enemies, blocks);
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

        GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 2.0f, 3.0f, bgTexture, 1.0f, 1.0f, 1.0f, 1.0f);
        this.renderer.renderMain();
        Shot shot = this.droid.getShot();
        this.touchEvent.isPointInside(shot.getX(), shot.getY());
    }

    //テクスチャを読み込むメソッド
    private void loadTextures(GL10 gl) {
        Resources res = context.getResources();
        this.enemyTexture = GraphicUtil.loadTexture(gl, res, R.drawable.fly);
        this.bgTexture = GraphicUtil.loadTexture(gl, res, R.drawable.circuit);
        this.droidTexture = GraphicUtil.loadTexture(gl, res, R.drawable.droid2);
        this.blockTexture = GraphicUtil.loadTexture(gl, res, R.drawable.block);

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
        for (int i = 0; i < TARGET_NUM; i++) {
            enemies[i].setGraphic(gl, this.enemyTexture);
        }
        for (int i = 0; i < BLOCK_NUM; i++) {
            blocks[i].setGraphic(gl, this.blockTexture);
        }
        droid.setGraphic(gl, this.droidTexture);
        droid.getShot().setGraphic(gl, droidTexture);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    //画面がタッチされたときに呼ばれるメソッド
    public void touched(float x, float y) {
        Log.i(getClass().toString(), String.format("touched! x = %f, y = %f", x, y));
        if (gameOverFlag) {
            return;
        }
        touchEvent.onTouch(x,y);
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
 