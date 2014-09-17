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
import jp.fujitaya.zunko.SceneMenu;
import jp.fujitaya.zunko.jimmy.FieldManager;
import jp.fujitaya.zunko.jimmy.SceneSelect;
import jp.fujitaya.zunko.util.*;

public class MainScene extends GameScene {
    //object list
    Field field;
    String fieldName;
    public MainScene(GameView parent,String name){
        super(parent);
        fieldName=name;
        init();
    }


    private void init(){
        field= FieldManager.getInstance().getField(fieldName);
    }

    @Override
    public void update(){
        FieldManager.getInstance().update();

        //changeScene
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
    public void dispose(){
        field.disposeImage();
    }

    void ChangeScene(){
        parent.changeScene(new SceneSelect(parent));
    }



}
