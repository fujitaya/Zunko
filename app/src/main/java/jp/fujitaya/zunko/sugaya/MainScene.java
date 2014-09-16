package jp.fujitaya.zunko.sugaya;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.*;

public class MainScene extends GameScene {
    Resources res;

    //object list
    Field field;

    public MainScene(GameView parent){
        super(parent);
        res = parent.getContext().getResources();
        init();
    }

    private void init(){
        //res = parent.getContext().getResources();
        //create lists
        ArrayList<Bitmap> tempf=new ArrayList<Bitmap>();
        tempf.add(BitmapFactory.decodeResource(res, R.drawable.green_field));
        field=new Field(tempf,res);

    }

    @Override
    public void update(){
        field.update();
    }
    @Override
    public void draw(Canvas canvas){
        field.draw(canvas);
    }

    @Override
    public void interrupt(MotionEvent event) {
        field.interrupt(event);
    }
    @Override
    public void dispose(){}

}
