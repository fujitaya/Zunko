package jp.fujitaya.zunko.sugaya;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Object {
    int x,y;
    Bitmap image;
    Object(Bitmap image){
        this.image=image;
    }

    public abstract void update();
    public abstract void init();
    public abstract void draw(Canvas canvas);

}
