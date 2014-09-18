package jp.fujitaya.zunko.sugaya;

import android.graphics.PointF;

import java.util.ArrayList;

import jp.fujitaya.zunko.hayashima.Building;
import jp.fujitaya.zunko.hayashima.ChibiZunko;
import jp.fujitaya.zunko.util.Field;
import jp.fujitaya.zunko.util.Image;

public class EndField extends Field {
    PointF vect;
    Image image;
    boolean selectFlag=false;
    String fieldName;

    //list of obj
    ArrayList<Building> listBuilding;
    ArrayList<Creator> listCreator;
    ArrayList<ChibiZunko> listMiniZunko;

    int MAX_CREAT_NUM=100;
    int MAX_POWER_UP_NUM=100;
    int powerUpCount=0;
    int maxFieldHitPoint=0;

    public EndField(Field f){
        super(f.getFieldName());

        vect=new PointF(0,0);
        fieldName=f.getFieldName();
        init();
//        this.listBuilding=f.listBuilding;
//        this.listCreator=f.listCreator;
//        this.listMiniZunko=f.listMiniZunko;
        }


    public void init(){
        setFieldImage();
    }
    public void update(){
        createMiniZunko();

        for(Creator b:listCreator){b.update();}
        for(MiniZunko b:listMiniZunko){b.update();}

    }

    public void dispose(){
        setMiniZunkoAttack();
        super.dispose();
    }
    public void setSumAttakcPower(){
        for(MiniZunko zun:listMiniZunko){
            sumAttackPower+=zun.getActionPoint();
        }
        deleteAllMiniZunko();
    }
    void deleteAllMiniZunko(){
        listMiniZunko.clear();
    }
}
