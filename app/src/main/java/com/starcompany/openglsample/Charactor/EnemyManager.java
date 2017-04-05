package com.starcompany.openglsample.Charactor;

import static com.starcompany.openglsample.DWGlobal.gl;
import static com.starcompany.openglsample.DroidWars.TARGET_NUM;

public class EnemyManager {
    private static final String TAG = EnemyManager.class.getSimpleName();

    private int bombTexture;
    private Enemy[] enemies = new Enemy[TARGET_NUM];
    private Ufo apple;
    private Ufo windows;

    public void initializeCharacter(){
        float posY = 0.7f;
        int count = 0;

        for (int i = 0; i < TARGET_NUM; i++) {
            float posX = -0.8f +(0.22f * count);

            if( (i  >= 3  && i <6 ) || (i >= 9 && i < 12 )|| (i >= 15 && i < 18)){
                posX += 0.33f;
            }

            enemies[i] = new Enemy(posX, posY, 0.2f, 0.2f, 0.02f, 0);
            count += 1;
            if(i < 6){
                enemies[i].setFront();
            }

            if(i == 5 || i == 11){
                count = 0;
                posY += 0.21;
            }
        }
        //表示位置は右端
        apple = new Ufo(1.2f, 1.0f, 0.2f, 0.2f, 0.02f, 0);
        windows = new Ufo(1.2f, 0.9f, 0.2f, 0.2f, 0.02f, 0);

    }

    public void setGraphicTexture(int enemy1, int enemy2, int enemy3, int bombTexture, int shot)
    {
        for (int i = 0; i < TARGET_NUM; i++) {
            if(i < 6) {
                enemies[i].setGraphic(gl, enemy1);
            }else if(i < 12){
                enemies[i].setGraphic(gl, enemy2);
            }else{
                enemies[i].setGraphic(gl, enemy3);
            }
            enemies[i].getShot().setGraphic(gl, shot);
            enemies[i].getShot().setBombTexture(bombTexture);

        }
        this.bombTexture = bombTexture;
    }
    private int ufoBombTexture;
    public void setUfoGraphicTexture(int apple, int windows, int bomb){
        this.apple.setGraphic(gl, apple);
        this.windows.setGraphic(gl, windows);
        this.ufoBombTexture = bomb;
    }

    /**
     * 死んだら敵を移動
     */
    public void gameOver(){
        for (int i = 0; i < TARGET_NUM; i++) {
            enemies[i].x = 3.0f;
            enemies[i].y = 3.0f;
            enemies[i].died();
        }
    }

    /**
     * ゲームクリア判定
     * 条件は敵を全て倒したとき
     * @return
     */
    public boolean isGameClear(){

        int die = 0;
        for (int i = 0; i < TARGET_NUM; i++) {
            enemies[i].draw();
            if(enemies[i].isDie()==true){
                die++;
            }
            if(TARGET_NUM == die){
                return true;
            }
        }
        return  false;
    }

    /**
     *　弾があたったかの判定
     * @param x 自機のx, y座標
     * @param y
     * @return
     */
    public boolean isEnemyShotPointInsite(float x, float y) {
        for (int i = 0; i < TARGET_NUM; i++) {

            if (enemies[i].getShot().isPointInside(x, y)) {
                enemies[i].getShot().Hit();
                return true;
            }
        }
        return false;
    }

    /**
     * 被弾
     * @param shot
     * @param x
     * @param y
     * @return
     */
    public boolean isShotPointInsite(Shot shot, float x, float y){

        if(shot.isShotState() == false){
            return false;
        }

        for (int i = 0; i < TARGET_NUM; i++) {

            if (enemies[i].isPointInside(x, y)) {
                enemies[i].died();
                shot.Hit();


                if(i  + 6 < TARGET_NUM){
                    int frontPos = i+6;
                    enemies[frontPos].setFront();
                }

                return true;
            }
        }

        if(apple.isPointInside(x,y)){
            apple.died();
            shot.setBombTexture(this.ufoBombTexture);
            shot.Hit();
            return  true;
        }
        if(windows.isPointInside(x,y)){
            windows.died();
            shot.setBombTexture(this.ufoBombTexture);
            shot.Hit();
            return true;
        }

        return false;
    }



    /**
     * ドロイド君とぶつかったか
     * @param x
     * @return
     */
    public boolean isDroidPointInsite(float x)
    {
        for (int i = 0; i < TARGET_NUM; i++) {
            if (enemies[i].isPointInside(x, -1.2f)) {
                return true;
            }
        }
        return false;
    }


    private int wait = 0;

    private boolean apple_start = false;
    private boolean windows_start = false;
    /**
     * 移動の実行
     * 左から右、　右から左へ
     * まったんまで誰かがたどり着いたら
     * 一ライン前に進む
     *
     * 最前列は弾を撃てる
     * 打つかどうかはランダムにしたい。。
     */
    public void move() {
        //Random rand = DWGlobal.rand; // randamで弾だし
        // Enemy[] enemies = this.enemies;

        // wait
        boolean lineFeed = false;
        for (int i = 0; i < TARGET_NUM; i++) {
            enemies[i].move();
            if(enemies[i].isLinefeed()){
                lineFeed = true;
            }

            if(enemies[i].isFront()){
                enemies[i].attack();
            }
        }

        if(apple_start){
            apple.move();
            apple.draw();
        }
        if(windows_start){
            windows.move();
            windows.draw();
        }


        if(lineFeed == false){
            return;
        }
        for (int i = 0; i<TARGET_NUM; i++) {
            enemies[i].LineFeed();
        }


        if(apple_start){
            windows_start = true;
        }

        apple_start = true;





    }

}
 