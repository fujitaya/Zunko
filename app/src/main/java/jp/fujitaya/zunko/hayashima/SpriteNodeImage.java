package jp.fujitaya.zunko.hayashima;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import jp.fujitaya.zunko.util.SpriteNode;

public class SpriteNodeImage extends SpriteNode {
    private Bitmap image;

    public SpriteNodeImage(Bitmap image){
        super();
        this.image = image;
    }

    public int getWidth(){return image.getWidth();}
    public int getHeight(){return image.getHeight();}

    @Override
    protected void drawThis(Canvas canvas, float xbase, float ybase, float xbasescale, float ybasescale){
        int x = translateXbase(xbase, xbasescale);
        int y = translateYbase(ybase, ybasescale);
        canvas.drawBitmap(image, x, y, null);
    }

}
