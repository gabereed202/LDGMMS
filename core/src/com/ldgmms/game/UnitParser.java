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
        private ArrayList<JsonUnit> units;
        public UnitObjectList(){
            this.units = new ArrayList<>();
        }
    }
    public static class JsonUnit{ //no constructor needed with how libgdx json parser works
        private String spritePath;
        private String name;
        private float cutRes;
        private float pierceRes;
        private float poisonRes;
        private float iceRes;
        private float fireRes;
        private float slowRes;
        private float hpMax;
        private float mpMax;
        private float apMax;
    }
    public static UnitObjectList loadFile() { //can add a list for heroes as well
        Json json = new Json();
        return json.fromJson(UnitObjectList.class, Gdx.files.internal("units.json"));
    }
}
