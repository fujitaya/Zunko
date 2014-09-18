package jp.fujitaya.zunko.hayashima;

import android.graphics.Canvas;
import android.graphics.PointF;

import jp.fujitaya.zunko.util.Image;

public abstract class FieldBaseObject {
    PointF vect=new PointF(0,0);
    Image image;
    int tatchSize=0;
    //draw
    PointF imageSize=new PointF(0,0);
    FieldBaseObject(/*ArrayList<Bitmap> image*/){
        //this.listImage=image;
    }
    FieldBaseObject(/*ArrayList<Bitmap> image,*/PointF v){
        this(/*image*/);
        vect=v;
    }

    public abstract void update();
    public abstract void init();
    public abstract void draw(Canvas canvas);
    public abstract void disposeImage();
    public PointF getVect(){return vect;}
    public boolean isHit(PointF v) {
        double q=Math.sqrt((vect.x-v.x)*(vect.x-v.x)+(vect.y-v.y)*(vect.y-v.y));
        return tatchSize>q;
    }

}
