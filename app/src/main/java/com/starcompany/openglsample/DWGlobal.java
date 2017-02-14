package com.starcompany.openglsample;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class DWGlobal {
    private static final String TAG = DWGlobal.class.getSimpleName();
    // MainActivity
    public static MainActivity mainActivity;

    //GLコンテキストを保持する変数
    public static GL10 gl;

    //ランダムな値を生成する
    public static Random rand = new Random(System.currentTimeMillis());

}
 