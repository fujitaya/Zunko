package jp.fujitaya.zunko.jimmy;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

    public StageMap(StageGroup group, Resources res, GameView parentView){
        init(group,res,parentView);
    }

    private void init(StageGroup group, Resources res, final GameView parentView){
        this.group = group;
        fieldButtons = new ArrayList<TouchableBitmap>();
        this.parentView = parentView;

        switch (group){
            case Miyagi:
                background = BitmapFactory.decodeResource(res, R.drawable.map_miyagi);
                fieldButtons.add(new TouchableBitmap(
                        BitmapFactory.decodeResource(res, R.drawable.mc_mig),
                        new RectF(200f,500f,300f,600f),
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
        canvas.drawBitmap(background,null,
                new RectF(0f,0f,720f,1024f),null);

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
}
