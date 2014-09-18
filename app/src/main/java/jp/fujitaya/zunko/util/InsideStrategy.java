package jp.fujitaya.zunko.util;

import android.graphics.PointF;

public interface InsideStrategy {
    public void move(float x, float y);
    public void moveOffSet(float x, float y);
    //public void moveTo(PointF point);
    public boolean isInside(PointF point);
}
