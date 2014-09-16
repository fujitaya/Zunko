package jp.fujitaya.zunko.util;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import jp.fujitaya.zunko.GameActivity;

public abstract class GameScene{
    protected GameActivity parent;

    public GameScene(GameActivity parent){
        this.parent = parent;
    }
    public abstract void update();
    public abstract void draw(Canvas canvas);
    public abstract void dispose();
    public abstract void interrupt(MotionEvent event);
}
