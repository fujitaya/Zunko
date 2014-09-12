package jp.fujitaya.zunko.sugaya;

import android.graphics.Canvas;

import java.util.ArrayList;

import jp.fujitaya.zunko.util.*;

public class MainScene extends GameScene {

    //object list
    ArrayList<Field> listField;
    ArrayList<MiniZunko> listMiniZunko;
    ArrayList<Icon> listIcon;


    public void init(){
        //create lists
        listField=new ArrayList<Field>();
        listMiniZunko=new ArrayList<MiniZunko>();
        listIcon=new ArrayList<Icon>();

        //create temp object
        //listField.add(new Field());

    }


    public void update(){
        //list update
        for(int i=0;i<listField.size();i++){
           // listField.get(i).update();
        }
        for(int i=0;i<listMiniZunko.size();i++){
            //listField.get(i).update();
        }
        for(int i=0;i<listMiniZunko.size();i++){
            //listField.get(i).update();
        }
    }

    public void draw(Canvas canvas){


    }
    public void dispose(){}

}
