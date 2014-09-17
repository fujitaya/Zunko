package jp.fujitaya.zunko.jimmy;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.sugaya.MainScene;
import jp.fujitaya.zunko.util.GameView;

import static android.view.GestureDetector.OnGestureListener;

public class StageMap {
    protected Bitmap background;
    protected ArrayList<TouchableBitmap> fieldButtons;
    protected StageGroup group;
    //本当はコールバックに変えたい
    protected GameView parentView;
    protected Rect backGroundSrc;
    protected RectF backGroundDst;
    protected PointF backGroundOffset;

    public StageMap(StageGroup group, Resources res, GameView parentView){
        init(group,res,parentView);
    }

    private void init(StageGroup group, Resources res, final GameView parentView){
        this.group = group;
        fieldButtons = new ArrayList<TouchableBitmap>();
        this.parentView = parentView;

        switch (group){
            case Miyagi:
                background = BitmapFactory.decodeResource(res, R.drawable.bg_tohoku);
                backGroundSrc = new Rect(1100,1600,2000,3300);
                backGroundOffset = new PointF(-50,-100);
                backGroundDst = new RectF(backGroundOffset.x,
                        backGroundOffset.y,
                        backGroundOffset.x+backGroundSrc.width(),
                        backGroundOffset.y+backGroundSrc.height());
                fieldButtons.add(new TouchableBitmap(
                        BitmapFactory.decodeResource(res, R.drawable.mc_mig),
                        new RectF(400f,600f,500f,700f),
                        new InsideRectF(new RectF(200f,500f,288f,611f)),
                        new OnGestureListener() {
                            @Override
                            public boolean onDown(MotionEvent motionEvent) {
                                return false;
                            }
                            @Override
                            public void onShowPress(MotionEvent motionEvent) { }
                            @Override
                            public boolean onSingleTapUp(MotionEvent motionEvent) {
                                parentView.changeScene(new MainScene(parentView,"Sendai"));
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
                        }
                ));
                break;
            case Tohoku:
                background = BitmapFactory.decodeResource(res, R.drawable.map_miyagi);
                break;
            case World:
                background = BitmapFactory.decodeResource(res, R.drawable.map_miyagi);
                break;
            default:
                break;
        }
    }
    public void update(){

    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(background,backGroundSrc,backGroundDst,null);

        for (TouchableBitmap button : fieldButtons){
            button.draw(canvas);
        }
    }
    public void dispose(){
        if (background != null){
            background.recycle();
            background = null;
        }
        for (TouchableBitmap button : fieldButtons){
            button.dispose();
        }
        fieldButtons.clear();
        parentView = null;
    }

    public ArrayList<TouchableBitmap> getButtons(){
        return fieldButtons;
    }

    public void changeGroup(StageGroup group, Resources res, GameView parentView){
        dispose();
        init(group, res, parentView);
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
        move(distanceX,distanceY);
        return true;
    }

    private void move(float distanceX, float distanceY){
        float moveX=0f,moveY=0f;
        if (backGroundOffset.x - distanceX + backGroundSrc.width() > GameView.VIEW_WIDTH &&
                backGroundOffset.x - distanceX < 0)
            moveX = distanceX;
        if (backGroundOffset.y - distanceY + backGroundSrc.height() > GameView.VIEW_HEIGHT &&
                backGroundOffset.y - distanceY < 0)
            moveY = distanceY;

        if (moveX != 0f || moveY != 0f){
            backGroundOffset.x -= moveX;
            backGroundOffset.y -= moveY;
            for (TouchableBitmap button : fieldButtons)
                button.move(-moveX,-moveY);

            backGroundDst = new RectF(backGroundOffset.x,
                    backGroundOffset.y,
                    backGroundOffset.x+backGroundSrc.width(),
                    backGroundOffset.y+backGroundSrc.height());
        }
    }
}
