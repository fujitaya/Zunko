package jp.fujitaya.zunko.hayashima;

import android.graphics.Bitmap;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.ImageLoader;

class ChibiZunkoStateLookAround extends ChibiZunkoState{
    private static final int LOOK_INTERVAL = 20;
    private int lookCount;

    ChibiZunkoStateLookAround(ChibiZunko zunko){
        super(zunko);
        lookCount = (int)(Math.random()*3);
    }

    @Override
    StateName getStateName(){return StateName.LOOK_AROUND;}

    @Override
    ChibiZunkoState execute(){
        ++counter;

        if(counter%LOOK_INTERVAL == LOOK_INTERVAL-1){
            --lookCount;
            if(lookCount <= 0) return new ChibiZunkoStateRandomWalk(zunko);
        }

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
        return loader.load(lookCount%2==0 ? R.drawable.cz_miwatasu01 : R.drawable.cz_miwatasu02);
    }
}
