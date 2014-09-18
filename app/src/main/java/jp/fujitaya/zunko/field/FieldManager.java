package jp.fujitaya.zunko.field;

import java.util.HashMap;
import java.util.Map;

import jp.fujitaya.zunko.R;

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
        Field f=fieldStore.get(fieldName);
        fieldStore.remove(fieldName);
        FieldData fd = createFD(fieldName);
        fieldStore.put(fieldName, new EndField(fd));
    }

    public boolean isWorking(String fieldName){
        return fieldStore.containsKey(fieldName);
    }

    public void update(){

        for (Map.Entry<String, Field> fieldEntry : fieldStore.entrySet()){
            fieldEntry.getValue().update();
        }
        //change Field to EndField
        for (Map.Entry<String, Field> fieldEntry : fieldStore.entrySet()){
            if(fieldEntry.getValue().getNowHP() == 0){
                setEndField(fieldEntry.getValue().getFieldName());
            }
        }
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
        fd.fieldWidth = 720*2;
        fd.fieldHeight = (int)(1280*1.5);

        fd.maxZunkoExistNum = 200;
        fd.initialZunkoPower = 1;
        fd.initialZunkoNum = 10;

        FieldData.CreatorData cd = fd.createCreatorData();
        cd.imageId = R.drawable.ic_launcher;
        cd.scale = 1;
        cd.fieldX = 100;
        cd.fieldY = 100;

        FieldData.BuildingData bd = fd.createBuildingData();
        bd.imageId = R.drawable.mc_mig;
        bd.hp = 180;
        bd.scale = 4;
        bd.fieldX = 500;
        bd.fieldY = 500;

        bd = fd.createBuildingData();
        bd.imageId = R.drawable.mc_mig;
        bd.hp = 180;
        bd.scale = 4;
        bd.fieldX = 200;
        bd.fieldY = 800;

        return fd;
    }
}
