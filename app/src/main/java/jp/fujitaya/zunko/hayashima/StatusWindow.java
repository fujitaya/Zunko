package jp.fujitaya.zunko.hayashima;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.Image;

public class StatusWindow {
    private float x, y;
    private float sx, sy;
    private Paint p, fp;
    private boolean show;
    private Image btn;

    public StatusWindow(){
        p = new Paint();
        p.setAlpha((int)(255*0.5));
        fp = new Paint();
        fp.setTextSize(30);
        fp.setColor(Color.BLACK);
        show = true;
        sx = 500;
        sy = 600;

        btn = new Image(R.drawable.btn_zukan);
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

    public void touched(float x, float y){

    }

    float frameSize = 5;
    Rect canvasRect = new Rect();
    public void draw(Canvas canvas, float baseX, float baseY){
        if(!show) return;

        canvas.getClipBounds(canvasRect);

        int width = canvasRect.right;
        int height = canvasRect.bottom;

        float dx = (width - sx) / 2;
        float dy = (height - sy) / 2;

        p.setColor(Color.BLACK);
        canvas.drawRect(baseX+dx, baseY+dy,
                baseX+dx+sx, baseY+dy+sy, p);
        p.setColor(Color.rgb(180, 180, 180));
        canvas.drawRect(baseX+dx+frameSize, baseY+dy+frameSize,
                baseX+dx+sx-frameSize, baseY+dy+sy-frameSize, p);

        p.setColor(Color.BLACK);
        float diffy = 35;
        float x = dx + 30;
        float y = dy + diffy*2;
        canvas.drawText("ステイタス", x, y, fp);
        y += diffy*2;
        canvas.drawText(String.format("プレイ時間　%d時間", 12), x, y, fp);
        y +=diffy*2;
        canvas.drawText(String.format("ずん子の数　%dずん", 100), x, y, fp);
        y += diffy;
        canvas.drawText(String.format("　宮城　　　%dずん", 20), x, y, fp);
        y += diffy;
        canvas.drawText(String.format("　東北　　　%dずん", 80), x, y, fp);
        y += diffy*2;
        canvas.drawText("攻略度", x, y, fp);
        y += diffy;
        canvas.drawText(String.format("　宮城県　　%d％", 80), x, y, fp);
        y += diffy;
        canvas.drawText(String.format("　東北　　　%d％", 0), x, y, fp);

        y += diffy*2;
        btn.draw(canvas, (width-btn.getWidth())/2, y);
    }
}
