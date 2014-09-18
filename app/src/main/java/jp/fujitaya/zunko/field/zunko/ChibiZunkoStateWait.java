package jp.fujitaya.zunko.field.zunko;

import android.graphics.Bitmap;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.ImageLoader;

class ChibiZunkoStateWait extends ChibiZunkoState {
    private static final int BASE_WAIT_TIME = 60;
    private int waitTime;

    ChibiZunkoStateWait(ChibiZunko zunko){
        super(zunko);
        flipImage();
        waitTime = (int)(Math.random()*1.5*BASE_WAIT_TIME);
    }

    @Override
    StateName getStateName(){return StateName.WAIT;}

    @Override
    ChibiZunkoState execute(){
        ++counter;
        if(counter >= waitTime) return new ChibiZunkoStateRandomWalk(zunko);

        flipImage();
        return null;
    }

    void flipImage(){
        imageId = zunko.isSelected() ? R.drawable.cz_tatsu_s : R.drawable.cz_tatsu;
    }
}
