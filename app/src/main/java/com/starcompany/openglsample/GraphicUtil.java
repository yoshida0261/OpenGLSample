package com.starcompany.openglsample;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GraphicUtil {
    private static final String TAG = GraphicUtil.class.getSimpleName();
    public static final void drawNumbers(GL10 gl, float x, float y, float width, float height, int texture, int number, int figures, float r, float g, float b, float a) {
        float totalWidth = width * (float)figures;//n文字分の横幅
        float rightX = x + (totalWidth * 0.5f);//右端のx座標
        float fig1X = rightX - width * 0.5f;//一番右の桁の中心のx座標
        for (int i = 0; i < figures; i++) {
            float figNX = fig1X - (float)i * width;//n桁目の中心のx座標
            int numberToDraw = number % 10;
            number = number / 10;
            drawNumber(gl, figNX, y, width, height, texture, numberToDraw, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public static final void drawNumber(GL10 gl, float x, float y, float w, float h, int texture, int number, float r, float g, float b, float a) {
        float u = (float)(number % 4) * 0.25f;
        float v = (float)(number / 4) * 0.25f;
        drawTexture(gl, x, y, w, h, texture, u, v, 0.25f, 0.25f, r, g, b, a);
    }

    public static final void drawTexture(GL10 gl, float x, float y, float width, float height, int texture, float u, float v, float tex_w, float tex_h, float r, float g, float b, float a) {
        float[] vertices = {
                -0.5f * width + x, -0.5f * height + y,
                0.5f * width + x, -0.5f * height + y,
                -0.5f * width + x,  0.5f * height + y,
                0.5f * width + x,  0.5f * height + y,
        };

        float[] colors = {
                r, g, b, a,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a,
        };

        float[] coords = {
                u, v + tex_h,
                u + tex_w, v + tex_h,
                u,         v,
                u + tex_w,         v,
        };

        FloatBuffer polygonVertices = makeFloatBuffer(vertices);
        FloatBuffer polygonColors = makeFloatBuffer(colors);
        FloatBuffer texCoords = makeFloatBuffer(coords);

        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, polygonColors);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoords);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    public static final void drawTexture(GL10 gl, float x, float y, float width, float height, int texture, float r, float g, float b, float a) {
        drawTexture(gl, x, y, width, height, texture, 0.0f, 0.0f, 1.0f, 1.0f, r, g, b, a);
    }

    public static final int loadTexture(GL10 gl, Resources resources, int resId) {
        int[] textures = new int[1];

        //Bitmapの作成
        Bitmap bmp = BitmapFactory.decodeResource(resources, resId, options);
        if (bmp == null) {
            return 0;
        }

        // OpenGL用のテクスチャを生成します
        gl.glGenTextures(1, textures, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);

        //OpenGLへの転送が完了したので、VMメモリ上に作成したBitmapを破棄する
        bmp.recycle();

        //TextureManagerに登録する
        TextureManager.addTexture(resId, textures[0]);

        if(textures[0] == 0){
            Log.e("GraphicUtil", "load texture error! " + resources.toString());
        }

        return textures[0];
    }
    private static final BitmapFactory.Options options = new BitmapFactory.Options();
    static {
        options.inScaled = false;//リソースの自動リサイズをしない
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;//32bit画像として読み込む
    }

    public static final void drawCircle(GL10 gl, float x, float y, int divides, float radius, float r, float g, float b, float a) {
        float[] vertices = new float[divides * 3 * 2];

        int vertexId = 0;//頂点配列の要素の番号を記憶しておくための変数
        for (int i = 0; i < divides; i++) {
            //円周上のi番目の頂点の角度(ラジアン)を計算します
            float theta1 = 2.0f / (float)divides * (float)i * (float)Math.PI;

            //円周上の(i + 1)番目の頂点の角度(ラジアン)を計算します
            float theta2 = 2.0f / (float)divides * (float)(i+1) * (float)Math.PI;

            //i番目の三角形の0番目の頂点情報をセットします
            vertices[vertexId++] = x;
            vertices[vertexId++] = y;

            //i番目の三角形の1番目の頂点の情報をセットします (円周上のi番目の頂点)
            vertices[vertexId++] = (float)Math.cos((double)theta1) * radius + x;//x座標
            vertices[vertexId++] = (float)Math.sin((double)theta1) * radius + y;//y座標

            //i番目の三角形の2番目の頂点の情報をセットします (円周上のi+1番目の頂点)
            vertices[vertexId++] = (float)Math.cos((double)theta2) * radius + x;//x座標
            vertices[vertexId++] = (float)Math.sin((double)theta2) * radius + y;//y座標
        }
        FloatBuffer polygonVertices = makeFloatBuffer(vertices);

        //ポリゴンの色を指定します
        gl.glColor4f(r, g, b, a);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, divides * 3);
    }

    public static final void drawRectangle(GL10 gl, float x, float y, float width, float height, float r, float g, float b, float a) {
        float[] vertices = {
                -0.5f * width + x, -0.5f * height + y,
                0.5f * width + x, -0.5f * height + y,
                -0.5f * width + x,  0.5f * height + y,
                0.5f * width + x,  0.5f * height + y,
        };

        float[] colors = {
                r, g, b, a,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a,
        };

        FloatBuffer polygonVertices = makeFloatBuffer(vertices);
        FloatBuffer polygonColors = makeFloatBuffer(colors);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, polygonColors);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
    }

    public static final void drawSquare(GL10 gl, float x, float y, float r, float g, float b, float a) {
        drawRectangle(gl, x, y, 1.0f, 1.0f, r, g, b, a);
    }

    public static final void drawSquare(GL10 gl, float r, float g, float b, float a) {
        drawSquare(gl, 0.0f, 0.0f, r, g, b, a);
    }

    public static final void drawSquare(GL10 gl) {
        drawSquare(gl, 1.0f, 0.0f, 0.0f, 1.0f);
    }

    public static final FloatBuffer makeFloatBuffer(float[] arr) {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
    }
}
 