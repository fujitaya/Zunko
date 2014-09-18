package jp.fujitaya.zunko.field;

import java.util.ArrayList;

public class FieldData {
    public String name;
    public int fieldImageId;
    public int fieldWidth;
    public int fieldHeight;

    public int maxZunkoExistNum;
    public float initialZunkoPower;
    public int initialZunkoNum;

    public ObjectData creator;
    public ArrayList<BuildingData> buildings;

    public class ObjectData{
        public int imageId;
        public float scale;
        public float fieldX, fieldY;
    }
    public class BuildingData extends ObjectData{
        public int hp;
    }

    public FieldData(){
        creator = new ObjectData();
        buildings = new ArrayList<BuildingData>();
    }
    public BuildingData createBuildingData(){
        BuildingData bd = new BuildingData();
        buildings.add(bd);
        return bd;
    }
}
