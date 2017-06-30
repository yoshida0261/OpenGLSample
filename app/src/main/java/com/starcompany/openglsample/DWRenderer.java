package com.starcompany.openglsample;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.starcompany.openglsample.Charactor.Droidkun;
import com.starcompany.openglsample.Charactor.Shot;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class DWRenderer implements  GLSurfaceView.Renderer{
    private static final String TAG = DWRenderer.class.getSimpleName();
    public static final int TARGET_NUM = 18;
    public static final int BLOCK_NUM = 4;
    private Context context;
    private int width;
    private int height;

    private Droidkun droid = null;

    private DroidWar droidWar;

    private long startTime;
    private boolean gameOverFlag;

    public DWRenderer(Context context) {
        this.context = context;
        startNewGame();
    }

    public void startNewGame() {
        this.startTime = System.currentTimeMillis();
        this.gameOverFlag = false;
        this.droidWar = new DroidWar();
        this.droidWar.initializeCharacter();
        droid = this.droidWar.getDroidInstance();

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

        gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        this.droidWar.renderMain();
        Shot shot = this.droid.getShot();

        this.droidWar.isShotPointInside(shot.getX(), shot.getY());

        shot.setFinal(this.droidWar.isNextShotStart(shot.getY()));
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        DWGlobal.gl = gl;
        this.droidWar.loadTextures(gl, this.context);
        this.droidWar.setGraphicTexture();
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

        droid.attack();
        droid.move(x);
    }

    public void subtractPausedTime(long pausedTime) {
        startTime += pausedTime;
    }

    public long getStartTime() {
        return startTime;
    }
}
 