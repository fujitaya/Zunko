package jp.fujitaya.zunko.sugaya;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.*;

public class MainScene extends GameScene {
    Resources res;

    //object list
    ArrayList<Field> listField;
    ArrayList<MiniZunko> listMiniZunko;
    ArrayList<Icon> listIcon;

    public MainScene(Context viewContext){
        super(viewContext);
        init();
    }
    public void init(){
        res = viewContext.getResources();
        //create lists
        listField=new ArrayList<Field>();
        listMiniZunko=new ArrayList<MiniZunko>();
        listIcon=new ArrayList<Icon>();

        listField.add(new Field(BitmapFactory.decodeResource(res, R.drawable.menutitle)));
        //create temp object
        //listField.add(new Field());

    }

    @Override
    public void update(){
        //list update
        for(int i=0;i<listField.size();i++){
           listField.get(i).update();
        }
        for(int i=0;i<listMiniZunko.size();i++){
            //listField.get(i).update();
        }
        for(int i=0;i<listMiniZunko.size();i++){
            //listField.get(i).update();
        }
    }
    @Override
    public void draw(Canvas canvas){
        for(int i=0;i<listField.size();i++){
            listField.get(i).draw(canvas);
        }


    }

    @Override
    public void interrupt(MotionEvent event){}
    @Override
    public void dispose(){}

}
