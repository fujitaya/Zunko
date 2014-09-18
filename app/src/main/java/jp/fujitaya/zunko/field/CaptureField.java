package jp.fujitaya.zunko.field;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.hayashima.ChibiZunko;
import jp.fujitaya.zunko.util.GameView;
import jp.fujitaya.zunko.util.ImageLoader;
import jp.fujitaya.zunko.util.PointerInfo;

public class CaptureField extends Field{
    private PointF pos;
    private Bitmap bg;  // Create TileImage class instead?
    private int width, height;

    FieldData fd;

    ArrayList<ChibiZunko> listZunko;
    ArrayList<Building> listBuilding;
//    ArrayList<Creator> listCreator;
    ArrayList<FieldData.ObjectData> listImageObject;

    ImageLoader loader;

    public CaptureField(String name){
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

        init();
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

    public void init(){
        pos.set(0, 0);
        setFD(fd, name);

        width = fd.fieldWidth;
        height = fd.fieldHeight;

        listImageObject.add(fd.creator);
        for(FieldData.BuildingData e: fd.buildings){
            listBuilding.add(new Building(e));
        }

        listImageObject.add(fd.creator);

        for(int i=0; i < fd.initialZunkoNum; ++i) addZunko();
    }
    public void dispose(){

    }

    int touchedCounter = 0;
    ChibiZunko touchedZunko = null;
    ArrayList<ChibiZunko> selectedQueue = new ArrayList<ChibiZunko>();
    ArrayList<ChibiZunko> selectWaitingQueue = new ArrayList<ChibiZunko>();
    ArrayList<ChibiZunko> searchBuffer = new ArrayList<ChibiZunko>();
    public void update(){
        // select chain
        if(touchedZunko != null && selectedQueue.size()==0) {
            startSelectChain(touchedZunko);
        }else if(touchedZunko != null){
            updateSelectChain();
        }else if(touchedZunko==null && selectedQueue.size()>0){
            endSelectChain();
        }

        // collision check
        for(ChibiZunko zunko: listZunko){
            for(Building build: listBuilding){
                if(zunko.isOverwrapped(build.getX(), build.getY(),
                        build.getCollision())){
                    zunko.activateAttackState(build);
                }
            }
        }

        // zunko update
        for(ChibiZunko e: listZunko) e.update();
    }
    private void startSelectChain(ChibiZunko top){
        touchedCounter = 0;
        selectedQueue.add(touchedZunko);
    }
    int selectWaitCounter = 0;
    private static final int CHAINBOX_SIZE = 300;
    private void updateSelectChain(){
        if(++selectWaitCounter == 3){
            selectWaitCounter = 0;
            if(selectWaitingQueue.size() > 0){
                ChibiZunko zunko = selectWaitingQueue.remove(0);
                zunko.select(true);
                zunko.chase(touchedZunko);
                selectedQueue.add(zunko);
            }
        }

        if(++touchedCounter < 20) return;
        touchedCounter = 0;

        ChibiZunko[] buf = new ChibiZunko[listZunko.size()];
        for(int i=0; i < listZunko.size(); ++i) buf[i] = listZunko.get(i);

        for(ChibiZunko zunko: selectedQueue) {
            float cx = zunko.getX()+ChibiZunko.COL_WID/2;
            float cy = zunko.getY()+ChibiZunko.COL_HEI/2;

            for(int i=0; i < buf.length; ++i){
                if(buf[i]==null || buf[i].isSelected() || selectWaitingQueue.indexOf(buf[i])>=0)
                    continue;
                if(buf[i].isOverwrapped(cx-CHAINBOX_SIZE, cy-CHAINBOX_SIZE,
                        cx+CHAINBOX_SIZE, cy+CHAINBOX_SIZE)){
                    searchBuffer.add(buf[i]);
                    // relate zunko to buf[i];
                    buf[i] = null;
                }
            }
        }

        for(ChibiZunko zunko: searchBuffer) selectWaitingQueue.add(zunko);
        searchBuffer.clear();
        Collections.sort(selectWaitingQueue, new Comparator<ChibiZunko>(){
            public int compare(ChibiZunko a, ChibiZunko b){
                float ax = a.getX()-touchedZunko.getX();
                float ay = a.getY()-touchedZunko.getY();
                float bx = b.getX()-touchedZunko.getX();
                float by = b.getY()-touchedZunko.getY();
                float res = ax*ax+ay*ay - bx*bx+by*by;
                if(res < 0) return -1;
                else if(res > 0) return 1;
                return 0;
            }
        });
    }
    private void endSelectChain(){
        for(ChibiZunko e: selectedQueue){
            e.select(false);
            e.tryUnsetChasing();
        }
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
        if(x < 0) x = 0;
        else if(x + GameView.VIEW_WIDTH >= width) x = width - GameView.VIEW_WIDTH;
        if(y < 0) y = 0;
        else if(y + GameView.VIEW_HEIGHT >= height) y = height - GameView.VIEW_HEIGHT;
        pos.set(x, y);
    }
    public void moveOffset(float x, float y){
        moveTo(pos.x+x, pos.y+y);
    }

    private static final float SPAWN_RANGE = 300;
    public void addZunko(){
        float x = (float) (Math.random() * SPAWN_RANGE) - SPAWN_RANGE/2 + fd.creator.fieldX;
        float y = (float) (Math.random() * SPAWN_RANGE) - SPAWN_RANGE/2 + fd.creator.fieldY;

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
        for(Building e: listBuilding) e.draw(canvas, pos.x, pos.y);

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
