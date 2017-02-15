package com.starcompany.openglsample;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;


public class MainActivity extends AppCompatActivity {


    private Button mRetryButton;//リトライボタン
    private DroidWars mRenderer;
    private long mPauseTime = 0L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // フルスクリーン、タイトルバーの非表示
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //インスタンスを保持させる
        DWGlobal.mainActivity = this;

        this.mRenderer = new DroidWars(this);
        DWGLSurfaceView glSurfaceView = new DWGLSurfaceView(this);
        glSurfaceView.setRenderer(mRenderer);
        setContentView(glSurfaceView);

        //ボタンのレイアウト
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        params.setMargins(0, 150, 0, 0);//ゲームオーバーテクスチャにかぶらないようにする
        //ボタンの作成
        this.mRetryButton = new Button(this);
        this.mRetryButton.setText("Retry");
        hideRetryButton();//非表示にする
        addContentView(mRetryButton, params);
        //イベントの追加


        this.mRetryButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                hideRetryButton();
                mRenderer.startNewGame();
            }
        });


        //　保存した状態に戻す
        if (savedInstanceState != null) {
            long startTime = savedInstanceState.getLong("startTime");
            long pauseTime = savedInstanceState.getLong("pauseTime");
            int score = savedInstanceState.getInt("score");
            long pausedTime = pauseTime - startTime;
            mRenderer.subtractPausedTime(-pausedTime);
            mRenderer.setScore(score);
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
            mRenderer.subtractPausedTime(pausedTime);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // テクスチャを削除する
        TextureManager.deleteAll(DWGlobal.gl);

        mPauseTime = System.currentTimeMillis();//バックグラウンドになった時刻を覚えておく
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 状態を保存する
        outState.putLong("startTime", mRenderer.getStartTime());//開始時間
        outState.putLong("pauseTime", System.currentTimeMillis());//onPauseした時間
        outState.putInt("score", mRenderer.getScore());//スコア
    }


}
