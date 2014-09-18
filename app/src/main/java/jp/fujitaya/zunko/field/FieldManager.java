package jp.fujitaya.zunko.field;

import java.util.HashMap;
import java.util.Map;

public class FieldManager {
    private static FieldManager instance;
    private Map<String, Field> fieldStore;

    private FieldManager(){
        fieldStore = new HashMap<String, Field>();
    }

    public static FieldManager getInstance(){
        if (instance == null) instance = new FieldManager();
        return instance;
    }

    public Field getField(String fieldName){
        if (!fieldStore.containsKey(fieldName))
            fieldStore.put(fieldName,new CaptureField(fieldName));
        return fieldStore.get(fieldName);
    }

    public void setEndField(String fieldName){
        Field f=fieldStore.get(fieldName);
        fieldStore.remove(fieldName);
        fieldStore.put(fieldName, new EndField(fieldName));
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
}
