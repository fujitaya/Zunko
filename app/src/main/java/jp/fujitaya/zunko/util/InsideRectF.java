package jp.fujitaya.zunko.util;

import android.graphics.PointF;
import android.graphics.RectF;

public class InsideRectF implements InsideStrategy {
    RectF rect;

    public InsideRectF(RectF rect){
        this.rect = rect;
    }

    @Override
    public void move(float x, float y) {
        rect.set(rect.left+x, rect.top+y, rect.right+x, rect.bottom+y);
    }
    public void moveOffSet(float x, float y){
        rect.offsetTo(x,y);
    }

    @Override
    public boolean isInside(PointF point) {
        return rect.contains(point.x, point.y);
    }
}
