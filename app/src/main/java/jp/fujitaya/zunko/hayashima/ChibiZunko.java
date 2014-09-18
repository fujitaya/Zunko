package jp.fujitaya.zunko.hayashima;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import jp.fujitaya.zunko.util.ImageLoader;

public class ChibiZunko extends FieldBaseObject{
//    public enum ImageName{
//        WAIT,
//        LOOK1, LOOK2,
//        MOVE1, MOVE2,
//        ATTACK1, ATTACK2,
//        REST1, REST2,
//    }
    public static final int COL_WID = 100;
    public static final int COL_HEI = 100;

    private PointF pos;
    private float power;
    private boolean selected;
    private boolean leftFace;
    private FieldBaseObject target;

    private ChibiZunkoState state;
    private ImageLoader ld;

    public ChibiZunko(){
        super();
        power = 0;
        selected = false;
        leftFace = true;
        target = null;

        setHP(100);
        setCollision(new RectF(0, 0, COL_WID, COL_HEI));

        state = new ChibiZunkoStateWait(this);
        ld = ImageLoader.getInstance();
    }

    public void update(){
        ChibiZunkoState newState = state.execute();
        if(newState != null){
            state = newState;
        }
    }
    public void chase(FieldBaseObject target){
        if(state.getStateName() == ChibiZunkoState.StateName.CHASE_TARGET) return;

        this.target = target;
        state = new ChibiZunkoStateChase(this);
    }
    public FieldBaseObject getChaseTarget(){
        return target;
    }
    public void tryUnsetChasing(){
        if(target != null) {
            target = null;
            state = new ChibiZunkoStateWait(this);
        }
    }

    public void activateAttackState(FieldBaseObject target){
        if(state.getStateName() == ChibiZunkoState.StateName.ATTACK) return;

        this.target = target;
        state = new ChibiZunkoStateAttack(this);
    }
    public void tryEndAttackState(){
        if(target != null) {
            target = null;
            state = new ChibiZunkoStateWait(this);
        }
    }

    public boolean isLeftFace(){
        return leftFace;
    }
    public void setDirection(boolean left){
        leftFace = left;
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
