package jp.fujitaya.zunko.sugaya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;

public abstract class BasicObject{
    Point vect;
    ArrayList<Bitmap> listImage;
    int tatchSize;
    BasicObject(ArrayList<Bitmap> image){
        this.listImage=image;
        vect=new Point(0,0);
        tatchSize=0;
    }
    BasicObject(ArrayList<Bitmap> image,Point v){
        this(image);
        vect=v;
    }

    public abstract void update();
    public abstract void init();
    public abstract void draw(Canvas canvas);
    public Point getVect(){return vect;}
    public boolean isHit(Point v) {
        double q=Math.sqrt((vect.x-v.x)*(vect.x-v.x)+(vect.y-v.y)*(vect.y-v.y));
        return tatchSize>q;
    }

}
