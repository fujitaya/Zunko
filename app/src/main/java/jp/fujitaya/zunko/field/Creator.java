package jp.fujitaya.zunko.field;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import jp.fujitaya.zunko.util.ImageLoader;

public class Creator extends FieldBaseObject {
    private Bitmap image;
    private float scale;
    private int spawnTime, spawnCounter;

    public Creator(FieldData.CreatorData data) {
        super();

        image = ImageLoader.getInstance().load(data.imageId);
        scale = data.scale;
        moveTo(data.fieldX, data.fieldY);
        spawnTime = data.spawnTime;
        spawnCounter = 0;
    }

    public boolean isCreatable(){
        return spawnCounter >= spawnTime;
    }
    public void reset(){
        spawnCounter = 0;
    }

    @Override public void update() {
        ++spawnCounter;
    }

    RectF drawRect = new RectF();
    @Override
    public void draw(Canvas canvas, float baseX, float baseY){
        drawRect.left = baseX + pos.x;
        drawRect.top = baseY + pos.y;
        drawRect.right = baseX + pos.x + image.getWidth()*scale;
        drawRect.bottom = baseY + pos.y + image.getHeight()*scale;
        canvas.drawBitmap(image, null, drawRect, null);
    }
}
