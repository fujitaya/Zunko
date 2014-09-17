package jp.fujitaya.zunko.jimmy;

import java.util.HashMap;
import java.util.Map;

import jp.fujitaya.zunko.sugaya.Field;

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
        //if (!fieldStore.containsKey(fieldName))
        //    fieldStore.put(fieldName,new Field(fieldName));
        return fieldStore.get(fieldName);
    }

    public void update(){
        for (Map.Entry<String, Field> fieldEntry : fieldStore.entrySet()){
            fieldEntry.getValue().update();
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
