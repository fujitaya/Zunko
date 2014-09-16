package jp.fujitaya.zunko.sugaya;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Parcelable;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import jp.fujitaya.zunko.R;

public class Field{
    PointF vect;
    Resources res;
    Bitmap image;
    boolean selectFlag=false;

    //list of obj
    ArrayList<Building> listBuilding;
    ArrayList<MiniZunko> listMiniZunko;

    //list of bitmap
    ArrayList<Bitmap> bitmapBuilding;
    ArrayList<Bitmap> bitmapMiniZunko;

    Field(Resources r){
        this.res=r;
        vect=new PointF(0,0);
        image=BitmapFactory.decodeResource(res, R.drawable.green_field);
        init();
    }

    public void init(){
        listBuilding=new ArrayList<Building>();
        listMiniZunko=new ArrayList<MiniZunko>();

        bitmapBuilding=new ArrayList<Bitmap>();
        bitmapMiniZunko=new ArrayList<Bitmap>();

        //Set bitmap data
        bitmapBuilding.add(BitmapFactory.decodeResource(res, R.drawable.mig));
        bitmapBuilding.add(BitmapFactory.decodeResource(res, R.drawable.ic_launcher));

        bitmapMiniZunko.add(BitmapFactory.decodeResource(res, R.drawable.zunko_aruku1));
        bitmapMiniZunko.add(BitmapFactory.decodeResource(res, R.drawable.zunko_select));
        bitmapMiniZunko.add(BitmapFactory.decodeResource(res, R.drawable.zunko_aruku2));
        bitmapMiniZunko.add(BitmapFactory.decodeResource(res, R.drawable.zunko_attack));
        bitmapMiniZunko.add(BitmapFactory.decodeResource(res, R.drawable.zunko_rest));

        listBuilding.add(new Building(bitmapBuilding,new PointF(100 ,100)));
        listBuilding.get(0).setZunkoCreatorFlag(true);
        listBuilding.add(new Building(bitmapBuilding,new PointF(500,500)));
        listBuilding.add(new Building(bitmapBuilding,new PointF(200,800)));

    }
    public void update(){

        setMiniZunkoAttack();
        attackMiniZunko();
        deleteZunko();
        createZunkoCreator();

        for(int i=0;i<listBuilding.size();i++){
            listBuilding.get(i).update();
        }
        for(int i=0;i<listMiniZunko.size();i++){
            listMiniZunko.get(i).update();
        }
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image,vect.x,vect.y,null);
        for(int i=0;i<listBuilding.size();i++){
            listBuilding.get(i).draw(canvas);
        }
        for(int i=0;i<listMiniZunko.size();i++){
            listMiniZunko.get(i).draw(canvas);
        }
    }

    public void interrupt(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){
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
                for(int i=0;i<listBuilding.size();i++){
                    if (listBuilding.get(i).isZunkoCreator()==false && listBuilding.get(i).isHit(new PointF((float) event.getX(), (float) event.getY()))) {
                        for(int j=0;j<listMiniZunko.size();j++) {
                            //cant moving
                            if(listMiniZunko.get(j).isSelect()) {
                                listMiniZunko.get(j).tatchToMove(new PointF(listBuilding.get(i).getVect().x,listBuilding.get(i).getVect().y));
                                listMiniZunko.get(j).setSelect(false);
                                selectFlag=false;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    void createZunkoCreator(){
        for(int i=0;i<listBuilding.size();i++){
            if(listBuilding.get(i).isNowZunkoCreate()){

                PointF p=new PointF((float)(Math.random()*bitmapBuilding.get(0).getWidth())-bitmapBuilding.get(0).getWidth()/2
                        ,(float)(Math.random()*bitmapBuilding.get(0).getHeight())-bitmapBuilding.get(0).getHeight()/2);
                p.x=listBuilding.get(i).getVect().x+p.x;
                p.y=listBuilding.get(i).getVect().y+p.y;
                listMiniZunko.add(new MiniZunko(bitmapMiniZunko,new PointF(p.x,p.y)));
            }
        }
    }
    void setMiniZunkoAttack(){
        for(int i=0;i<listMiniZunko.size();i++){
            for(int j=0;j<listBuilding.size();j++){
                if(listBuilding.get(j).isZunkoCreator()==false) {
                    if (listMiniZunko.get(i).isHit(listBuilding.get(j).getVect())) {
                        listMiniZunko.get(i).changeState(miniZunkoState.attack);
                        listMiniZunko.get(i).setAttackBuildingNumber(listBuilding.get(j).getCreateNumber());
                        listMiniZunko.get(i).resetDv();

                    }
                }
            }
        }
    }
    void attackMiniZunko(){
        for(int i=0;i<listMiniZunko.size();i++) {
            for (int j = 0; j < listBuilding.size(); j++) {
                if (listMiniZunko.get(i).getNowState() == miniZunkoState.attack
                        &&listBuilding.get(j).isZunkoCreator()==false
                        &&listMiniZunko.get(i).getAttackBuildingNumber()==listBuilding.get(j).getCreateNumber()) {
                    listBuilding.get(j).DecHitPoint(listMiniZunko.get(i).getAttackPower());
                    if(listBuilding.get(j).getHitPoint()<=0){
                        for(int k=0;k<listMiniZunko.size();k++){
                            if(listMiniZunko.get(k).getAttackBuildingNumber()==listBuilding.get(j).getCreateNumber()){
                                listMiniZunko.get(k).changeState(miniZunkoState.wait);
                                listMiniZunko.get(k).resetDv();
                            }
                        }
                        listBuilding.get(j).setZunkoCreatorFlag(true);
                    }
                }
            }
        }
    }
    public void deleteZunko(){
        Iterator<MiniZunko> i = listMiniZunko.iterator();
        while(i.hasNext()){
                while(i.hasNext()){
                    MiniZunko temp = i.next();
                    //接頭辞が「堀北」以外は除外
                    if(temp.getActionPoint()==0){
                        i.remove();
                    }
                }
            }
        }
}
