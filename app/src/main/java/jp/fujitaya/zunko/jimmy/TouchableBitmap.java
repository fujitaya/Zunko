package jp.fujitaya.zunko.jimmy;

import android.graphics.Bitmap;
import android.graphics.PointF;

import jp.fujitaya.zunko.util.GameScene;

public class TouchableBitmap{
    protected Bitmap bitmap;
    protected GameScene parent;
    InsideStrategyF strategy;

    protected TouchableBitmap(Bitmap bitmap, GameScene parent, InsideStrategyF strategy){
        this.bitmap = bitmap;
        this.parent = parent;
        this.strategy = strategy;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public boolean isInside(PointF point){
        return strategy.isInside(point);
    }
}
