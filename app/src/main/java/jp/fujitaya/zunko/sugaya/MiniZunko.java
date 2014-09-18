package jp.fujitaya.zunko.sugaya;

import android.graphics.Canvas;
import android.graphics.PointF;

import java.util.HashMap;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.Image;

public class MiniZunko extends BasicObject{
    static enum ImageName{wait,move,rest,attack;}
    HashMap<ImageName,Integer> mapImage;
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
    int movingAttackBuildNumber=-1;
    int attackPower;

    MiniZunko(/*ArrayList<Bitmap> image,*/PointF v){
        super(/*image,*/v);

        mapImage = new HashMap<ImageName, Integer>();
        mapImage.put(ImageName.wait, R.drawable.cz_miwatasu01);
        mapImage.put(ImageName.move, R.drawable.cz_aruku01);
        mapImage.put(ImageName.rest, R.drawable.cz_suru01);
        mapImage.put(ImageName.attack, R.drawable.cz_mochi01);
        image = new Image(mapImage.get(ImageName.wait));
        image.setCenter();
        image.setPosition(vect);


        createNumber++;
        tatchSize=(int)Math.sqrt(image.getWidth()*image.getWidth()+image.getHeight()*image.getHeight());
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
        changeDrawImage();
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
    public void setMovingAttackBuildingNumber(int n){movingAttackBuildNumber=n;}
    public int getMovingAttackBuildingNumber(){return movingAttackBuildNumber;}
    public void addAttackPower(int p){attackPower+=p;}
    public int getAttackPower(){return attackPower;}

    void changeDrawImage(){
        //selected
        if(selectFlag && nowState!=miniZunkoState.wait)  {
            image.changeImage(mapImage.get(ImageName.wait));
        }
        if(nowState==miniZunkoState.wait){
            image.changeImage(mapImage.get(ImageName.wait));
        }
        else if(nowState==miniZunkoState.move){
            image.changeImage(mapImage.get(ImageName.move));
        }
        else if(nowState==miniZunkoState.attack){
            image.changeImage(mapImage.get(ImageName.attack));
        }
        else if(nowState==miniZunkoState.rest){
            image.changeImage(mapImage.get(ImageName.rest));
        }

    }
    @Override
    public void draw(Canvas canvas){
        image.draw(canvas);
    }
    @Override public void disposeImage(){
        mapImage.clear();
        image=null;
    }
}
