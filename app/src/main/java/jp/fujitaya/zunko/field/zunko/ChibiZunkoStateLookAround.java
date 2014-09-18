package jp.fujitaya.zunko.field.zunko;

import jp.fujitaya.zunko.R;

class ChibiZunkoStateLookAround extends ChibiZunkoState {
    private static final int LOOK_INTERVAL = 20;
    private int lookCount;

    ChibiZunkoStateLookAround(ChibiZunko zunko){
        super(zunko);
        lookCount = (int)Math.random()*3+1;
        flipImage();
    }

    @Override
    StateName getStateName(){return StateName.LOOK_AROUND;}

    @Override
    ChibiZunkoState execute(){
        ++counter;

        if(counter%LOOK_INTERVAL == 0){
            --lookCount;
            flipImage();
            if(lookCount <= 0)
                return new ChibiZunkoStateRandomWalk(zunko);
        }

        return null;
    }

    void flipImage(){
        int id = lookCount%2==0 ? R.drawable.cz_miwatasu01 : R.drawable.cz_miwatasu02;
        if(id == R.drawable.cz_miwatasu01) zunko.setDirection(false);
        else zunko.setDirection(true);
        imageId = id;
    }
}
