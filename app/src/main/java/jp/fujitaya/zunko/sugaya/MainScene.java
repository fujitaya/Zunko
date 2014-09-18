package jp.fujitaya.zunko.sugaya;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Menu;
import android.view.MotionEvent;

import java.util.ArrayList;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.SceneMenu;
import jp.fujitaya.zunko.hayashima.MainMessageWindowScene;
import jp.fujitaya.zunko.hayashima.MenuState;
import jp.fujitaya.zunko.hayashima.MessageWindowScene;
import jp.fujitaya.zunko.jimmy.FieldManager;
import jp.fujitaya.zunko.jimmy.SceneSelect;
import jp.fujitaya.zunko.util.*;

public class MainScene extends GameScene {
    //object list
    String fieldName;
    MainMessageWindowScene message;
    public MainScene(GameView parent,String name){
        super(parent);
        fieldName=name;
        message=new MainMessageWindowScene(parent,fieldName);
        init();
    };


    private void init(){
        FieldManager.getInstance().getField(fieldName).init();
    }

    @Override
    public void update(){
        FieldManager.getInstance().update();
        message.update();

        //changeScene

    }
    @Override
    public void draw(Canvas canvas){

        FieldManager.getInstance().getField(fieldName).draw(canvas);
        message.draw(canvas);
    }

    @Override
    public void interrupt(MotionEvent event) {
        MenuState m=message.getMenuInterrupt(event);
        if(m== MenuState.On){
            //get Zunko attackPoint
            if(FieldManager.getInstance().getField(fieldName).getClass() == (Class<?>)EndField.class){
                //((EndField)(FieldManager.getInstance().getField(fieldName))).interruptOnMenu(event);
            }

        }
        if(m==MenuState.None) {
            FieldManager.getInstance().getField(fieldName).interrupt(event);
        }
    }
    @Override
    public void dispose(){
        FieldManager.getInstance().getField(fieldName).dispose();
    }

    void ChangeScene(){
        parent.changeScene(new SceneSelect(parent));
    }
}
