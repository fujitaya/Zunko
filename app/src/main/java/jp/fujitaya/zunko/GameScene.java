package jp.fujitaya.zunko;

import android.graphics.Canvas;

public abstract class GameScene {
    public abstract void init();
    public abstract void update();
    public abstract void draw(Canvas canvas);
    public abstract void dispose();
}
