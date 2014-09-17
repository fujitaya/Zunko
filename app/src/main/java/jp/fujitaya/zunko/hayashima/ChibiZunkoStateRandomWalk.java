package jp.fujitaya.zunko.hayashima;

import android.graphics.Bitmap;


import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.ImageLoader;

class ChibiZunkoStateRandomWalk extends ChibiZunkoState{
    private float vx, vy;

    ChibiZunkoStateRandomWalk(ChibiZunko zunko){
        super(zunko);
        counter = 0;
        vx = vy = 0;
    }

    @Override
    StateName getStateName(){return StateName.RANDOM_WALK;}

    @Override
    ChibiZunkoState execute(){
        ++counter;
        if (counter % 30 == 0) {
            vx = (float)(Math.random() * 2f-1f);
            vy = (float)(Math.random() * 2f-1f);
        }
        if (counter % (int)(180.0*Math.random()+0.5) == 0) {
            return new ChibiZunkoStateLookAround(zunko);
        }
        zunko.moveOffset(vx, vy);
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
        int resId;
        if(vx >= 0){
            if(zunko.isSelected())
                resId = counter%20==0 ? R.drawable.cz_aruku01_s_r : R.drawable.cz_aruku02_s_r;
            else
                resId = counter%20==0 ? R.drawable.cz_aruku01_r : R.drawable.cz_aruku02_r;
        }else{
            if(zunko.isSelected())
                resId = counter%20==0 ? R.drawable.cz_aruku01_s : R.drawable.cz_aruku02_s;
            else
                resId = counter%20==0 ? R.drawable.cz_aruku01 : R.drawable.cz_aruku02;
        }
        return loader.load(resId);
    }
}
