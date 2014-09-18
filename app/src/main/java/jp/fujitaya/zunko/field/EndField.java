package jp.fujitaya.zunko.field;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import java.util.ArrayList;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.field.zunko.ChibiZunko;
import jp.fujitaya.zunko.util.ImageLoader;

public class EndField extends BasicField {
    /*int MAX_CREAT_NUM=100;
    int MAX_POWER_UP_NUM=100;
    int powerUpCount=0;
    int maxFieldHitPoint=0;
    //static int sumAttackPower=0;
    */
    public EndField(FieldData fd){
        super(fd);
    }
    /*void addOneAttackPower() {
        listMiniZunko.get((int) (Math.random() * listMiniZunko.size())).addAttackPower(1);
    }*/
}
