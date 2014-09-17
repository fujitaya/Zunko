package jp.fujitaya.zunko.hayashima;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.sugaya.Building;
import jp.fujitaya.zunko.util.GameView;
import jp.fujitaya.zunko.util.ImageLoader;
import jp.fujitaya.zunko.util.PointerInfo;

public class CaptureField {
    String name;

    private PointF pos;
    private Bitmap bg;  // Create TileImage class instead?
    private int width, height;

    FieldData fd;

    ArrayList<ChibiZunko> listZunko;
    ArrayList<FieldData.ObjectData> listImageObject;
    ArrayList<Building> listBuilding;

    ImageLoader loader;

    public CaptureField(String name){
        pos = new PointF();
        pos.set(0, 0);

        fd = new FieldData();
        setFD(fd, name);

        this.name = name;
        listZunko = new ArrayList<ChibiZunko>();
        listImageObject = new ArrayList<FieldData.ObjectData>();
//        listCreator = new ArrayList<Creator>();
//        listBuilding = new ArrayList<Building>();

        init(fd);
        loader = ImageLoader.getInstance();
        for(FieldData.ObjectData e: listImageObject){
            loader.load(e.imageId);
        }
    }

    private void setFD(FieldData fd, String name){
        fd.name = name;
        fd.fieldImageId = R.drawable.fd_green;
        fd.fieldWidth = 720*2;
        fd.fieldHeight = (int)(1280*1.5);

        fd.maxZunkoExistNum = 200;
        fd.initialZunkoPower = 1;
        fd.initialZunkoNum = 0;

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

    public void init(FieldData fd){
        ImageLoader ld = ImageLoader.getInstance();
        bg = ld.load(fd.fieldImageId);
        width = fd.fieldWidth;
        height = fd.fieldHeight;

        listImageObject.add(fd.creator);
        for(FieldData.ObjectData e: fd.buildings) listImageObject.add(e);

        for(int i=0; i < 10; ++i) addZunko();

    }
    public void dispose(){

    }

    int touchedCounter = 0;
    ChibiZunko touchedZunko = null;
    ArrayList<ChibiZunko> selectedQueue = new ArrayList<ChibiZunko>();
    ArrayList<ChibiZunko> selectWaitingQueue = new ArrayList<ChibiZunko>();
    ArrayList<ChibiZunko> searchBuffer = new ArrayList<ChibiZunko>();
    public void update(){
        if(touchedZunko != null && selectedQueue.size()==0) {
            startSelectChain(touchedZunko);
        }else if(touchedZunko != null){
            updateSelectChain();
        }else if(touchedZunko==null && selectedQueue.size()>0){
            endSelectChain();
        }
    }
    private void startSelectChain(ChibiZunko top){
        touchedCounter = 0;
        selectedQueue.add(touchedZunko);
    }
    int selectWaitCounter = 0;
    private void updateSelectChain(){
        if(++selectWaitCounter == 3){
            selectWaitCounter = 0;
            if(selectWaitingQueue.size() > 0){
                ChibiZunko zunko = selectWaitingQueue.remove(0);
                zunko.select(true);
                selectedQueue.add(zunko);
            }
        }

        if(++touchedCounter < 20) return;
        touchedCounter = 0;

        ChibiZunko[] buf = new ChibiZunko[listZunko.size()];
        for(int i=0; i < listZunko.size(); ++i) buf[i] = listZunko.get(i);

        for(ChibiZunko zunko: selectedQueue) {
            for(int i=0; i < buf.length; ++i){
                if(buf[i] == null) continue;
                if(zunko.isOverwrapped(buf[i])){
                    buf[i].select(true);
                    searchBuffer.add(buf[i]);
                    // relate zunko to buf[i];
                    buf[i] = null;
                }
            }
        }

        for(ChibiZunko zunko: searchBuffer) selectedQueue.add(zunko);
        searchBuffer.clear();
    }
    private void endSelectChain(){
        for(ChibiZunko e: selectedQueue) e.select(false);
        selectedQueue.clear();
        selectWaitingQueue.clear();
        touchedCounter = 0;
    }

    private PointerInfo pi = new PointerInfo();
    private PointerInfo oldPi = new PointerInfo();
    boolean fieldTouched = false;
    public void interrupt(MotionEvent event){
        pi.update(event);
        float fx = pi.x - pos.x;
        float fy = pi.y - pos.y;
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                for (ChibiZunko e : listZunko) {
                    if (e.isInside(fx, fy)) {
                        touchedZunko = e;
                        break;
                    }
                }
                if (touchedZunko == null) {
                    fieldTouched = true;
                } else {
                    touchedZunko.select(true);
                }
                oldPi.update(event);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = (float)Math.floor(pi.x - oldPi.x);
                float dy = (float)Math.floor(pi.y - oldPi.y);
                if(touchedZunko == null){
                    moveOffset(dx, dy);
                }else{
                    touchedZunko.moveOffset(dx, dy);
                }
                oldPi.update(event);
                break;
            case MotionEvent.ACTION_UP:
                if(touchedZunko != null){
                    touchedZunko.select(false);
                    touchedZunko = null;
                }
                fieldTouched = false;
                break;
            default: break;
        }
    }

    public void moveTo(float x, float y){
        pos.set(x, y);
    }
    public void moveOffset(float x, float y){
        pos.offset(x, y);
    }

    public void addZunko(){
        float x = (float) (Math.random() * 200) - 100 + fd.creator.fieldX;
        float y = (float) (Math.random() * 200) - 100 + fd.creator.fieldY;

        ChibiZunko zunko = new ChibiZunko();
        zunko.moveTo(x, y);

        listZunko.add(zunko);
    }

    RectF rect = new RectF();
    public void draw(Canvas canvas){
        // backgrond
        int w = bg.getWidth();
        int h = bg.getHeight();
        for(int y=0; y < GameView.VIEW_HEIGHT; y+=h) {
            if(y+h < 0) continue;
            for (int x = 0; x < GameView.VIEW_WIDTH; x+=w) {
                if(x+w < 0) continue;
                canvas.drawBitmap(bg, x, y, null);
            }
        }

        // objects
        for(FieldData.ObjectData e: listImageObject){
            Bitmap b = loader.load(e.imageId);
            rect.left = pos.x + e.fieldX;
            rect.top = pos.y + e.fieldY;
            rect.right = pos.x + e.fieldX + b.getWidth()*e.scale;
            rect.bottom = pos.y + e.fieldY + b.getHeight()*e.scale;
            // Is rect inside screen window? No -> continue;
            canvas.drawBitmap(b, null, rect, null);
        }

        // zunko
        for(ChibiZunko e: listZunko){e.draw(canvas, pos.x, pos.y);}
    }
}
