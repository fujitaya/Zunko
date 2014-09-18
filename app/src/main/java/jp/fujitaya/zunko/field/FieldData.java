package jp.fujitaya.zunko.field;

import java.util.ArrayList;

public class FieldData {
    public String name;
    public int fieldImageId;
    public int fieldWidth;
    public int fieldHeight;
    public int initX;
    public int initY;

    public int maxZunkoExistNum;
    public int initialZunkoPower;
    public int initialZunkoNum;

    public ArrayList<BuildingData> buildings;
    public ArrayList<CreatorData> creators;

    public class ObjectData{
        public int imageId;
        public float scale;
        public float fieldX, fieldY;
    }
    public class BuildingData extends ObjectData{
        public int hp;
        public int maxHP;
    }
    public class CreatorData extends ObjectData{
        public int spawnTime;
        public float spawnRange;
    }

    public FieldData(){
        buildings = new ArrayList<BuildingData>();
        creators = new ArrayList<CreatorData>();
    }
    public void addBuildingData(BuildingData build){
        buildings.add(build);
    }
    public void addCreatorData(CreatorData creator){
        creators.add(creator);
    }

    public BuildingData createBuildingData(){
        BuildingData bd = new BuildingData();
        return bd;
    }
    public CreatorData createCreatorData(){
        CreatorData cd = new CreatorData();
        return cd;
    }
}
