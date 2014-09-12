package jp.fujitaya.zunko.util;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class GameScene {
    protected Context viewContext;

    public GameScene(Context viewContext){
        this.viewContext = viewContext;
    }
    public abstract void update();
    public abstract void draw(Canvas canvas);
    public abstract void dispose();
    public abstract void interrupt(MotionEvent event);
}
