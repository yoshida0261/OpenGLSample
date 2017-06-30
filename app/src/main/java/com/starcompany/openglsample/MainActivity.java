package com.starcompany.openglsample;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;


public class MainActivity extends Activity {


    private Button mRetryButton;//リトライボタン
    private DWRenderer droidWars;
    private long mPauseTime = 0L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全画面設定
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        DWGlobal.mainActivity = this;

        //surfaceViewからtouchを受け取れるようにする
        this.droidWars = new DWRenderer(this);
        DWGLSurfaceView glSurfaceView = new DWGLSurfaceView(this);
        glSurfaceView.setRenderer(droidWars);
        setContentView(glSurfaceView);


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        params.setMargins(0, 150, 0, 0);


        this.mRetryButton = new Button(this);
        this.mRetryButton.setText("Retry");
        hideRetryButton();//非表示にする
        addContentView(mRetryButton, params);


        this.mRetryButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                hideRetryButton();
                droidWars.startNewGame();
            }
        });


        if (savedInstanceState != null) {
            long startTime = savedInstanceState.getLong("startTime");
            long pauseTime = savedInstanceState.getLong("pauseTime");
            long pausedTime = pauseTime - startTime;
            droidWars.subtractPausedTime(-pausedTime);
        }
    }

    //リトライボタンを表示する
    public void showRetryButton() {
       mRetryButton.setVisibility(View.VISIBLE);
    }

    //リトライボタンを非表示にする
    public void hideRetryButton() {
        mRetryButton.setVisibility(View.INVISIBLE);
    }


    public void onResume() {
        super.onResume();


        if (mPauseTime != 0L) {
            // バックグラウンドになっていた時間を計算する
            long pausedTime = System.currentTimeMillis() - mPauseTime;
            droidWars.subtractPausedTime(pausedTime);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        TextureManager.deleteAll(DWGlobal.gl);

        mPauseTime = System.currentTimeMillis();//バックグラウンドになった時刻を覚えておく
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("startTime", droidWars.getStartTime());//開始時間
        outState.putLong("pauseTime", System.currentTimeMillis());//onPauseした時間
        //outState.putInt("score", droidWars.getScore());//スコア
    }


}
