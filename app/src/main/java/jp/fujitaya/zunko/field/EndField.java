package jp.fujitaya.zunko.field;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import java.util.ArrayList;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.field.zunko.ChibiZunko;
import jp.fujitaya.zunko.util.ImageLoader;

public class EndField extends Field {

    private PointF pos;
    private Bitmap bg;  // Create TileImage class instead?
    private int width, height;


    FieldData fd;

    ArrayList<ChibiZunko> listZunko;
    ArrayList<Building> listBuilding;
    //    ArrayList<Creator> listCreator;
    ArrayList<FieldData.ObjectData> listImageObject;

    /*int MAX_CREAT_NUM=100;
    int MAX_POWER_UP_NUM=100;
    int powerUpCount=0;
    int maxFieldHitPoint=0;
    //static int sumAttackPower=0;
    */



    ImageLoader loader;

    public EndField(String name){
        super(name);

        pos = new PointF();
        fd = new FieldData();
        listZunko = new ArrayList<ChibiZunko>();
        listImageObject = new ArrayList<FieldData.ObjectData>();

        loader = ImageLoader.getInstance();
        bg = loader.load(fd.fieldImageId);
        for(FieldData.ObjectData e: listImageObject){
            loader.load(e.imageId);
        }
    }


    @Override public int getNowHP(){
        int hp = 0;
        for(FieldBaseObject e: listBuilding){
            hp += e.getHP();
        }
        return hp;
    }
    @Override public int getInitialHP() {
        int ihp = 0;
        for(FieldBaseObject e: listBuilding) {
            ihp += e.getInitialHP();
        }
        return ihp;
    }
    @Override public int getTotalZunkoNum(){
        int ret = 0;
        ret += listZunko.size();
        return ret;
    }
    @Override public void clearZunko(){
        listZunko.clear();
    }


    private void setFD(FieldData fd, String name){
        fd.name = name;
        fd.fieldImageId = R.drawable.fd_green;
        fd.fieldWidth = 720*2;
        fd.fieldHeight = (int)(1280*1.5);

        fd.maxZunkoExistNum = 200;
        fd.initialZunkoPower = 1;
        fd.initialZunkoNum = 10;

        fd.creator.imageId = R.drawable.ic_launcher;
        fd.creator.scale = 1;
        fd.creator.fieldX = 100;
        fd.creator.fieldY = 100;

        FieldData.BuildingData bd = fd.createBuildingData();
        bd.imageId = R.drawable.mc_mig;
        bd.hp = 180;
        bd.scale = 4;
        bd.fieldX = 500;
        bd.fieldY = 500;

        bd = fd.createBuildingData();
        bd.imageId = R.drawable.mc_mig;
        bd.hp = 180;
        bd.scale = 4;
        bd.fieldX = 200;
        bd.fieldY = 800;
    }

    @Override public void update(){
        createMiniZunko();
        for(Creator b:listCreator){b.update();}
        for(ChibiZunko b:listMiniZunko){b.update();}
    }
    @Override public void interrupt(MotionEvent event){
    }
    @Override public void dispose(){
        disposeImage();
    }
    @Override void disposeImage(){
        for(Building b:listBuilding){b.disposeImage();}
        for(Creator b:listCreator){b.disposeImage();}
        for(ChibiZunko b:listMiniZunko){b.disposeImage();}
    }
    @Override public void draw(Canvas canvas){
        for(Building b:listBuilding){b.draw(canvas);}
        for(Creator b:listCreator){b.draw(canvas);}
        for(ChibiZunko b:listMiniZunko){b.draw(canvas);}
    }
    public void createBuild(){
     /*   listBuilding=new ArrayList<Building>();
        listCreator=new ArrayList<Creator>();
        listMiniZunko=new ArrayList<MiniZunko>();

        listCreator.add(new Creator(new PointF(100 ,100)));
        listBuilding.add(new Building(new PointF(500,500)));
        listBuilding.add(new Building(new PointF(200,800)));
        */

        for(Building build:listBuilding) {
            maxFieldHitPoint +=build.getHitPoint();
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
    void addOneAttackPower() {
        listMiniZunko.get((int) (Math.random() * listMiniZunko.size())).addAttackPower(1);
    }
}
