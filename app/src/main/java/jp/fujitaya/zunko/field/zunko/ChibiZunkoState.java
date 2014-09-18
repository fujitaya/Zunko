package jp.fujitaya.zunko.field.zunko;

import android.graphics.Bitmap;

import jp.fujitaya.zunko.util.ImageLoader;

abstract class ChibiZunkoState {
    public enum StateName{
        STAY, SPAWN, WAIT, RANDOM_WALK, LOOK_AROUND, CHASE_TARGET,
        ATTACK, REST,
    };
    protected ChibiZunko zunko;
    protected int imageId;
    protected int counter;

    ChibiZunkoState(ChibiZunko zunko){
        this.zunko = zunko;
        imageId = -1;
        counter = 0;
    }
    Bitmap getImage(ImageLoader loader){
        return loader.load(imageId);
    }

    abstract StateName getStateName();
    abstract ChibiZunkoState execute();

}
