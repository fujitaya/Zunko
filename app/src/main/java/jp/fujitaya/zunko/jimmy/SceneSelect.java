package jp.fujitaya.zunko.jimmy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.MotionEvent;

import jp.fujitaya.zunko.GameActivity;
import jp.fujitaya.zunko.util.GameScene;
import jp.fujitaya.zunko.util.GameView;

import static android.view.GestureDetector.OnGestureListener;

public class SceneSelect extends GameScene implements OnGestureListener{
    FieldMap map;
    GestureDetector gestureDetector;

    public SceneSelect(GameActivity parent){
        super(parent);
        init();
    }

    public void init(){
        map = new FieldMap(FieldGroup.Miyagi, parent.getResources(), parent);
        gestureDetector = new GestureDetector(parent.getWindow().getContext(), this);
    }

    @Override
    public void update() {
        map.update();
    }

    @Override
    public void draw(Canvas canvas) {
        map.draw(canvas);
    }

    @Override
    public void dispose() {
        map.dispose();
        gestureDetector = null;
        parent = null;
    }

    @Override
    public void interrupt(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        for (TouchableBitmap button : map.getButtons()){
            if (button.isInside(new PointF(x,y))){
                button.getGestureListener().onDown(e);
                return false;
            }
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        for (TouchableBitmap button : map.getButtons()){
            if (button.isInside(new PointF(x,y))){
                button.getGestureListener().onShowPress(e);
                return;
            }
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        for (TouchableBitmap button : map.getButtons()){
            if (button.isInside(new PointF(x,y))){
                button.getGestureListener().onSingleTapUp(e);
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
        float x = e.getX();
        float y = e.getY();

        for (TouchableBitmap button : map.getButtons()){
            if (button.isInside(new PointF(x,y))){
                button.getGestureListener().onLongPress(e);
                return;
            }
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
