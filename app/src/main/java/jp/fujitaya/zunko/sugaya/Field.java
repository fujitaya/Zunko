package jp.fujitaya.zunko.sugaya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class Field extends BasicObject {
    Field(ArrayList<Bitmap> image){super(image);}

    @Override public void update(){}
    @Override public void init(){}
    @Override public void draw(Canvas canvas){
        canvas.drawBitmap(listImage.get(0),x,y,new Paint());
    }
}
