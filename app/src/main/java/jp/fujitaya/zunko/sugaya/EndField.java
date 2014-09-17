package jp.fujitaya.zunko.sugaya;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Iterator;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.Image;
public class EndField extends Field{
    public EndField(Field f){
        vect=new PointF(0,0);
        fieldName=f.getFieldName();
        init();
        this.listBuilding=f.listBuilding;
        this.listCreator=f.listCreator;
        this.listMiniZunko=f.listMiniZunko;
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
