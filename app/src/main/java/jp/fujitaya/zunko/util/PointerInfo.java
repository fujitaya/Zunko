package jp.fujitaya.zunko.util;

import android.view.MotionEvent;

public class PointerInfo{
    public int index, id;
    public float x, y;

    public PointerInfo(){
        clear();
    }

    public void clear(){
        index = id = -1;
        x = y = 0;
    }
    public void update(MotionEvent event){
        clear();
        index = event.getActionIndex();
        id = event.getPointerId(index);
        x = event.getX(index);
        y = event.getY(index);
    }
    public boolean available(){
        if(index!=-1 && id!=-1) return true;
        return false;
    }
    public void copy(PointerInfo rhs){
        index = rhs.index;
        id = rhs.id;
        x = rhs.x;
        y = rhs.y;
    }

    public boolean isThisEvent(MotionEvent event){
        return id == event.getPointerId(event.getActionIndex());
    }
}
