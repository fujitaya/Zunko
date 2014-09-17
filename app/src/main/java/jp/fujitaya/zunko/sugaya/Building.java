package jp.fujitaya.zunko.sugaya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.HashMap;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.Image;

public class Building extends BasicObject {
    static enum ImageName{
        base
    };
    HashMap<ImageName,Integer> mapImage;
    static int staticCreateNumber=0;
    int createNumber=0;
    String buildingName;
    int hitPoint;
    static int START_HIT_POINT=60*3;
    Building(/*ArrayList<Bitmap> image,*/PointF v){
        super(/*image,*/v);
        //image
        mapImage = new HashMap<ImageName, Integer>();
        mapImage.put(ImageName.base, R.drawable.mc_mig);
        image = new Image(mapImage.get(ImageName.base));
        image.setCenter();
        image.setPosition(vect);

        buildingName="None";
        tatchSize=(int)Math.sqrt(image.getWidth()*image.getWidth()+image.getHeight()*image.getHeight());
        hitPoint=START_HIT_POINT;
        createNumber=staticCreateNumber;
        staticCreateNumber++;

    }
    public int getHitPoint(){return hitPoint;}
    public void setHitPoint(int h){hitPoint=h;}
    public void DecHitPoint(int power){hitPoint-=power;}

    public int getCreateNumber(){return createNumber;}
    public void resetCreateNumber(){createNumber=0;}
    @Override public void init(){
    }
    public void loadImage(){

    }
    @Override public void update(){
    }
    @Override public void draw(Canvas canvas){
        image.draw(canvas);
    }

    @Override public void disposeImage(){
        mapImage.clear();
        image=null;
    }
}
