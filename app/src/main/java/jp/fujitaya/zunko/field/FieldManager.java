package jp.fujitaya.zunko.field;

import java.util.HashMap;
import java.util.Map;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.field.zunko.StatusAll;

public class FieldManager {
    public static synchronized FieldManager getInstance(){
        if (instance == null) instance = new FieldManager();
        return instance;
    }

    public Field getField(String fieldName){
        if (!fieldStore.containsKey(fieldName)) {
            FieldData fd = createFD(fieldName);
            fieldStore.put(fieldName, new CaptureField(fd));
        }
        return fieldStore.get(fieldName);
    }
    public void setEndField(String fieldName){
        Field f = fieldStore.get(fieldName);
        fieldStore.remove(fieldName);
        fieldStore.put(fieldName, new EndField(f.getFieldData()));
    }
    public void updateToEndField(){
        for (Map.Entry<String, Field> fieldEntry : fieldStore.entrySet()){
            Field f = fieldEntry.getValue();
            if(f.isCaptureField() && f.getNowHP() <= 0){
                setEndField(f.getFieldName());
            }
        }
    }

    public boolean isWorking(String fieldName){
        return fieldStore.containsKey(fieldName);
    }
    public StatusAll getStatusAll(){
        StatusAll statusAll = new StatusAll();
        double totalScore = 0.0;
        long zunkoAll = 0;
        int stageNum = 0;
        int clearedNum = 0;

        for (Map.Entry<String, Field> fieldEntry : fieldStore.entrySet()){
            Field field = fieldEntry.getValue();
            totalScore += 1.0-((double)field.getNowHP()/field.getInitialHP());
            zunkoAll += field.getTotalZunkoNum();
            stageNum++;
            if (field.getNowHP() == 0) clearedNum++;
        }
        statusAll.cleared = clearedNum;
        statusAll.zunkoAll = zunkoAll;
        if (stageNum == 0) statusAll.progress = 0.0;
        else statusAll.progress = totalScore / stageNum;

        return statusAll;
    }

    public void update(){

        for (Map.Entry<String, Field> fieldEntry : fieldStore.entrySet()){
            fieldEntry.getValue().update();
        }
        //change Field to EndField
        /*for (Map.Entry<String, Field> fieldEntry : fieldStore.entrySet()){
            if(fieldEntry.getValue().getNowHP() <= 0){
                setEndField(fieldEntry.getValue().getFieldName());
            }
        }*/
    }

    public void save(){
        //TODO;現在のフィールドデータを保存
    }

    public void release(){
        fieldStore.clear();
        fieldStore = null;
        instance = null;
    }

    private static FieldManager instance;
    private Map<String, Field> fieldStore;

    private FieldManager(){
        fieldStore = new HashMap<String, Field>();
    }
    private FieldData createFD(String name){
        FieldData fd = new FieldData();

        fd.name = name;
        fd.fieldImageId = R.drawable.fd_green;
        fd.fieldWidth = 720*2+50;
        fd.fieldHeight = (int)(1280*1.5)+50;
        fd.initX = -50;
        fd.initY = -50;

        fd.maxZunkoExistNum = 200;
        fd.initialZunkoPower = 1;
        fd.initialZunkoNum = 5;

        FieldData.CreatorData cd = fd.createCreatorData();
        cd.imageId = R.drawable.cz_zunda;
        cd.scale = 3;
        cd.fieldX = 150;
        cd.fieldY = 150;
        cd.spawnTime = 60*5;
        cd.spawnRange = 300;
        fd.addCreatorData(cd);

        FieldData.BuildingData bd = fd.createBuildingData();
        bd.imageId = R.drawable.mc_mig;
        bd.hp = 2;//180;
        bd.maxHP = 180;
        bd.scale = 4;
        bd.fieldX = 550;
        bd.fieldY = 550;
        fd.addBuildingData(bd);

        bd = fd.createBuildingData();
        bd.imageId = R.drawable.mc_mig;
        bd.hp = 2;//180;
        bd.maxHP = 180;
        bd.scale = 4;
        bd.fieldX = 250;
        bd.fieldY = 850;
        fd.addBuildingData(bd);

        return fd;
    }
}
