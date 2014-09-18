package jp.fujitaya.zunko.field.zunko;

import jp.fujitaya.zunko.R;

class ChibiZunkoStateAttack extends ChibiZunkoState {
    ChibiZunkoStateAttack(ChibiZunko zunko){
        super(zunko);
        imageId = R.drawable.cz_mochi01;
    }

    @Override
    StateName getStateName(){return StateName.ATTACK;}

    @Override
    ChibiZunkoState execute() {
        updateCounter();
        if(counter == 45){
            zunko.getChaseTarget().damage(zunko.getPower());
            zunko.damage(1);
        }
        if(counter==0 && zunko.getHP()==0)
            return new ChibiZunkoStateRest(zunko);

        return null;
    }

    void updateCounter(){
        ++counter;
        int id;
        if(zunko.isLeftFace()){
            if(counter < 20) id = R.drawable.cz_mochi01;
            else if(counter < 40) id = R.drawable.cz_mochi02;
            else if(counter < 60) id = R.drawable.cz_mochi03;
            else if(counter < 90) id = R.drawable.cz_mochi04;
            else id = R.drawable.cz_mochi05;
        }else{
            if(counter < 20) id = R.drawable.cz_mochi01_r;
            else if(counter < 40) id = R.drawable.cz_mochi02_r;
            else if(counter < 60) id = R.drawable.cz_mochi03_r;
            else if(counter < 90) id = R.drawable.cz_mochi04_r;
            else id = R.drawable.cz_mochi05_r;
        }
        if(counter >= 120) counter = 0;
        imageId = id;
    }
}
