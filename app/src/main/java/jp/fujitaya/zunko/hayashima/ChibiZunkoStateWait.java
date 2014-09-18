package jp.fujitaya.zunko.hayashima;

import android.graphics.Bitmap;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.ImageLoader;

class ChibiZunkoStateWait extends ChibiZunkoState{
    private static final int MAX_WAIT_TIME = 100;



    ChibiZunkoStateWait(ChibiZunko zunko){
        super(zunko);
    }

    @Override
    StateName getStateName(){return StateName.WAIT;}

    @Override
    ChibiZunkoState execute(){
        ++counter;
        if(counter >= (int)(Math.random()*MAX_WAIT_TIME))
            return new ChibiZunkoStateRandomWalk(zunko);

        return null;
    }

    @Override
    boolean interrupt(CaptureScene.PlayerOperation op){
        switch(op){
            case SELECT:
                return true;
            default: break;
        }
        return false;
    }

    @Override
    Bitmap getImage(ImageLoader loader){
        return loader.load(zunko.isSelected()?R.drawable.cz_tatsu_s:R.drawable.cz_tatsu);
    }
}
