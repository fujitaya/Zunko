package jp.fujitaya.zunko.sugaya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Field extends BasicObject {
    Field(Bitmap image){super(image);}
    public void update(){}
    public void init(){}
    public void draw(Canvas canvas){
        canvas.drawBitmap(image,0,0,new Paint());
    }
}
