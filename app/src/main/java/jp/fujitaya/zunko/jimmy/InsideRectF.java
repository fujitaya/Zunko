package jp.fujitaya.zunko.jimmy;

import android.graphics.PointF;
import android.graphics.RectF;

public class InsideRectF implements InsideStrategyF {
    RectF rect;

    public InsideRectF(RectF rect){
        this.rect = rect;
    }

    @Override
    public void move(float x, float y) {
        rect.set(rect.left+x, rect.right+x, rect.top+y, rect.bottom+y);
    }

    @Override
    public boolean isInside(PointF point) {
        return rect.contains(point.x, point.y);
    }
}
