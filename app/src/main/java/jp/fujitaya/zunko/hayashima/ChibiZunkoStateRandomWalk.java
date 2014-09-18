package jp.fujitaya.zunko.hayashima;

import android.graphics.Bitmap;
import android.util.Log;


import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.ImageLoader;

class ChibiZunkoStateRandomWalk extends ChibiZunkoState{
    int imageId;
    private float vx, vy;
    int resetInterval;
    int time;
    ChibiZunkoStateRandomWalk(ChibiZunko zunko){
        super(zunko);
        counter = 0;
        time =  (int)(360.0*(Math.random()+0.5));
        resetInterval = (int)(60.0*(Math.random()+0.5));
        resetVel();
        flipImage();
    }

    @Override
    StateName getStateName(){return StateName.RANDOM_WALK;}

    @Override
    ChibiZunkoState execute(){
        ++counter;
        if (counter % resetInterval == 0){
            resetVel();
            resetTime();
            flipImage();
        }
        if (counter >= time){
            return new ChibiZunkoStateLookAround(zunko);
        }
        zunko.moveOffset(vx, vy);
        return null;
    }

    @Override
    Bitmap getImage(ImageLoader loader){
        return loader.load(imageId);
    }

    void flipImage(){
        boolean left = true;
        if(vx >= 0) left =false;
        boolean sel = zunko.isSelected();

        zunko.setDirection(left);
        if(left && sel){
            if(imageId==R.drawable.cz_aruku01_s)
                imageId = R.drawable.cz_aruku02_s;
            else
                imageId = R.drawable.cz_aruku01_s;
        }else if(left && !sel){
            if(imageId==R.drawable.cz_aruku01)
                imageId = R.drawable.cz_aruku02;
            else
                imageId = R.drawable.cz_aruku01;
        }else if(!left && sel){
            if(imageId==R.drawable.cz_aruku01_s_r)
                imageId = R.drawable.cz_aruku02_s_r;
            else
                imageId = R.drawable.cz_aruku01_s_r;
        }else{
            if(imageId==R.drawable.cz_aruku01_r)
                imageId = R.drawable.cz_aruku02_r;
            else
                imageId = R.drawable.cz_aruku01_r;
        }
    }

    private void resetVel(){
        vx = (float)(Math.random() * 2f-1f);
        vy = (float)(Math.random() * 2f-1f);
    }
    private void resetTime(){
        resetInterval = (int)(60.0*(Math.random()+0.5));
    }
}
