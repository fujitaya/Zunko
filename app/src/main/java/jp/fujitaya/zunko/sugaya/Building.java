package jp.fujitaya.zunko.sugaya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;

public class Building extends BasicObject {
    String buildingName;
    int hitPoint;
    int createCount;
    static int START_CREATE_COUNT=60*5;
    boolean zunkoCreatorFlag;
    Building(ArrayList<Bitmap> image,Point v){
        super(image,v);
        buildingName="None";
        tatchSize=25;//temp size 25
        hitPoint=60*30;//temp hit point 30s
        zunkoCreatorFlag=false;
        createCount=START_CREATE_COUNT;//temp create time 5s

    }
    public int getHitPoint(){return hitPoint;}
    public void setHitPoint(int h){hitPoint=h;}
    void setZunkoCreatorFlag(){
        if(hitPoint<=0){
            zunkoCreatorFlag=true;
        }
    }
    boolean isZunkoCreatorFlag(){return  zunkoCreatorFlag;}
    void setCreateCount(){
        if(zunkoCreatorFlag==true){
            createCount--;
            if(createCount<0){
                createCount=START_CREATE_COUNT;
            }
        }
    }
    public boolean isNowZunkoCreate(){
        return zunkoCreatorFlag==true && createCount==0;
    }
    @Override public void init(){}
    @Override public void update(){

        setZunkoCreatorFlag();
        setCreateCount();
    }
    @Override public void draw(Canvas canvas){
        if(zunkoCreatorFlag==false){
            canvas.drawBitmap(listImage.get(0),vect.x,vect.y,new Paint());
        }
        else if(zunkoCreatorFlag==true){
            canvas.drawBitmap(listImage.get(1),vect.x,vect.y,new Paint());
        }
    }

}