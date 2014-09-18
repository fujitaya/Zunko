package jp.fujitaya.zunko.field.zunko;

import android.graphics.Bitmap;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.field.FieldBaseObject;
import jp.fujitaya.zunko.util.ImageLoader;

class ChibiZunkoStateChase extends ChibiZunkoState {
    int imageId;
    float vx, vy;
    static final float VEL = 2;
    static final int FLIP_INTERVAL = 30;

    ChibiZunkoStateChase(ChibiZunko zunko){
        super(zunko);
        vx = vy = 0;
        flipImage();
    }

    @Override
    StateName getStateName(){return StateName.CHASE_TARGET;}

    @Override
    ChibiZunkoState execute(){
        ++counter;
        if(counter%FLIP_INTERVAL == 0) flipImage();

        FieldBaseObject target = zunko.getChaseTarget();

        float dx = target.getX() - zunko.getX();
        float dy = target.getY() - zunko.getY();

        double len = Math.sqrt(dx*dx+dy*dy);
        double ux = dx/len;
        double uy = dy/len;

        vx = (float)(ux*VEL);
        vy = (float)(uy*VEL);

        zunko.moveOffset(vx, vy);
        if(target.isOverwrapped(zunko.getX(), zunko.getY(),
                zunko.getX()+ChibiZunko.COL_WID,
                zunko.getY()+ChibiZunko.COL_HEI))
            return new ChibiZunkoStateWait(zunko);

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
}
