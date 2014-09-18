package jp.fujitaya.zunko.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.GestureDetector;

public class TouchableBitmapWithText extends TouchableBitmap{
    protected String text;
    protected int textSize;
    protected float textOffsetX;
    protected float textOffsetY;
    protected int textColor;
    protected int textBackColor;
    protected boolean drawTextBack;
    protected int textBackAlpha;
    protected RectF textBackRect;
    protected float textRectMerginX, textRectMerginY;

    public TouchableBitmapWithText(Bitmap bitmap,
                                   RectF drawRect,
                                   InsideStrategy strategy,
                                   String text,
                                   int textSize,
                                   float textOffsetX,
                                   float textOffsetY,
                                   int textColor,
                                   GestureDetector.OnGestureListener gestureListener){
        super(bitmap,drawRect,strategy,gestureListener);
        this.text = text;
        this.textSize = textSize;
        this.textOffsetX = textOffsetX;
        this.textOffsetY = textOffsetY;
        this.textColor = textColor;
        drawTextBack = false;
    }
    public TouchableBitmapWithText(Bitmap bitmap,
                                   RectF drawRect,
                                   InsideStrategy strategy,
                                   String text,
                                   int textSize,
                                   float textOffsetX,
                                   float textOffsetY,
                                   int textColor,
                                   int textBackColor,
                                   int textBackAlpha,
                                   GestureDetector.OnGestureListener gestureListener){
        super(bitmap,drawRect,strategy,gestureListener);
        drawTextBack = false;
        this.text = text;
        this.textSize = textSize;
        this.textOffsetX = textOffsetX;
        this.textOffsetY = textOffsetY;
        this.textColor = textColor;
        this.textBackColor = textBackColor;
        this.textBackAlpha = textBackAlpha;
        textRectMerginX = 5f;
        textRectMerginY = 5f;
        drawTextBack = true;
        setTextBackRect();
    }

    private void setTextBackRect(){
        float left, top, right, bottom;
        Paint paint = new Paint();
        paint.setTextSize(textSize);

        left = drawRect.left + textOffsetX - textRectMerginX;
        top = drawRect.top + textOffsetY - textRectMerginY + paint.getFontMetrics().top;
        right = left + paint.measureText(text) + textRectMerginX*2;
        bottom = top + paint.getFontMetrics().bottom
                - paint.getFontMetrics().top + textRectMerginY*2;

        textBackRect = new RectF(left,top,right,bottom);
    }

    @Override
    public void move(float x, float y){
        super.move(x,y);
        if (drawTextBack) textBackRect.offset(x,y);
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (drawTextBack){
            Paint paintBack = new Paint();
            paintBack.setColor(textBackColor);
            paintBack.setAlpha(textBackAlpha);

            canvas.drawRect(textBackRect,paintBack);
        }

        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setAntiAlias(true);

        canvas.drawText(text,drawRect.left+textOffsetX,drawRect.top+textOffsetY,paint);
    }
}
