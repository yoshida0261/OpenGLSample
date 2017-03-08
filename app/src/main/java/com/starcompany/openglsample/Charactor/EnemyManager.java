package com.starcompany.openglsample.Charactor;

import static com.starcompany.openglsample.DWGlobal.gl;
import static com.starcompany.openglsample.DroidWars.TARGET_NUM;

public class EnemyManager {
    private static final String TAG = EnemyManager.class.getSimpleName();

    private int bombTexture;
    private Enemy[] enemies = new Enemy[TARGET_NUM];

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
    }

    public void setGraphicTexture(int enemy1, int enemy2, int enemy3, int bombTexture)
    {
        for (int i = 0; i < TARGET_NUM; i++) {
            if(i < 6) {
                enemies[i].setGraphic(gl, enemy1);
            }else if(i < 12){
                enemies[i].setGraphic(gl, enemy2);
            }else{
                enemies[i].setGraphic(gl, enemy3);
            }

        }
        this.bombTexture = bombTexture;
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
                // enemies　フェードアウト
                enemies[i].died();
                shot.Hit(this.bombTexture);

                //後ろのやつが弾を撃てるように
                if(i  + 6 < TARGET_NUM){
                    int frontPos = i+6;
                    enemies[frontPos].setFront();
                }

                return true;
            }
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
        if(lineFeed == false){
            return;
        }
        for (int i = 0; i<TARGET_NUM; i++) {
            enemies[i].LineFeed();
        }



        //弾発射のwait todo
        wait++;
        if(wait < 30){
            return;
        }
        wait = 0;

    }

}
 