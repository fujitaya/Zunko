package jp.fujitaya.zunko.scene;

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

import jp.fujitaya.zunko.GameView;
import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.InsideRectF;
import jp.fujitaya.zunko.util.TouchableBitmap;

import static android.view.GestureDetector.OnGestureListener;

public class SceneMenu extends GameScene implements OnGestureListener{
    private Bitmap menuTitle;
    private ArrayList<TouchableBitmap> buttons;
    private GestureDetector gestureDetector;
    private Bitmap background;

    public SceneMenu(GameView parent){
        super(parent);
        init();
    }

    public void init() {
        Resources res = parent.getContext().getResources();
        buttons = new ArrayList<TouchableBitmap>();

        //背景画像
        background = BitmapFactory.decodeResource(res, R.drawable.bg_title);
        //タイトル画像
        menuTitle = BitmapFactory.decodeResource(res,R.drawable.menutitle);
        //スタートボタン
        buttons.add(new TouchableBitmap(BitmapFactory.decodeResource(res,R.drawable.btn_start),
                new RectF(200f,800f,520f,900f),
                new OnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent motionEvent) {
                        return false;
                    }
                    @Override
                    public void onShowPress(MotionEvent motionEvent) { }
                    @Override
                    public boolean onSingleTapUp(MotionEvent motionEvent) {
                        parent.changeScene(new SceneSelect(parent));
                        return false;
                    }
                    @Override
                    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                        return false;
                    }
                    @Override
                    public void onLongPress(MotionEvent motionEvent) {
                    }
                    @Override
                    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                        return false;
                    }
                }));
        //図鑑ボタン
        buttons.add((new TouchableBitmap(BitmapFactory.decodeResource(res,R.drawable.btn_config),
                new RectF(200f,1000f,520f,1100f),
                new InsideRectF(new RectF(250f,1000f,550f,1100f)),
                new OnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent motionEvent) {
                        return false;
                    }
                    @Override
                    public void onShowPress(MotionEvent motionEvent) { }
                    @Override
                    public boolean onSingleTapUp(MotionEvent motionEvent) {
                        //TODO:設定画面への遷移
                        return false;
                    }
                    @Override
                    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                        return false;
                    }
                    @Override
                    public void onLongPress(MotionEvent motionEvent) { }
                    @Override
                    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                        return false;
                    }
                })));
        //タッチジェスチャ監視
        gestureDetector = new GestureDetector(parent.getContext(), this);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        //背景描画
        canvas.drawBitmap(background, null,
                new Rect(0, 0, 720, 1280), null);
        //タイトル描画
        //canvas.drawBitmap(menuTitle, 0, 0, null);

        for (TouchableBitmap button : buttons){
            button.draw(canvas);
        }
    }

    @Override
    public void dispose() {
        if (background != null){
            background.recycle();
            background = null;
        }
        if (menuTitle != null){
            menuTitle.recycle();
            menuTitle = null;
        }
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
        float x = e.getX();
        float y = e.getY();

        for (TouchableBitmap button : buttons){
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

        for (TouchableBitmap button : buttons){
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

        for (TouchableBitmap button : buttons){
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

        for (TouchableBitmap button : buttons){
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
