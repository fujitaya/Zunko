package jp.fujitaya.zunko.field.zunko;

import jp.fujitaya.zunko.R;

class ChibiZunkoStateRest extends ChibiZunkoState{

    ChibiZunkoStateRest(ChibiZunko zunko){
        super(zunko);

        imageId = R.drawable.cz_taberu03;
    }

    @Override
    ChibiZunkoState.StateName getStateName(){return ChibiZunkoState.StateName.REST;}

    @Override
    ChibiZunkoState execute(){
        ++counter;
        flipImage();

        if(counter >= 30){
            zunko.rest();
        }

        return null;
    }

    void flipImage(){

    }
}
