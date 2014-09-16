package jp.fujitaya.zunko.jimmy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class TouchableBitmap {
    private Bitmap bitmap;
    private InsideStrategy strategy;
    private RectF drawRect;
    private OnClick onClick;

    public TouchableBitmap(Bitmap bitmap, RectF drawRect,
                           InsideStrategy strategy, OnClick onClick){
        this.bitmap = bitmap;
        this.drawRect = drawRect;
        this.strategy = strategy;
        this.onClick = onClick;
    }
    public TouchableBitmap(Bitmap bitmap, RectF drawRect, OnClick onClick){
        this.bitmap = bitmap;
        this.drawRect = drawRect;
        this.strategy = new InsideRectF(new RectF(drawRect.left,drawRect.top,drawRect.right,drawRect.bottom));
        this.onClick = onClick;
    }

    public void move(float x, float y){
        drawRect.set(drawRect.left+x, drawRect.right+x, drawRect.top+y, drawRect.bottom+y);
        strategy.move(x,y);
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void draw(Canvas canvas){
        if (bitmap != null)
            canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),
                drawRect,new Paint());
    }

    public boolean isInside(PointF point){
        return strategy.isInside(point);
    }

    public void onClick(){
        onClick.onClick();
    }

    public void dispose(){
        if (bitmap != null) bitmap.recycle();
        bitmap = null;
    }
}
