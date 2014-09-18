package jp.fujitaya.zunko.field.zunko;

import android.graphics.Bitmap;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.ImageLoader;

class ChibiZunkoStateLookAround extends ChibiZunkoState {
    private static final int LOOK_INTERVAL = 20;
    private int lookCount;

    ChibiZunkoStateLookAround(ChibiZunko zunko){
        super(zunko);
        lookCount = (int)Math.random()*3+1;
    }

    @Override
    StateName getStateName(){return StateName.LOOK_AROUND;}

    @Override
    ChibiZunkoState execute(){
        ++counter;

        if(counter%LOOK_INTERVAL == 0){
            --lookCount;
            if(lookCount <= 0)
                return new ChibiZunkoStateRandomWalk(zunko);
        }

        return null;
    }

    @Override
    Bitmap getImage(ImageLoader loader){
        int id = lookCount%2==0 ? R.drawable.cz_miwatasu01 : R.drawable.cz_miwatasu02;
        if(id == R.drawable.cz_miwatasu01) zunko.setDirection(false);
        else zunko.setDirection(true);
        return loader.load(id);
    }
}
