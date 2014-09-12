package jp.fujitaya.zunko.util;

import android.graphics.Canvas;

public abstract class GameScene {
    public abstract void update();
    public abstract void draw(Canvas canvas);
    public abstract void dispose();
}
