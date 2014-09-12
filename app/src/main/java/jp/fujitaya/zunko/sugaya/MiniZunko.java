package jp.fujitaya.zunko.sugaya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class MiniZunko extends BasicObject{
    MiniZunko(ArrayList<Bitmap> listImage){
        super(listImage);
    }
    int count=0;
    boolean updateFlag=false;
    @Override
    public void update(){
        updateFlag=false;
        move();
    }
    //moving minizunko
    void move(){
        if(count%2==0){
            x++;
            updateFlag=true;
        }
    }
    @Override
    public void init(){}
    @Override
    public void draw(Canvas canvas){
        if(updateFlag==true){
            canvas.drawBitmap(listImage.get(0),x,y,new Paint());
        }
        else{
            canvas.drawBitmap(listImage.get(1),x,y,new Paint());
        }
    }
}
