package jp.fujitaya.zunko.sugaya;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Parcelable;
import android.view.MotionEvent;

import java.util.ArrayList;

import jp.fujitaya.zunko.R;

public class Field extends BasicObject {
    Resources res;
    boolean selectFlag=false;

    //list of obj
    ArrayList<Building> listBuilding;
    ArrayList<MiniZunko> listMiniZunko;

    //list of bitmap
    ArrayList<Bitmap> bitmapBuilding;
    ArrayList<Bitmap> bitmapMiniZunko;

    Field(ArrayList<Bitmap> image,Resources r){
        super(image);
        this.res=r;
        init();
    }

    @Override public void init(){
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


        listBuilding.add(new Building(bitmapBuilding,new Point(50,50)));
        listBuilding.get(0).setHitPoint(0);
        listBuilding.add(new Building(bitmapBuilding,new Point(500,500)));
        listBuilding.add(new Building(bitmapBuilding,new Point(200,800)));

    }
    @Override public void update(){

        setMiniZunkoAttack();
        createZunkoCreator();

        for(int i=0;i<listBuilding.size();i++){
            listBuilding.get(i).update();
        }
        for(int i=0;i<listMiniZunko.size();i++){
            listMiniZunko.get(i).update();
        }
    }
    @Override public void draw(Canvas canvas){
        canvas.drawBitmap(listImage.get(0),vect.x,vect.y,new Paint());
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
                    if (listMiniZunko.get(i).isHit(new Point((int) event.getX(), (int) event.getY()))) {
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
                    if (listBuilding.get(i).isZunkoCreator()==false && listBuilding.get(i).isHit(new Point((int) event.getX(), (int) event.getY()))) {
                        for(int j=0;j<listMiniZunko.size();j++) {
                            //cant moving
                            if(listMiniZunko.get(j).isSelect()) {
                                listMiniZunko.get(j).tatchToMove(new Point((int) event.getX(), (int) event.getY()));
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
                Point p=new Point(bitmapBuilding.get(0).getWidth()/2,bitmapBuilding.get(0).getHeight()/2);
                p.x=listBuilding.get(i).getVect().x+p.x;
                p.y=listBuilding.get(i).getVect().y+p.y;
                listMiniZunko.add(new MiniZunko(bitmapMiniZunko,new Point(p)));
            }
        }
    }
    void setMiniZunkoAttack(){
        for(int i=0;i<listMiniZunko.size();i++){
            for(int j=0;j<listBuilding.size();j++){
                if(listBuilding.get(j).isZunkoCreator()==false) {
                    if (listMiniZunko.get(i).isHit(listBuilding.get(j).getVect())) {
                        listMiniZunko.get(i).changeState(miniZunkoState.attack);
                        listMiniZunko.get(i).setAttackBuildingNumber(i);

                    }
                }
            }
        }
    }
}
