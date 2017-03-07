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
        int line =0;

        for (int i = 0; i < TARGET_NUM; i++) {
            float posX = -0.8f +(0.22f * count);


            if( (i  >= 3  && i <6 ) || (i >= 9 && i < 12 )|| (i >= 15 && i < 18)){
                posX += 0.33f;
            }

            enemies[i] = new Enemy(posX, posY, 0.2f, 0.2f, 0.02f, 0);
            enemies[i].setLine(line);
            count += 1;

            if(i == 5 || i == 11){
                line++;
                count = 0;
                posY += 0.21;

            }
        }
    }

    public void setGraphicTexture(int enemyTexture, int bombTexture)
    {
        for (int i = 0; i < TARGET_NUM; i++) {
            enemies[i].setGraphic(gl, enemyTexture);
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

    public boolean isShotPointInsite(Shot shot, float x, float y){
        for (int i = 0; i < TARGET_NUM; i++) {

            if (shot.isShotState() == true && enemies[i].isPointInside(x, y)) {
                // enemies　フェードアウト
                enemies[i].x = 3.0f;
                enemies[i].y = 3.0f;
                enemies[i].died();
                shot.Hit(this.bombTexture);
                return true;
            }
        }
        return false;
    }

    public boolean isDroidPointInsite(float x)
    {
        for (int i = 0; i < TARGET_NUM; i++) {
            if (enemies[i].isPointInside(x, -1.2f)) {
                return true;
            }
        }
        return false;
    }



    /**
     * 移動の実行
     * 左から右、　右から左へ
     * まったんまで誰かがたどり着いたら
     * 一ライン前に進む
     */
    public void move() {
        //Random rand = DWGlobal.rand; // randamで弾だし
        // Enemy[] enemies = this.enemies;

        // wait
        for (int i = 0; i < TARGET_NUM; i++) {
            enemies[i].move();
        }
    }

}
 