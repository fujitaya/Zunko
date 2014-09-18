package jp.fujitaya.zunko.field.zunko;

import jp.fujitaya.zunko.R;

class ChibiZunkoStateSpawn extends ChibiZunkoState{
    ChibiZunkoStateSpawn(ChibiZunko zunko){
        super(zunko);
        imageId = R.drawable.ef_kemuri1;
    }

    @Override
    ChibiZunkoState.StateName getStateName(){return ChibiZunkoState.StateName.SPAWN;}

    @Override
    ChibiZunkoState execute(){
        ++counter;
        flipImage();

        if(counter == 30) return new ChibiZunkoStateWait(zunko);

        return null;
    }

    void flipImage(){
        if(counter == 10) imageId = R.drawable.ef_kemuri2;
        else if(counter == 20) imageId = R.drawable.ef_kemuri3;
    }
}
