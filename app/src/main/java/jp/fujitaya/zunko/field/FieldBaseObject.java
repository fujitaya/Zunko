package jp.fujitaya.zunko.field;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class FieldBaseObject {
    protected PointF pos;
    protected int hp;
    protected int initHP;
    protected RectF col;
    protected int imageId;

    public FieldBaseObject(){
        pos = new PointF();
        pos.set(0, 0);
        hp = 1;
        initHP = 1;
        imageId = -1;
        col = null;
    }

    public int getImageId(){
        return imageId;
    }

    public abstract void update();
    public abstract void draw(Canvas canvas, float baseX, float baseY);

    public float getX(){return pos.x;}
    public float getY(){return pos.y;}
    public void moveTo(float x, float y){
        pos.set(x, y);
    }
    public void moveOffset(float x, float y){
        pos.offset(x, y);
    }

    public void setHP(int val){
        initHP = hp = val;
    }
    public int damage(int val){
        return hp -= val;
    }
    public int getHP(){
        return hp;
    }
    public int getInitialHP(){
        return initHP;
    }

    public void setCollision(RectF rect){
        col = rect;
    }
    public RectF getCollision(){
        return col;
    }
    public boolean isInside(float x, float y){
        if(col == null) return false;
        return pos.x+col.left<=x && pos.x+col.right>=x && pos.y+col.top<=y && pos.y+col.bottom>=y;
    }
    public boolean isOverwrapped(float l, float t, float r, float b){
        if(col == null) return false;
        return pos.x+col.left<r && l<pos.x+col.right &&
                t<pos.y+col.bottom && pos.y+col.top<b;
    }
    public boolean isOverwrapped(float baseX, float baseY, RectF col){
        if(this.col == null) return false;
        return isOverwrapped(baseX+col.left, baseY+col.top,
                baseX+col.right, baseY+col.bottom);
    }

//    public boolean isHit(PointF v) {
//        double q=Math.sqrt((vect.x-v.x)*(vect.x-v.x)+(vect.y-v.y)*(vect.y-v.y));
//        return tatchSize>q;
//    }
}
