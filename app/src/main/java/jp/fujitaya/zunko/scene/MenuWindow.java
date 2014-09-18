package jp.fujitaya.zunko.scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;

import jp.fujitaya.zunko.GameView;
import jp.fujitaya.zunko.field.FieldManager;


public class MenuWindow extends StatusWindow{
    String fieldName;
    public MenuWindow(String name){
       fieldName=name;
    }
    public boolean isOnSumAttackMode(PointF f){
        float dx = (GameView.VIEW_WIDTH - sx) / 2;
        float dy = (GameView.VIEW_HEIGHT - sy) / 2;
        float diffy = 35;
        float x = dx + 30;
        float y = dy + diffy*6;
        if(new RectF(dx,y,x,y+diffy*2).contains(f.x,f.y) ){
            return true;
        }
        return false;
    }
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
//        if(FieldManager.getInstance().getField(fieldName).getClass() == (Class<?>)EndField.class) {
//            canvas.drawText("集める", x, y, fp);
//            y += diffy * 2;
//        }
    }
}
