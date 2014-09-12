package jp.fujitaya.zunko;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

import jp.fujitaya.zunko.jimmy.InsideRectF;
import jp.fujitaya.zunko.jimmy.TouchableBitmap;
import jp.fujitaya.zunko.util.GameScene;

public class SceneMenu extends GameScene {
    Resources res;
    Bitmap menuTitle;
    TouchableBitmap butStart, butExtra, butExit;

    public SceneMenu(Context viewContext){
        super(viewContext);
        init();
    }

    public void init() {
        res = viewContext.getResources();

        menuTitle = BitmapFactory.decodeResource(res,R.drawable.menutitle);
        butStart = (new TouchableBitmap(BitmapFactory.decodeResource(res,R.drawable.startbutton),
                new RectF(250f,800f,550f,900f),this,new InsideRectF(new RectF(250f,800f,550f,900f))));
        butExtra = (new TouchableBitmap(BitmapFactory.decodeResource(res,R.drawable.extrabutton),
                new RectF(250f,1000f,550f,1100f),this,new InsideRectF(new RectF(250f,1000f,550f,1100f))));
        butExit = (new TouchableBitmap(BitmapFactory.decodeResource(res,R.drawable.exitbutton),
                new RectF(250f,1200f,550f,1300f),this,new InsideRectF(new RectF(250f,1200f,550f,1300f))));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(menuTitle, 0, 0, null);
        butStart.draw(canvas);
        butExtra.draw(canvas);
        butExit.draw(canvas);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void interrupt(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            float y = event.getY();

            if (butStart.isInside(new PointF(x,y))){
            }
        }
    }
}
