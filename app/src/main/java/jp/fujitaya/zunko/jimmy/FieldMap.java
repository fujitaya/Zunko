package jp.fujitaya.zunko.jimmy;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

import jp.fujitaya.zunko.GameActivity;
import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.sugaya.MainScene;
import jp.fujitaya.zunko.util.GameView;

import static android.view.GestureDetector.OnGestureListener;

public class FieldMap{
    protected Bitmap background;
    protected ArrayList<TouchableBitmap> fieldButtons;
    protected FieldGroup group;
    //本当はコールバックに変えたい
    protected GameActivity parentActivity;

    public FieldMap(FieldGroup group, Resources res, GameActivity parentActivity){
        init(group,res, parentActivity);
    }

    private void init(FieldGroup group, Resources res, final GameActivity parentActivity){
        this.group = group;
        fieldButtons = new ArrayList<TouchableBitmap>();
        this.parentActivity = parentActivity;

        switch (group){
            case Miyagi:
                background = BitmapFactory.decodeResource(res, R.drawable.map_miyagi);
                fieldButtons.add(new TouchableBitmap(
                        BitmapFactory.decodeResource(res, R.drawable.mig),
                        new RectF(200f,500f,288f,611f),
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
                                parentActivity.changeScene(new MainScene(parentActivity));
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
        parentActivity = null;
    }

    public ArrayList<TouchableBitmap> getButtons(){
        return fieldButtons;
    }

    public void changeGroup(FieldGroup group, Resources res, GameActivity parentActivity){
        dispose();
        init(group, res,  parentActivity);
    }
}
