package jp.fujitaya.zunko;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import jp.fujitaya.zunko.util.GameScene;

public class SceneMenu extends GameScene {
    Context viewContext;
    Resources res;
    Bitmap menuTitle;

    public SceneMenu(Context viewContext){
        super(viewContext);
    }

    public void init() {
        res = viewContext.getResources();
        menuTitle = BitmapFactory.decodeResource(res,R.drawable.menutitle);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(menuTitle, 0, 0, null);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void interrupt(MotionEvent event) {

    }
}
