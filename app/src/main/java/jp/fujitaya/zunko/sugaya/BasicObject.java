package jp.fujitaya.zunko.sugaya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.hayashima.MessageWindowScene;
import jp.fujitaya.zunko.util.Image;

public abstract class BasicObject{
    PointF vect=new PointF(0,0);
    Image image;
    int tatchSize=0;
    //draw
    PointF imageSize=new PointF(0,0);
    BasicObject(/*ArrayList<Bitmap> image*/){
        //this.listImage=image;
    }
    BasicObject(/*ArrayList<Bitmap> image,*/PointF v){
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
