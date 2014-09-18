package jp.fujitaya.zunko.field;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class Field {
    protected String name;

    public Field(String name){
        this.name = name;
    }
    public String getFieldName(){
        return name;
    }
    public abstract int getNowHP();
    public abstract int getInitialHP();
    public abstract int getTotalZunkoNum();

    public abstract void clearZunko();
    public abstract void update();
    public abstract void interrupt(MotionEvent event);
    public abstract void draw(Canvas canvas);
    public abstract void dispose();
}
