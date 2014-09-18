package jp.fujitaya.zunko.field;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jp.fujitaya.zunko.GameView;
import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.field.zunko.ChibiZunko;
import jp.fujitaya.zunko.util.ImageLoader;
import jp.fujitaya.zunko.util.PointerInfo;

public class BasicField extends Field {
    protected PointF pos;
    protected Bitmap bg;  // Create TileImage class instead?
    protected int width, height;

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
        bg = loader.load(fd.fieldImageId);

        init(fd);
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

        for(FieldData.CreatorData e: fd.creators){
            listCreator.add(new Creator(e));
        }
        for(FieldData.BuildingData e: fd.buildings){
            listBuilding.add(new Building(e));
        }

        if(listCreator.size() > 0) {
            for (int i = 0; i < fd.initialZunkoNum; ++i)
                addZunko(listCreator.get(0));
        }
    }

    public void dispose(){
    }

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
                    zunko.activateAttackState(build);
                }
            }
        }

        // field objects update
        for(Building build: listBuilding) build.update();
        for(Creator creator: listCreator) creator.update();

        // check to create ChibiZunko
        for(Creator e: listCreator){
            if(e.isCreatable()) addZunko(e);
        }

        // zunko update
        for(ChibiZunko e: listZunko) e.update();
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
        if(x < 0) x = 0;
        else if(x + GameView.VIEW_WIDTH >= width) x = width - GameView.VIEW_WIDTH;
        if(y < 0) y = 0;
        else if(y + GameView.VIEW_HEIGHT >= height) y = height - GameView.VIEW_HEIGHT;
        pos.set(x, y);
    }
    public void moveOffset(float x, float y){
        moveTo(pos.x+x, pos.y+y);
    }

    public void addZunko(Creator creator){
        float r = creator.getSpawnRange();
        float x = (float) (Math.random() * r) - r/2 + creator.getX();
        float y = (float) (Math.random() * r) - r/2 + creator.getY();

        ChibiZunko zunko = new ChibiZunko();
        zunko.moveTo(x, y);

        listZunko.add(zunko);
        creator.reset();
    }

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
        for(Creator e: listCreator) e.draw(canvas, pos.x, pos.y);

        // zunko
        for(ChibiZunko e: listZunko){e.draw(canvas, pos.x, pos.y);}
    }
}
