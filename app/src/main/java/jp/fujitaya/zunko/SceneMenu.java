package jp.fujitaya.zunko;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.ArrayList;

import jp.fujitaya.zunko.jimmy.InsideRectF;
import jp.fujitaya.zunko.jimmy.OnClick;
import jp.fujitaya.zunko.jimmy.SceneSelect;
import jp.fujitaya.zunko.jimmy.TouchableBitmap;
import jp.fujitaya.zunko.util.GameScene;
import jp.fujitaya.zunko.util.GameView;

public class SceneMenu extends GameScene implements GestureDetector.OnGestureListener{
    private Bitmap menuTitle;
    private ArrayList<TouchableBitmap> buttons;
    private GestureDetector gestureDetector;

    public SceneMenu(GameView parent){
        super(parent);
        init();
    }

    public void init() {
        Resources res = parent.getContext().getResources();
        buttons = new ArrayList<TouchableBitmap>();

        //タイトル画像
        menuTitle = BitmapFactory.decodeResource(res,R.drawable.menutitle);
        //スタートボタン
        buttons.add(new TouchableBitmap(BitmapFactory.decodeResource(res,R.drawable.startbutton),
                new RectF(250f,800f,550f,900f),
                new InsideRectF(new RectF(250f,800f,550f,900f)),
                new OnClick(){
                    @Override
                    public void onClick() {
                        parent.changeScene(new SceneSelect(parent));
                    }
                }));
        //図鑑ボタン
        buttons.add((new TouchableBitmap(BitmapFactory.decodeResource(res,R.drawable.extrabutton),
                new RectF(250f,1000f,550f,1100f),
                new InsideRectF(new RectF(250f,1000f,550f,1100f)),
                new OnClick() {
                    @Override
                    public void onClick() {
                        //TODO;図鑑への移動
                    }
                })));

        gestureDetector = new GestureDetector(parent.getContext(), this);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(menuTitle, 0, 0, null);

        for (TouchableBitmap button : buttons){
            button.draw(canvas);
        }
    }

    @Override
    public void dispose() {
        for (TouchableBitmap button : buttons){
            button.dispose();
        }
        gestureDetector = null;
        parent = null;
    }

    @Override
    public void interrupt(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        for (TouchableBitmap button : buttons){
            if (button.isInside(new PointF(x,y))){
                button.onClick();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
