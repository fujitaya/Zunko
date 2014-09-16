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
    boolean updateFlag=false;
    boolean selectFlag=false;
    int actionPoint;
    //move
    float addVect;
    Point toVect;
    PointF dv=new PointF(0,0);

    //draw
    Point imageSize;

    MiniZunko(ArrayList<Bitmap> image,Point v){
        super(image,v);

        createNumber++;
        //temp size 50
        tatchSize=50;

        addVect=10;
        toVect=vect;
        //2min
        actionPoint=60*120;

        nowState=miniZunkoState.wait;

        imageSize=new Point(image.get(0).getWidth(),image.get(0).getHeight());
    }
    @Override
    public void init(){}

    @Override
    public void update(){
        updateFlag=false;
        if(nowState==miniZunkoState.move){
            move();
        }
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
            vect.x += dv.x;
            vect.y += dv.y;
    }
    public void tatchToMove(Point v_){
        nowState=miniZunkoState.move;
        toVect=v_;
        PointF vSingle=new PointF(v_.x-vect.x,v_.y-vect.y);
        float vec= (float) Math.sqrt(vSingle.x*vSingle.x+vSingle.y*vSingle.y);
        dv= new PointF(addVect * (vSingle.x / vec), addVect * (vSingle.y / vec));
    }
    //action
    @Override
    public void draw(Canvas canvas){
        if(selectFlag==false) {
            canvas.drawBitmap(listImage.get(0), vect.x-imageSize.x/2, vect.y-imageSize.y/2, new Paint());
        }
        else{
            canvas.drawBitmap(listImage.get(1), vect.x-imageSize.x/2, vect.y-imageSize.y/2, new Paint());
        }
    }
}
