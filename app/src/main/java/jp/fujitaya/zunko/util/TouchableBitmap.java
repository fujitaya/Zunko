package jp.fujitaya.zunko.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import jp.fujitaya.zunko.util.InsideRectF;
import jp.fujitaya.zunko.util.InsideStrategy;

import static android.view.GestureDetector.OnGestureListener;

public class TouchableBitmap{
    //BitmapとdrawRectをArrayListで管理したいが，後回し
    private Bitmap bitmap;
    private InsideStrategy strategy;
    private RectF drawRect;
    private OnGestureListener gestureListener;

    public TouchableBitmap(Bitmap bitmap, RectF drawRect,
                           InsideStrategy strategy, OnGestureListener gestureListener){
        this.bitmap = bitmap;
        this.drawRect = drawRect;
        this.strategy = strategy;
        this.gestureListener = gestureListener;
    }
    public TouchableBitmap(Bitmap bitmap, RectF drawRect, OnGestureListener gestureListener){
        this.bitmap = bitmap;
        this.drawRect = drawRect;
        this.strategy = new InsideRectF(new RectF(drawRect.left,drawRect.top,drawRect.right,drawRect.bottom));
        this.gestureListener = gestureListener;
    }

    public void move(float x, float y){
        //drawRect.set(drawRect.left+x, drawRect.top+y, drawRect.right+x, drawRect.bottom+y);
        drawRect.offset(x,y);
        strategy.move(x,y);
    }

    public void moveTo(float left, float top){
        float moveX = left - drawRect.left;
        float moveY = top - drawRect.top;

        move(left, top);
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void draw(Canvas canvas){
        if (bitmap != null)
            canvas.drawBitmap(bitmap,null,
                drawRect,null);
    }

    public boolean isInside(PointF point){
        return strategy.isInside(point);
    }

    public OnGestureListener getGestureListener(){
        return gestureListener;
    }

    public void dispose(){
        if (bitmap != null) bitmap.recycle();
        bitmap = null;
    }
}
