package jp.fujitaya.zunko.jimmy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import jp.fujitaya.zunko.util.GameScene;

public class TouchableBitmap{
    protected Bitmap bitmap;
    protected GameScene parent;
    InsideStrategyF strategy;
    RectF drawRect;

    public TouchableBitmap(Bitmap bitmap, RectF drawRect, GameScene parent, InsideStrategyF strategy){
        this.bitmap = bitmap;
        this.drawRect = drawRect;
        this.parent = parent;
        this.strategy = strategy;
    }

    public void move(float x, float y){
        drawRect.set(drawRect.left+x, drawRect.right+x, drawRect.top+y, drawRect.bottom+y);
        strategy.move(x,y);
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),
                drawRect,new Paint());
    }

    public boolean isInside(PointF point){
        return strategy.isInside(point);
    }

    public void dispose(){
        if (bitmap != null) bitmap.recycle();
        bitmap = null;
        parent = null;
    }
}
