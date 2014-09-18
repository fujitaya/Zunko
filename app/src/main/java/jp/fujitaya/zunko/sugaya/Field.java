package jp.fujitaya.zunko.sugaya;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.os.Parcelable;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.hayashima.MenuState;
import jp.fujitaya.zunko.hayashima.MenuState;
import jp.fujitaya.zunko.util.Image;

public class Field{
    PointF vect;
    //Resources res;
    //Bitmap image;
    Image image;
    boolean selectFlag=false;
    String fieldName;

    //list of obj
    ArrayList<Building> listBuilding;
    ArrayList<Creator> listCreator;
    ArrayList<MiniZunko> listMiniZunko;

    int MAX_CREAT_NUM=100;
    int MAX_POWER_UP_NUM=100;
    int powerUpCount=0;
    int maxFieldHitPoint=0;
    static int sumAttackPower=0;


    public Field(String name){
        vect=new PointF(0,0);
        fieldName=name;
        createBuild();
        init();
    }
    public Field(){
    }


    public void init(){
        setFieldImage();
        addSumAttackPower();
    }
    void setFieldImage(){
        image=new Image(R.drawable.fd_green);
    }

    public void createBuild(){
        listBuilding=new ArrayList<Building>();
        listCreator=new ArrayList<Creator>();
        listMiniZunko=new ArrayList<MiniZunko>();

        listCreator.add(new Creator(new PointF(100 ,100)));
        listBuilding.add(new Building(new PointF(500,500)));
        listBuilding.add(new Building(new PointF(200,800)));

        for(Building build:listBuilding) {
            maxFieldHitPoint +=build.getHitPoint();
        }
    }

    public void update(){

        setMiniZunkoAttack();
        attackMiniZunko();
        deleteBuildingAndCreatCreator();
        deleteZunko();
        createMiniZunko();

        for(Building b:listBuilding){b.update();}
        for(Creator b:listCreator){b.update();}
        for(MiniZunko b:listMiniZunko){b.update();}

    }
    public void draw(Canvas canvas){
        //canvas.drawBitmap(image,vect.x,vect.y,null);
        image.draw(canvas);
        for(Building b:listBuilding){b.draw(canvas);}
        for(Creator b:listCreator){b.draw(canvas);}
        for(MiniZunko b:listMiniZunko){b.draw(canvas);}
    }

    public void interrupt(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_UP){
            if(selectFlag==false){
                for(int i=0;i<listMiniZunko.size();i++) {
                    if (listMiniZunko.get(i).isHit(new PointF((float) event.getX(), (float) event.getY()))) {
                        if (listMiniZunko.get(i).isSelect() == false&&listMiniZunko.get(i).getNowState()!=miniZunkoState.attack) {
                            listMiniZunko.get(i).setSelect(true);
                            selectFlag=true;
                            break;
                        }
                    }
                }
            }
            else{
                for(Building build:listBuilding){
                    if (build.isHit(new PointF((float) event.getX(), (float) event.getY()))) {
                        for(MiniZunko zun:listMiniZunko) {
                            //cant moving
                            if(zun.isSelect()) {
                                zun.tatchToMove(new PointF(build.getVect().x, build.getVect().y));
                                zun.setMovingAttackBuildingNumber(build.getCreateNumber());
                                zun.setSelect(false);
                                selectFlag=false;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    void createMiniZunko(){
        for(Creator cre:listCreator){
            if(cre.getCreateCount()==0){
                if(listMiniZunko.size()<MAX_CREAT_NUM) {
                    PointF p = new PointF((float) (Math.random() * 200) - 100, (float) (Math.random() * 200) - 100);
                    p.x = cre.getVect().x + p.x;
                    p.y = cre.getVect().y + p.y;
                    listMiniZunko.add(new MiniZunko(/*bitmapMiniZunko,*/new PointF(p.x, p.y)));
                }
                else if(powerUpCount<MAX_POWER_UP_NUM){
                    addOneAttackPower();
                    powerUpCount++;
                }
            }
        }
    }
    void deleteBuildingAndCreatCreator(){
        Iterator<Building> build = listBuilding.iterator();
        while(build.hasNext()){
                Building temp = build.next();
                if(temp.getHitPoint()<0){
                    listCreator.add(new Creator(/*bitmapCreator,*/ new PointF(temp.getVect().x, temp.getVect().y)));
                    for(MiniZunko zun:listMiniZunko){
                        if(zun.getAttackBuildingNumber()==temp.getCreateNumber()||zun.getMovingAttackBuildingNumber()==temp.getCreateNumber()){
                            zun.changeState(miniZunkoState.wait);
                            zun.setAttackBuildingNumber(-1);
                            zun.setMovingAttackBuildingNumber(-1);
                            zun.resetDv();
                        }
                    }
                    build.remove();
                }
            }
    }
    void setMiniZunkoAttack(){
        for(MiniZunko zun:listMiniZunko){
            for(Building build:listBuilding){
                    if (zun.isHit(build.getVect())) {
                        zun.changeState(miniZunkoState.attack);
                        zun.setAttackBuildingNumber(build.getCreateNumber());
                        zun.resetDv();
                    }
                }
        }
    }
    void attackMiniZunko(){
        for(MiniZunko zun:listMiniZunko){
            for(Building build:listBuilding){
                if (zun.getNowState() == miniZunkoState.attack
                        &&zun.getAttackBuildingNumber()==build.getCreateNumber()) {
                    build.DecHitPoint(zun.getAttackPower());
                }
            }
        }
    }
    public void deleteZunko(){
        Iterator<MiniZunko> i = listMiniZunko.iterator();
                while(i.hasNext()){
                    MiniZunko temp = i.next();
                    if(temp.getActionPoint()==0){
                        i.remove();
                        powerUpCount--;
                    }
                }
        }
    public void dispose(){
        disposeImage();
        setMenuState(menuState.None);
    }
    public void disposeImage(){
        image=null;
        for(Building b:listBuilding){b.disposeImage();}
        for(Creator b:listCreator){b.disposeImage();}
        for(MiniZunko b:listMiniZunko){b.disposeImage();}
    }
    public int getMiniZunkoNumber(){return listMiniZunko.size();}
    public int getBuildingNumber(){return listBuilding.size();}
    public int getCreatorNumber(){return listCreator.size();}

    public int getSumAttackPower(){
        int sum=0;
        for(MiniZunko zun:listMiniZunko){
            sum+=zun.getAttackPower();
        }
        return sum;
    }
    public float getFieldHitPoint(){
        int p=0;
        for(Building build:listBuilding){
            p+=build.getHitPoint();
        }
        return (float)(p/maxFieldHitPoint);
    }
    public String getFieldName(){return  fieldName;}
    public boolean isToEndField(){
        if(listBuilding.size()==0){
            return true;
        }
        return false;
    }
    void addSumAttackPower(){
        if(listMiniZunko.size()!=0) {
            listMiniZunko.get((int) (Math.random() * listMiniZunko.size())).addAttackPower(sumAttackPower);
            sumAttackPower=0;
        }
    }
    void addOneAttackPower() {
            listMiniZunko.get((int) (Math.random() * listMiniZunko.size())).addAttackPower(1);
    }

}
