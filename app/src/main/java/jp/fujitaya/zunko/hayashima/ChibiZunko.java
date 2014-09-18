package jp.fujitaya.zunko.hayashima;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;

import jp.fujitaya.zunko.util.ImageLoader;

public class ChibiZunko {
    public enum ImageName{
        WAIT,
        LOOK1, LOOK2,
        MOVE1, MOVE2,
        ATTACK1, ATTACK2,
        REST1, REST2,
    }
    public static final int COL_WID = 100;
    public static final int COL_HEI = 100;

    private PointF pos;
    private float power;
    private boolean selected;

    private ChibiZunkoState state;
    private ImageLoader ld;

    public ChibiZunko(){
        pos = new PointF();
        pos.set(0, 0);
        power = 0;
        selected = false;

        state = new ChibiZunkoStateWait(this);
        ld = ImageLoader.getInstance();
    }
    public void update(){
        ChibiZunkoState newState = state.execute();
        if(newState != null){
            state = newState;
        }
    }

    public void moveTo(float x, float y){
        pos.set(x, y);
    }
    public void moveOffset(float x, float y){
        pos.offset(x, y);
    }

    public boolean isInside(float x, float y){
        return pos.x<=x && pos.x+COL_WID>=x && pos.y<=y && pos.y+COL_HEI>=y;
    }
    public boolean isOverwrapped(ChibiZunko rhs){
        return pos.x<rhs.pos.x+COL_WID && rhs.pos.x<pos.x+COL_WID &&
                rhs.pos.y<pos.y+COL_HEI && pos.y<rhs.pos.y+COL_HEI;
    }
    public void select(boolean flag){
        selected = flag;
    }
    public boolean isSelected(){
        return selected;
    }

    public void draw(Canvas canvas, float baseX, float baseY){
        Bitmap image = state.getImage(ld);
        canvas.drawBitmap(image, baseX+pos.x, baseY+pos.y, null);
    }
}
