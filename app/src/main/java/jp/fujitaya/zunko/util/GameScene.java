package jp.fujitaya.zunko.util;

import android.graphics.Canvas;
import android.view.MotionEvent;

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
