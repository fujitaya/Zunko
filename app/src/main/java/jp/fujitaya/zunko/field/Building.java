package jp.fujitaya.zunko.field;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import jp.fujitaya.zunko.util.ImageLoader;

public class Building extends FieldBaseObject {
    private float scale;
    private Bitmap image;

    Building(FieldData.BuildingData data){
        super();

        image = ImageLoader.getInstance().load(data.imageId);
        scale = data.scale;
        setHP(data.hp);
        moveTo(data.fieldX, data.fieldY);
        setCollision(new RectF(0, 0, image.getWidth()*scale, image.getHeight()*scale));
    }

    @Override
    public void update(){
        if(hp <= 0){

        }
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
