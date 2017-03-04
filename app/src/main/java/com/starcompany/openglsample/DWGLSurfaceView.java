package com.starcompany.openglsample;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class DWGLSurfaceView extends GLSurfaceView{
    private static final String TAG = DWGLSurfaceView.class.getSimpleName();
    private float width;
    private float height;

    private DroidWars mMyRenderer;

    public DWGLSurfaceView(Context context) {
        super(context);
        setFocusable(true);
    }

    @Override
    public void setRenderer(Renderer renderer) {
        super.setRenderer(renderer);
        this.mMyRenderer = (DroidWars)renderer;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        super.surfaceChanged(holder, format, w, h);
        this.width = w;
        this.height = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = (event.getX() / (float) width) * 2.0f - 1.0f;
        float y = (event.getY() / (float) height) * -3.0f + 1.5f;
        mMyRenderer.touched(x, y);

        // 導入してみたが、死ぬので保留
        //Vibrator vibrator = (Vibrator) DWGlobal.mainActivity.getSystemService(VIBRATOR_SERVICE);
        //vibrator.vibrate(10);

        return false;
    }
}
 