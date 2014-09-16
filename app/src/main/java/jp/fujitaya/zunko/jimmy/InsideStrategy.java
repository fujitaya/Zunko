package jp.fujitaya.zunko.jimmy;

import android.graphics.PointF;

public interface InsideStrategy {
    public void move(float x, float y);
    //public void moveTo(PointF point);
    public boolean isInside(PointF point);
}
