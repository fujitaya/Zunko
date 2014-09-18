package jp.fujitaya.zunko.field.zunko;

import android.graphics.Bitmap;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.field.zunko.ChibiZunko;
import jp.fujitaya.zunko.field.zunko.ChibiZunkoState;
import jp.fujitaya.zunko.util.ImageLoader;

public class ChibiZunkoStateAttack extends ChibiZunkoState {
    int imageId;
    ChibiZunkoStateAttack(ChibiZunko zunko){
        super(zunko);
        imageId = R.drawable.cz_mochi01;
    }

    @Override
    StateName getStateName(){return StateName.ATTACK;}

    @Override
    ChibiZunkoState execute() {
        updateCounter();
        if(counter == 45) zunko.getChaseTarget().damage(zunko.getPower());

        return null;
    }

    @Override
    Bitmap getImage(ImageLoader loader){
        return loader.load(imageId);
    }

    void updateCounter(){
        ++counter;
        int id;
//        if(zunko.isLeftFace()){
            if(counter < 20) id = R.drawable.cz_mochi01;
            else if(counter < 40) id = R.drawable.cz_mochi02;
            else if(counter < 60) id = R.drawable.cz_mochi03;
            else if(counter < 90) id = R.drawable.cz_mochi04;
            else id = R.drawable.cz_mochi05;
//        }else{
//
//        }
        if(counter >= 120) counter = 0;
        imageId = id;
    }
}
