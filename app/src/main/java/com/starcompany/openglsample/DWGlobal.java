package com.starcompany.openglsample;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class DWGlobal {
    private static final String TAG = DWGlobal.class.getSimpleName();
    public static MainActivity mainActivity;
    public static GL10 gl;
    public static Random rand = new Random(System.currentTimeMillis());

}
 