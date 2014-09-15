package jp.fujitaya.zunko.jimmy;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.GameScene;

public class FieldMap{
    protected Bitmap background;
    protected ArrayList<TouchableBitmap> fieldButtons;
    protected FieldGroup group;

    public FieldMap(FieldGroup group, Resources res){
        init(group,res);
    }

    private void init(FieldGroup group, Resources res){
        this.group = group;
        fieldButtons = new ArrayList<TouchableBitmap>();

        switch (group){
            case Miyagi:
                background = BitmapFactory.decodeResource(res, R.drawable.map_miyagi);
                fieldButtons.add(new TouchableBitmap(
                        BitmapFactory.decodeResource(res, R.drawable.mig),
                        new RectF(200f,500f,288f,611f),
                        new InsideRectF(new RectF(200f,500f,288f,611f)),
                        new OnClick() {
                            @Override
                            public void onClick() {
                                //TODO:仙台ステージへの遷移
                            }
                        }
                ));
                break;
            case Tohoku:
                break;
            case World:
                break;
            default:
                break;
        }
    }
    public void update(){

    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(background,new Rect(0,0,background.getWidth(),background.getHeight()),
                new RectF(0f,0f,720f,1024f),new Paint());

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
    }

    public ArrayList<TouchableBitmap> getButtons(){
        return fieldButtons;
    }

    public void changeGroup(FieldGroup group, Resources res){
        dispose();
        init(group, res);
    }
}
