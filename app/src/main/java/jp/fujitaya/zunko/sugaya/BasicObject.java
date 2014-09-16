package jp.fujitaya.zunko.sugaya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;

public abstract class BasicObject{
    PointF vect;
    ArrayList<Bitmap> listImage;
    int tatchSize;
    //draw
    Point imageSize;
    BasicObject(ArrayList<Bitmap> image){
        this.listImage=image;
        vect=new PointF(0,0);
        tatchSize=0;
        imageSize=new Point(image.get(0).getWidth(),image.get(0).getHeight());
    }
    BasicObject(ArrayList<Bitmap> image,PointF v){
        this(image);
        vect=v;
    }

    public abstract void update();
    public abstract void init();
    public abstract void draw(Canvas canvas);
    public PointF getVect(){return vect;}
    public boolean isHit(PointF v) {
        double q=Math.sqrt((vect.x-v.x)*(vect.x-v.x)+(vect.y-v.y)*(vect.y-v.y));
        return tatchSize>q;
    }

}
