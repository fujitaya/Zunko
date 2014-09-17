package jp.fujitaya.zunko.hayashima;

import android.graphics.Bitmap;

import jp.fujitaya.zunko.util.ImageLoader;

abstract class ChibiZunkoState {
    public enum StateName{
        STAY, WAIT, RANDOM_WALK, LOOK_AROUND, APPROACH_TARGET,
        ATTACK, REST,
    };
    protected ChibiZunko zunko;
    protected int counter;

    ChibiZunkoState(ChibiZunko zunko){
        this.zunko = zunko;
        counter = 0;
    }
    abstract StateName getStateName();
    abstract ChibiZunkoState execute();
    abstract boolean interrupt(CaptureScene.PlayerOperation op);
    abstract Bitmap getImage(ImageLoader loader);
}
