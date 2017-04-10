package com.starcompany.openglsample;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.Log;

import com.starcompany.openglsample.Charactor.Block;
import com.starcompany.openglsample.Charactor.Droidkun;
import com.starcompany.openglsample.Charactor.Enemy;
import com.starcompany.openglsample.Charactor.Shot;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class DroidWars implements  GLSurfaceView.Renderer{
    private static final String TAG = DroidWars.class.getSimpleName();
    public static final int TARGET_NUM = 18;
    public static final int BLOCK_NUM = 4;
    private static final int GAME_INTERVAL = 60;
    private Context context;
    private int width;
    private int height;

    private Enemy[] enemies = null;
    private Droidkun droid = null;
    private Block[] blocks = null;

    private DWTouchEvent touchEvent;
    private DWRenderer renderer;

    private long startTime;
    private boolean gameOverFlag;
    private Handler handler = new Handler();
    //private MySe mSe;

    public DroidWars(Context context) {
        this.context = context;
        startNewGame();
    }

    public void startNewGame() {
        this.startTime = System.currentTimeMillis();
        this.gameOverFlag = false;
        this.renderer = new DWRenderer();
        this.renderer.initializeCharacter();
        droid = this.renderer.getDroidInstance();
        enemies = this.renderer.getEnemyInstance();
        blocks = this.renderer.getBlocksInstance();
        this.touchEvent = new DWTouchEvent(droid, enemies);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //画面領域の設定
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(-1.0f, 1.0f, -1.5f, 1.5f, 0.5f, -0.5f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        //
        //gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        this.renderer.renderMain();
        Shot shot = this.droid.getShot();

        this.renderer.isShotPointInside(shot.getX(), shot.getY());
        if(this.renderer.isNextShotStart(shot.getY())){
            shot.setFinal(true);
        }

        //  this.renderer.isDroidPointInside();
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        DWGlobal.gl = gl;
        this.renderer.loadTextures(gl, this.context);
        this.renderer.setGraphicTexture();
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


    public void setScore(int score) {
        score = score;
    }
}
 