package jp.fujitaya.zunko.scene;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import jp.fujitaya.zunko.util.InsideRectF;
import jp.fujitaya.zunko.util.InsideStrategy;

public class NoticeWindow {
    private RectF drawRect;
    private int rectColor;
    private int rectAlpha;
    private String text;
    private int textColor;
    private int textSize;
    private int textOffsetX, textOffsetY;
    private float lineInterval;
    private InsideStrategy inside;

    public NoticeWindow(RectF drawRect, String text,
                        int rectColor, int rectAlpha, int textColor){
        this.drawRect = drawRect;
        this.text = text;
        this.rectColor = rectColor;
        this.rectAlpha = rectAlpha;
        this.textColor = textColor;
        this.textSize = 50;
        this.lineInterval = 52;
        this.textOffsetX = 30;
        this.textOffsetY = 60;
        this.inside = new InsideRectF(new RectF(drawRect.left,drawRect.
                top,drawRect.right,drawRect.bottom));
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        float textX, textY;

        paint.setColor(rectColor);
        paint.setAlpha(rectAlpha);
        canvas.drawRect(drawRect,paint);

        paint = new Paint();
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        textX = drawRect.left + textOffsetX;
        textY = drawRect.top + textOffsetY;

        for (String line : text.split("\n")){
            canvas.drawText(line,textX,textY,paint);
            textY += lineInterval;
        }
    }

    public void setText(String text){
        this.text = text;
    }

    public void move(float x, float y){
        this.drawRect.offset(x,y);
        this.inside.move(x,y);
    }

    public RectF getRect(){
        return drawRect;
    }

    public boolean isInside(PointF point){
        return inside.isInside(point);
    }

    public void dispose(){

    }
}
