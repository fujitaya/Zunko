package jp.fujitaya.zunko.sugaya;

import android.graphics.Canvas;
import android.graphics.PointF;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.Image;

public class Creator extends BasicObject {
    Image image;
    static int staticCreateNumber = 0;
    int createNumber = 0;
    String buildingName;
    int createCount;
    int START_CREATE_COUNT = 60 * 5;

    Creator(/*ArrayList<Bitmap> image, */PointF v) {
        super(/*image,*/ v);
        image=new Image(R.drawable.ic_launcher);
        image.setCenter();
        image.setPosition(vect);

        buildingName = "None";
        tatchSize=(int)Math.sqrt(image.getWidth()*image.getWidth()+image.getHeight()*image.getHeight());
        createCount = START_CREATE_COUNT;//temp create time 5s
        createNumber = staticCreateNumber;
        staticCreateNumber++;

    }

    void setCreateCount() {
        createCount--;
        if (createCount < 0) {
            createCount = START_CREATE_COUNT;
        }
    }

    public int getCreateCount() {
        return createCount;
    }

    public int getCreateNumber() {
        return createNumber;
    }

    public void resetCreateNumber() {
        createNumber = 0;
    }

    @Override
    public void init() {
    }

    @Override
    public void update() {

        setCreateCount();
    }

    @Override
    public void draw(Canvas canvas) {
        image.draw(canvas);
    }
    @Override public void disposeImage(){
        image=null;
    }
}
