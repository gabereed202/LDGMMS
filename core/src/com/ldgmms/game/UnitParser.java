package com.ldgmms.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;


public class UnitParser { //example usage: UnitParser.UnitObjectList buildableUnits = UnitParser.loadFile();
    public static class UnitObjectList{
        public ArrayList<JsonUnit> units;
        public UnitObjectList(){
            this.units = new ArrayList<>();
        }
    }
    public static class JsonUnit{ //no constructor needed with how libgdx json parser works
        public String getSpritePath() {
            return spritePath;
        }

        public String getName() {
            return name;
        }

        public int getCutRes() {
            return cutRes;
        }

        public int getPierceRes() {
            return pierceRes;
        }

        public int getPoisonRes() {
            return poisonRes;
        }

        public int getIceRes() {
            return iceRes;
        }

        public int getFireRes() {
            return fireRes;
        }

        public int getSlowRes() {
            return slowRes;
        }

        public float getHpMax() {
            return hpMax;
        }

        public float getMpMax() {
            return mpMax;
        }

        public int getApMax() {
            return apMax;
        }

        private String spritePath;
        private String name;
        private int cutRes;
        private int pierceRes;
        private int poisonRes;
        private int iceRes;
        private int fireRes;
        private int slowRes;
        private int hpMax;
        private int mpMax;
        private int apMax;
    }
    public static UnitObjectList loadFile() { //can add a list for heroes as well
        Json json = new Json();
        return json.fromJson(UnitObjectList.class, Gdx.files.internal("units.json"));
    }
}
