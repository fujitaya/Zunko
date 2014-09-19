package jp.fujitaya.zunko.field;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class Field {
    protected String name;
    protected boolean active;

    public Field(String name){
        this.name = name;
        active = false;
    }
    public String getFieldName(){
        return name;
    }

    public abstract boolean isCaptureField();
    public void activate(boolean flag){
        active = flag;
    }
    public boolean isActive(){
        return active;
    }

    public abstract int getNowHP();
    public abstract int getInitialHP();
    public abstract int getTotalZunkoNum();
    public abstract FieldData getFieldData();

    public abstract void clearZunko();
    public abstract void update();
    public abstract void interrupt(MotionEvent event);
    public abstract void draw(Canvas canvas);
    public abstract void dispose();
    public abstract void addPower();
}
