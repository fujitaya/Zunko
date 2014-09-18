package jp.fujitaya.zunko.scene;

import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.MotionEvent;

import jp.fujitaya.zunko.GameView;
import jp.fujitaya.zunko.field.FieldManager;

import static android.view.GestureDetector.OnGestureListener;

public class SceneSelect extends GameScene implements OnGestureListener{
    StageMap map;
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
        map = new StageMap(StageGroup.Miyagi, parent.getResources(), parent);
        gestureDetector = new GestureDetector(parent.getContext(), this);
    }

    @Override
    public void update() {
        synchronized (map) {
            map.update();
        }
        fieldManager.update();
        message.update();
    }

    @Override
    public void draw(Canvas canvas) {
        synchronized (map) {
            map.draw(canvas);
        }
        message.draw(canvas);
    }

    @Override
    public void dispose() {
//        synchronized (map) {
            map.dispose();
            gestureDetector = null;
            parent = null;
            fieldManager = null;
//        }
    }

    @Override
    public void interrupt(MotionEvent event) {
        synchronized (map) {
            gestureDetector.onTouchEvent(event);
        }
        message.interrupt(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        map.onDown(e);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        map.onShowPress(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        map.onSingleTapUp(e);
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (map != null) map.onScroll(e1, e2, distanceX, distanceY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        map.onLongPress(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        map.onFling(e1, e2, velocityX, velocityY);
        return false;
    }
}
