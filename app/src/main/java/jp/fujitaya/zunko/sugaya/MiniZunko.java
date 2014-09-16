package jp.fujitaya.zunko.sugaya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;

enum miniZunkoState{wait,move,rest,attack;}
public class MiniZunko extends BasicObject{
    int createNumber=0;
    //state
    miniZunkoState nowState;
    boolean selectFlag=false;
    int actionPoint;
    int waitCounter=0;
    //move
    float addVect;
    PointF toVect;
    PointF dv=new PointF(0,0);

    //attack
    int attackBuildingNumber=-1;
    int attackPower;

    MiniZunko(ArrayList<Bitmap> image,PointF v){
        super(image,v);

        createNumber++;
        tatchSize=(int)Math.sqrt(image.get(0).getWidth()*image.get(0).getWidth()+image.get(0).getHeight()*image.get(0).getHeight())/2;

        addVect=10;
        toVect=vect;
        //2min
        actionPoint=60*120;

        attackPower=1;

        nowState=miniZunkoState.wait;

    }
    @Override
    public void init(){}

    @Override
    public void update(){
        stateAction();
        move();
    }

    void changeState(miniZunkoState state){
        nowState=state;
    }
    public miniZunkoState getNowState(){return nowState;}
    public boolean isSelect(){return selectFlag;}
    void setSelect(boolean flag){
        selectFlag=flag;
    }

    //moving minizunko
    void move(){

            vect.x =vect.x+ dv.x;
            vect.y =vect.y+ dv.y;

    }
    public void tatchToMove(PointF v_){
        nowState=miniZunkoState.move;
        toVect=v_;
        PointF vSingle=new PointF(v_.x-vect.x,v_.y-vect.y);
        float vec= (float) Math.sqrt(vSingle.x*vSingle.x+vSingle.y*vSingle.y);
        dv= new PointF(addVect * (vSingle.x / vec), addVect * (vSingle.y / vec));
    }
    public void resetDv(){
        dv=new PointF(0,0);
    }

    //action
    void stateAction(){
        if(nowState==miniZunkoState.wait) {
            if (waitCounter % 30 == 0) {
                dv = new PointF((float) (Math.random() * 2f-1f), (float) (Math.random() * 2f-1f));

            }
            if (waitCounter % 180 == 0) {
                dv = new PointF(0, 0);
            }
            waitCounter++;
        }
        if(nowState!=miniZunkoState.wait){
            actionPoint--;
        }

    }
    public int getActionPoint(){return actionPoint;}
    public void setAttackBuildingNumber(int n){attackBuildingNumber=n;}
    public int getAttackBuildingNumber(){return attackBuildingNumber;}
    public int getAttackPower(){return attackPower;}

    @Override
    public void draw(Canvas canvas){
        //selected
        if(selectFlag==true &&nowState!=miniZunkoState.wait)  {
            canvas.drawBitmap(listImage.get(2), vect.x - imageSize.x / 2, vect.y - imageSize.y / 2, null);
        }
        if(nowState==miniZunkoState.wait){
            canvas.drawBitmap(listImage.get(0), vect.x-imageSize.x/2, vect.y-imageSize.y/2, null);
        }
        else if(nowState==miniZunkoState.move){
            canvas.drawBitmap(listImage.get(1), vect.x-imageSize.x/2, vect.y-imageSize.y/2, null);
        }
        else if(nowState==miniZunkoState.attack){
            canvas.drawBitmap(listImage.get(3), vect.x-imageSize.x/2, vect.y-imageSize.y/2, null);
        }
        else if(nowState==miniZunkoState.rest){
            canvas.drawBitmap(listImage.get(4), vect.x-imageSize.x/2, vect.y-imageSize.y/2, null);
        }
        canvas.drawBitmap(listImage.get(0), -100, -100, null);
    }
}
