package jp.fujitaya.zunko.scene;

import android.graphics.Canvas;
import android.graphics.Color;

public class CaptureMenuWindow extends StatusWindow {
public CaptureMenuWindow(){}
    @Override
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
        canvas.drawText("メニュー", x, y, fp);
        y += diffy*2;
        canvas.drawText("ステージ選択へ", x, y, fp);
        y += diffy*2;
    }
}
