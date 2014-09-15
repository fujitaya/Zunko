package jp.fujitaya.zunko.hayashima;

import android.graphics.Canvas;
import android.view.MotionEvent;

import jp.fujitaya.zunko.util.GameScene;
import jp.fujitaya.zunko.util.GameView;

public class TitleScene extends GameScene {
    public TitleScene(GameView parent){
        super(parent);
    }


    @Override
    public void dispose(){
    }

    @Override
    public void update(){
    }

    @Override
    public void interrupt(MotionEvent event){}

    @Override
    public void draw(Canvas canvas){
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int baseX = 0;
        int baseY = height - height/5;


    }
}
