package jp.fujitaya.zunko.sugaya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public abstract class BasicObject{
    int x,y;
    ArrayList<Bitmap> listImage;
    BasicObject(ArrayList<Bitmap> image){
        x=0;
        y=0;
        this.listImage=image;
    }

    public abstract void update();
    public abstract void init();
    public abstract void draw(Canvas canvas);

}
