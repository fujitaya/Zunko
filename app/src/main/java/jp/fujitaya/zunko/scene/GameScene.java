package jp.fujitaya.zunko.scene;

import android.graphics.Canvas;
import android.view.MotionEvent;

import jp.fujitaya.zunko.GameView;

public abstract class GameScene {
    protected GameView parent;

    public GameScene(GameView parent){
        this.parent = parent;
    }
    public abstract void update();
    public abstract void draw(Canvas canvas);
    public abstract void dispose();
    public abstract void interrupt(MotionEvent event);
}
