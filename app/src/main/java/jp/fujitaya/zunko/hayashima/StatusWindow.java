package jp.fujitaya.zunko.hayashima;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import jp.fujitaya.zunko.util.GameView;

public class StatusWindow {
    private static final float STATUS_WINDOW_WIDTH = 500;
    private float x, y;
    private Paint p;
    private boolean show;

    public StatusWindow(GameView parent){
        p.setAlpha((int)(255*0.8));
        show = false;
    }
    public void moveTo(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void moveOffset(float x, float y){
        this.x += x;
        this.y += y;
    }
    public void show(boolean show){
        this.show = show;
    }

    public void interrupt(MotionEvent event){
    }

    Rect canvasRect = new Rect();
    public void draw(Canvas canvas, float baseX, float baseY){
        if(!show) return;

        canvas.getClipBounds(canvasRect);

        int width = canvasRect.right;
        int height = canvasRect.bottom;

//        canvas.drawRect();
    }
}
