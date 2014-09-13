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
    ArrayList<Field> listField;
    ArrayList<MiniZunko> listMiniZunko;
    ArrayList<Icon> listIcon;

    public MainScene(GameView parent){
        super(parent);
        init();
    }
    private void init(){
        res = parent.getContext().getResources();
        //create lists
        listField=new ArrayList<Field>();
        listMiniZunko=new ArrayList<MiniZunko>();
        listIcon=new ArrayList<Icon>();

        //Get bitmap data
        ArrayList<Bitmap> tempf=new ArrayList<Bitmap>();
        tempf.add(BitmapFactory.decodeResource(res, R.drawable.menutitle));
        listField.add(new Field(tempf));

        ArrayList<Bitmap> tempm=new ArrayList<Bitmap>();
        tempm.add(BitmapFactory.decodeResource(res, R.drawable.ic_launcher));
        tempm.add(BitmapFactory.decodeResource(res, R.drawable.ic_launcher));
        listMiniZunko.add(new MiniZunko(tempm));





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
            listMiniZunko.get(i).update();
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
        for(int i=0;i<listMiniZunko.size();i++){
            listMiniZunko.get(i).draw(canvas);
        }
    }

    @Override
    public void interrupt(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < listMiniZunko.size(); i++) {
                listMiniZunko.get(i).tatchToMove((int) event.getX(), (int) event.getY());
            }
        }
    }
    @Override
    public void dispose(){}

}
