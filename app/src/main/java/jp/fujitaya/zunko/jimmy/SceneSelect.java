package jp.fujitaya.zunko.jimmy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.MotionEvent;

import jp.fujitaya.zunko.hayashima.MessageWindowScene;
import jp.fujitaya.zunko.sugaya.Field;
import jp.fujitaya.zunko.util.GameScene;
import jp.fujitaya.zunko.util.GameView;

import static android.view.GestureDetector.OnGestureListener;

public class SceneSelect extends GameScene implements OnGestureListener{
    FieldMap map;
    GestureDetector gestureDetector;
    FieldManager fieldManager;
    MessageWindowScene message;

    public SceneSelect(GameView parent){
        super(parent);
        this.fieldManager = FieldManager.getInstance();
        message = new MessageWindowScene(parent);
        init();
    }

    public void init(){
        map = new FieldMap(FieldGroup.Miyagi, parent.getResources(), parent);
        gestureDetector = new GestureDetector(parent.getContext(), this);
    }

    @Override
    public void update() {
        map.update();
        fieldManager.update();
        message.update();
    }

    @Override
    public void draw(Canvas canvas) {
        map.draw(canvas);
        message.draw(canvas);
    }

    @Override
    public void dispose() {
        map.dispose();
        gestureDetector = null;
        parent = null;
        fieldManager = null;
    }

    @Override
    public void interrupt(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        message.interrupt(event);
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
