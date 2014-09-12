package jp.fujitaya.zunko;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import jp.fujitaya.zunko.util.GameScene;

public class SceneMenu extends GameScene {
    Context viewContext;
    Resources res;
    Bitmap menuTitle;

<<<<<<< HEAD
    public SceneMenu(Context viewContext){
        super(viewContext);
    }

    public void init() {
        res = viewContext.getResources();
        menuTitle = BitmapFactory.decodeResource(res,R.drawable.menutitle);
    }

=======
>>>>>>> origin/master
    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(menuTitle,0,0,new Paint());
    }

    @Override
    public void dispose() {

    }
}
