package jp.fujitaya.zunko.hayashima;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.GameScene;
import jp.fujitaya.zunko.util.GameView;
import jp.fujitaya.zunko.util.PointerInfo;
import jp.fujitaya.zunko.util.SpriteNodeImage;

public class TitleScene extends GameScene {
    SpriteNodeImage bg;
    Rect btn1, btn2, btn3;

    public TitleScene(GameView parent){
        super(parent);

        bg = new SpriteNodeImage(
                BitmapFactory.decodeResource(parent.getResources(),
                R.drawable.title));

        btn1 = new Rect();
        btn2 = new Rect();
        btn3 = new Rect();
        btn1.set(203, 590, 480, 670);
        btn2.set(207, 726, 484, 800);
        btn3.set(210, 864, 484, 936);
    }

    @Override
    public void dispose(){
        bg.changeImage(null).recycle();
    }

    @Override
    public void update(){
    }

    PointerInfo pi = new PointerInfo();
    @Override
    public void interrupt(MotionEvent event){
        pi.update(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(btn1.contains((int)pi.x, (int)pi.y)){
                    parent.changeScene(new MessageWindowScene(parent));
                }else if(btn2.contains((int)pi.x, (int)pi.y)){

                }else if(btn3.contains((int)pi.x, (int)pi.y)){
                    parent.changeScene(null);
                }
                break;
            default: break;
        }
    }

    @Override
    public void draw(Canvas canvas){
        bg.draw(canvas, 0, 0, 1, 1, 0);
    }
}
