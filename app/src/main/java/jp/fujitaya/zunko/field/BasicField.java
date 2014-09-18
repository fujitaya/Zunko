package jp.fujitaya.zunko.field;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import jp.fujitaya.zunko.GameView;
import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.field.zunko.ChibiZunko;
import jp.fujitaya.zunko.util.ImageLoader;
import jp.fujitaya.zunko.util.PointerInfo;

public abstract class BasicField extends Field {
    protected PointF pos;
    protected Bitmap bg;
    protected int fieldImageId;
    protected int width, height, initX, initY;

    protected int maxZunkoExistNum;
    protected int initialZunkoPower;
    protected int initialZunkoNum;

    ArrayList<ChibiZunko> listZunko;
    ArrayList<Building> listBuilding;
    ArrayList<Creator> listCreator;

    ImageLoader loader;

    BasicField(FieldData fd){
        super(fd.name);

        pos = new PointF();
        listZunko = new ArrayList<ChibiZunko>();
        listBuilding = new ArrayList<Building>();
        listCreator = new ArrayList<Creator>();

        loader = ImageLoader.getInstance();
        fieldImageId = fd.fieldImageId;
        bg = loader.load(fieldImageId);

        init(fd);
    }

    @Override public FieldData getFieldData(){
        FieldData fd = new FieldData();

        fd.name = name;
        fd.fieldImageId = fieldImageId;
        fd.fieldWidth = width;
        fd.fieldHeight = height;
        fd.initX = initX;
        fd.initY = initY;

        fd.maxZunkoExistNum = maxZunkoExistNum;
        fd.initialZunkoPower = initialZunkoPower;
        fd.initialZunkoNum = initialZunkoNum;

        for(Creator c: listCreator){
            FieldData.CreatorData cd = fd.createCreatorData();
            cd.imageId = c.getImageId();
            cd.scale = c.getScale();
            cd.fieldX = c.getX();
            cd.fieldY = c.getY();
            cd.spawnTime = c.getSpawnTime();
            cd.spawnRange = c.getSpawnRange();
            fd.addCreatorData(cd);
        }

        for(Building b: listBuilding){
            FieldData.BuildingData bd = fd.createBuildingData();
            bd.imageId = b.getImageId();
            bd.scale = b.getScale();
            bd.fieldX = b.getX();
            bd.fieldY = b.getY();
            bd.hp = b.getHP();
            bd.maxHP = b.getInitialHP();
            fd.addBuildingData(bd);
        }

        return fd;
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

    public void init(FieldData fd){
        pos.set(0, 0);

        width = fd.fieldWidth;
        height = fd.fieldHeight;
        initX = fd.initX;
        initY = fd.initY;
        moveTo(initX, initY);

        for(FieldData.CreatorData e: fd.creators){
            listCreator.add(new Creator(e));
        }
        for(FieldData.BuildingData e: fd.buildings){
            listBuilding.add(new Building(e));
        }

        if(listCreator.size() > 0) {
            for (int i = 0; i < fd.initialZunkoNum; ++i)
                addZunko(listCreator.get(0), fd.initialZunkoPower);
        }

        maxZunkoExistNum = fd.maxZunkoExistNum;
        initialZunkoPower = fd.initialZunkoPower;
        initialZunkoNum = fd.initialZunkoNum;
    }

    public void dispose(){
    }

    private int sortCounter = 0;
    private static final int SORT_INTERVAL = 120;
    private int touchedCounter = 0;
    private ChibiZunko touchedZunko = null;
    private ArrayList<ChibiZunko> selectedQueue = new ArrayList<ChibiZunko>();
    private ArrayList<ChibiZunko> selectWaitingQueue = new ArrayList<ChibiZunko>();
    private ArrayList<ChibiZunko> searchBuffer = new ArrayList<ChibiZunko>();
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
                    int idx = selectedQueue.indexOf(zunko);
                    if(idx >= 0) selectedQueue.remove(idx);
                    idx = selectWaitingQueue.indexOf(idx);
                    if(idx >= 0) selectWaitingQueue.remove(idx);
                    zunko.activateAttackState(build);
                }
            }
        }

        // check if build is dead.
        Iterator<Building> iterb = listBuilding.iterator();
        while(iterb.hasNext()){
            Building b = iterb.next();
            if(b.isCollapsed()){
                for(ChibiZunko zunko: listZunko){
                    if(b == zunko.getTarget()) zunko.tryEndAttackState();
                }

                FieldData.CreatorData cd = (new FieldData()).createCreatorData();
                cd.imageId = R.drawable.cz_zunda;
                cd.scale = b.getScale();
                cd.fieldX = b.getX();
                cd.fieldY = b.getY();
                cd.spawnTime = 60*5;
                cd.spawnRange = 300;

                listCreator.add(new Creator(cd));
                iterb.remove();
            }
        }

        // check to create ChibiZunko
        for(Creator e: listCreator){
            if(e.isCreatable() && getTotalZunkoNum()<maxZunkoExistNum)
                addZunko(e, 1);
        }

        // field objects update
        for(Building build: listBuilding) build.update();
        for(Creator creator: listCreator) creator.update();

        // zunko update and remove check
        Iterator<ChibiZunko> iterz = listZunko.iterator();
        while(iterz.hasNext()){
            ChibiZunko zunko = iterz.next();
            zunko.update();
            if(zunko.isRest()){
                if(zunko == touchedZunko) touchedZunko = null;
                iterz.remove();
            }
        }

        // Z sort
        if(++sortCounter >= SORT_INTERVAL){
            sortCounter = 0;
            Collections.sort(listZunko, new Comparator<ChibiZunko>(){
                public int compare(ChibiZunko a, ChibiZunko b){
                    float da = a.getX()*a.getX() + a.getY()*a.getY();
                    float db = b.getX()*b.getX() + b.getY()*b.getY();
                    if(da <= db) return -1;
                    return 1;
                }
            });
        }
    }

    private void startSelectChain(ChibiZunko top){
        touchedCounter = 0;
        selectedQueue.add(touchedZunko);
    }
    private int selectWaitCounter = 0;
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
        Collections.sort(selectWaitingQueue, new Comparator<ChibiZunko>() {
            public int compare(ChibiZunko a, ChibiZunko b) {
                float ax = a.getX() - touchedZunko.getX();
                float ay = a.getY() - touchedZunko.getY();
                float bx = b.getX() - touchedZunko.getX();
                float by = b.getY() - touchedZunko.getY();
                float res = ax * ax + ay * ay - bx * bx + by * by;
                if (res < 0) return -1;
                else if (res > 0) return 1;
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
    private boolean fieldTouched = false;
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
        if(x+width < GameView.VIEW_WIDTH) x = GameView.VIEW_WIDTH - width;
        else if(x > 0) x = 0;
        if(y+height < GameView.VIEW_HEIGHT) y = GameView.VIEW_HEIGHT - height;
        else if(y > 0) y = 0;
        pos.set(x, y);
    }
    public void moveOffset(float x, float y){
        moveTo(pos.x+x, pos.y+y);
    }

    public void addZunko(Creator creator, int powerOffset){
        float r = creator.getSpawnRange();
        float x = (float) (Math.random() * r) - r/2 + creator.getX();
        float y = (float) (Math.random() * r) - r/2 + creator.getY();

        ChibiZunko zunko = new ChibiZunko();
        zunko.setPower(powerOffset);
        zunko.moveTo(x, y);

        listZunko.add(zunko);
        creator.reset();
    }

    public void draw(Canvas canvas){
        // backgrond
        int w = bg.getWidth();
        int h = bg.getHeight();
        float bx = pos.x;
        float by = pos.y;
        while(bx+w < 0) bx += w;
        while(by+h < 0) by += h;
        for(float dy=by; dy < GameView.VIEW_HEIGHT; dy+=h) {
            for(float dx=bx; dx < GameView.VIEW_WIDTH; dx+=w) {
                canvas.drawBitmap(bg, dx, dy, null);
            }
        }

        // objects
        for(Building e: listBuilding) e.draw(canvas, pos.x, pos.y);
        for(Creator e: listCreator) e.draw(canvas, pos.x, pos.y);

        // zunko
        for(ChibiZunko e: listZunko){e.draw(canvas, pos.x, pos.y);}
    }
}
