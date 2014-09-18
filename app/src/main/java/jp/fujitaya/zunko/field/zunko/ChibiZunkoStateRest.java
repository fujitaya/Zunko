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

        if(counter >= 155){
            zunko.rest();
        }

        return null;
    }

    void flipImage(){
        int id = imageId;
        if(counter == 20) id = R.drawable.cz_taberu04;
        else if(counter == 40) id = R.drawable.cz_taberu05;
        else if(counter == 70) id = R.drawable.cz_taberu06;
        else if(counter == 90) id = R.drawable.cz_taberu07;
        else if(counter == 110) id = R.drawable.cz_taberu06;
        else if(counter == 130) id = R.drawable.cz_taberu05;
        else if(counter == 140) id = R.drawable.ef_hikari3;
        else if(counter == 145) id = R.drawable.ef_hikari2;
        else if(counter == 150) id = R.drawable.ef_hikari1;
        imageId = id;
    }
}
