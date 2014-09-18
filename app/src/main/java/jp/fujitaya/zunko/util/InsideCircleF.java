package jp.fujitaya.zunko.util;

import android.graphics.PointF;

public class InsideCircleF implements InsideStrategy {
    private PointF center;
    private float radius;

    public InsideCircleF(PointF center, float radius){
        this.center = center;
        this.radius = radius;
    }

    public void setCenter(PointF center){this.center = center;}
    public void setRadius(float Radius){this.radius = radius;}

    @Override
    public void move(float x, float y) {
        center.set(center.x + x, center.y + y);
    }
    @Override public void moveOffSet(float x,float y){}
    @Override
    public boolean isInside(PointF point) {
        float distanceX = point.x - center.x;
        float distanceY = point.y - center.y;
        float distance = (float)Math.sqrt(distanceX*distanceX + distanceY*distanceY);

        return (radius >= distance);
    }
}
