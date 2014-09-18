package jp.fujitaya.zunko.util;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class Field{
    protected String name;

    public Field(String name){this.name=name;}
    public String getFiledName(){return name;}
    public abstract int getNowHP();
    public abstract int getInitialHP();
    public abstract int getTotalZunkoNum();

    public abstract void celarZunko();
    public abstract void update();
    public abstract void interrupt(MotionEvent event);
    public abstract void dispose();
    public abstract void draw(Canvas canvas);
}