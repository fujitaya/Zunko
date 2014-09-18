package jp.fujitaya.zunko.field;

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

    public boolean isCaptureField(){
        return false;
    }
    /*void addOneAttackPower() {
        listMiniZunko.get((int) (Math.random() * listMiniZunko.size())).addAttackPower(1);
    }*/
}
