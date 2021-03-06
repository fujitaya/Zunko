package jp.fujitaya.zunko.scene;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.text.NumberFormat;

import jp.fujitaya.zunko.GameView;
import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.field.FieldManager;
import jp.fujitaya.zunko.field.zunko.StatusAll;
import jp.fujitaya.zunko.util.InsideRectF;
import jp.fujitaya.zunko.util.Sound;
import jp.fujitaya.zunko.util.TouchableBitmap;
import jp.fujitaya.zunko.util.TouchableBitmapWithText;

import static android.view.GestureDetector.OnGestureListener;

public class SceneSelect extends GameScene implements OnGestureListener{
    public final RectF statusBar = new RectF(0f,0f,GameView.VIEW_WIDTH,100f);
    public final int statusBarColor = Color.BLACK;
    public final int statusBarAlpha = 180;
    private StageMap map;
    private GestureDetector gestureDetector;
    private FieldManager fieldManager;
    private MessageWindowScene message;
    private TouchableBitmap zunkobutton;
    private NoticeWindow statusWindow;
    private TouchableBitmap zukanButton;
    private boolean isStatusWindowOpen;

    public SceneSelect(GameView parent){
        super(parent);
        this.fieldManager = FieldManager.getInstance();
        message = new MessageWindowScene(parent);
        init();
    }

    public void init(){
        map = new StageMap(StageGroup.Miyagi, parent.getResources(), parent);
        gestureDetector = new GestureDetector(parent.getContext(), this);
        zunkobutton = new TouchableBitmap(
                null,
                //BitmapFactory.decodeResource(parent.getResources(), R.drawable.bg_dummy),
                new RectF(0f,900f,200f,1280f),
                new OnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent motionEvent) {
                        return false;
                    }
                    @Override
                    public void onShowPress(MotionEvent motionEvent) {
                    }
                    @Override
                    public boolean onSingleTapUp(MotionEvent motionEvent) {
                        openStatusWindow();
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
                }
        );

        statusWindow = new NoticeWindow(new RectF(50f,340f,670f,940f),
                "",
                Color.GRAY,250,Color.BLACK);
        zukanButton = new TouchableBitmap(
                BitmapFactory.decodeResource(parent.getResources(), R.drawable.btn_zukan),
                new RectF(260f,800f,460f,900f),
                new OnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent motionEvent) {
                        return false;
                    }
                    @Override
                    public void onShowPress(MotionEvent motionEvent) {
                    }
                    @Override
                    public boolean onSingleTapUp(MotionEvent motionEvent) {
                        //TODO:図鑑画面への遷移
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
                }
        );
        isStatusWindowOpen = false;
        message.show(true);
        message.appendMessage("ステージを選択してください");
    }

    public synchronized void openStatusWindow(){
        isStatusWindowOpen = true;
    }
    public synchronized void closeStatusWindow(){
        isStatusWindowOpen = false;
    }

    @Override
    public void update() {
        map.update();
        fieldManager.updateToEndField();
        fieldManager.update();
        message.update();
        if (isStatusWindowOpen){
            StatusAll status = fieldManager.getStatusAll();

            statusWindow.setText("総合進捗\n" +
                            "\nちびずん子の数          " + Long.toString(status.zunkoAll) +
                            "\nクリアしたステージ  " + Integer.toString(status.cleared) +
                            "\n総進行度\t                      " + NumberFormat.getPercentInstance().format(status.progress)
            );
        }
    }

    @Override
    public void draw(Canvas canvas) {
        map.draw(canvas);
        message.draw(canvas);
        //zunkobutton.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(statusBarColor);
        paint.setAlpha(statusBarAlpha);
        canvas.drawRect(statusBar,paint);

        paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        canvas.drawText("宮城周辺",30,80,paint);

        if (isStatusWindowOpen){
            statusWindow.draw(canvas);
            zukanButton.draw(canvas);
        }
    }

    @Override
    public void dispose() {
        map.dispose();
        gestureDetector = null;
        parent = null;
        fieldManager = null;
        statusWindow.dispose();
        statusWindow = null;
        zunkobutton.dispose();
        zunkobutton = null;
        zukanButton.dispose();
        zukanButton = null;
        message = null;
    }

    @Override
    public void interrupt(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        if (message != null) message.interrupt(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (isStatusWindowOpen){

        }
        else {
            map.onDown(e);
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        if(isStatusWindowOpen){

        }else {
            map.onShowPress(e);
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        if (isStatusWindowOpen){
            if (zukanButton.isInside(new PointF(x,y))) {
                zukanButton.getGestureListener().onSingleTapUp(e);
            } else {
                closeStatusWindow();
            }
        } else {
            if (zunkobutton.isInside(new PointF(x,y))){
                zunkobutton.getGestureListener().onSingleTapUp(e);
            } else {
                map.onSingleTapUp(e);
            }
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (isStatusWindowOpen){

        } else {
            if (map != null) map.onScroll(e1, e2, distanceX, distanceY);
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        if (isStatusWindowOpen){

        } else {
            map.onLongPress(e);
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (isStatusWindowOpen){

        } else {
            map.onFling(e1, e2, velocityX, velocityY);
        }
        return false;
    }
}
