package jp.fujitaya.zunko.field.zunko;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import jp.fujitaya.zunko.field.FieldBaseObject;
import jp.fujitaya.zunko.util.ImageLoader;

public class ChibiZunko extends FieldBaseObject {
    public static final int COL_WID = 100;
    public static final int COL_HEI = 100;

    private int power;
    private boolean selected;
    private boolean leftFace;
    private boolean alive;
    private FieldBaseObject target;

    private ChibiZunkoState state;
    private ImageLoader ld;

    public ChibiZunko(){
        super();
        power = 1;
        selected = false;
        leftFace = true;
        alive = true;
        target = null;

        setHP(2);
        setCollision(new RectF(0, 0, COL_WID, COL_HEI));

        state = new ChibiZunkoStateSpawn(this);
        ld = ImageLoader.getInstance();
    }

    public void setPower(int val){
        power = val;
    }
    public int getPower(){
        return power;
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
        if(state.getStateName() == ChibiZunkoState.StateName.ATTACK
                || state.getStateName() == ChibiZunkoState.StateName.REST)
            return;

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

    public void rest(){
        alive = false;
    }
    public boolean isRest(){
        return !alive;
    }

    private static RectF dr = new RectF();
    public void draw(Canvas canvas, float baseX, float baseY){
        Bitmap image = state.getImage(ld);

        dr.left = baseX + pos.x;
        dr.top = baseY + pos.y;
        dr.right = dr.left + image.getWidth() * power;
        dr.bottom = dr.top + image.getHeight() * power;
        canvas.drawBitmap(image, null, dr, null);
    }
}
