package jp.fujitaya.zunko.sugaya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;

public class Building extends BasicObject {
    static int staticCreateNumber=0;
    int createNumber=0;
    String buildingName;
    int hitPoint;
    static int START_HIT_POINT=60*3;
    int createCount;
    static int START_CREATE_COUNT=60*5;

    boolean zunkoCreatorFlag;
    Building(ArrayList<Bitmap> image,PointF v){
        super(image,v);
        buildingName="None";
        tatchSize=(int)Math.sqrt(image.get(0).getWidth()*image.get(0).getWidth()+image.get(0).getHeight()*image.get(0).getHeight());
        hitPoint=START_HIT_POINT;
        zunkoCreatorFlag=false;
        createCount=START_CREATE_COUNT;//temp create time 5s
        createNumber=staticCreateNumber;
        staticCreateNumber++;

    }
    public int getHitPoint(){return hitPoint;}
    public void setHitPoint(int h){hitPoint=h;}
    public void DecHitPoint(int power){hitPoint-=power;}

    public void setZunkoCreatorFlag(boolean flag){
            zunkoCreatorFlag=flag;
    }

    void setCreateCount(){
        if(zunkoCreatorFlag==true){
            createCount--;
            if(createCount<0){
                createCount=START_CREATE_COUNT;
            }
        }
    }
    public boolean isZunkoCreator(){return zunkoCreatorFlag;}
    public boolean isNowZunkoCreate(){
        return zunkoCreatorFlag==true && createCount==0;
    }
    public int getCreateNumber(){return createNumber;}
    public void resetCreateNumber(){createNumber=0;}
    @Override public void init(){
    }
    @Override public void update(){

        setCreateCount();
    }
    @Override public void draw(Canvas canvas){
        if(zunkoCreatorFlag==false){
            canvas.drawBitmap(listImage.get(0), vect.x - imageSize.x / 2, vect.y - imageSize.y / 2,null);
        }
        else if(zunkoCreatorFlag==true){
            canvas.drawBitmap(listImage.get(1), vect.x - imageSize.x / 2, vect.y - imageSize.y / 2,null);
        }
    }

}
