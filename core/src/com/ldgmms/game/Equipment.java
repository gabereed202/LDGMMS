package com.ldgmms.game;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class Equipment {
    public Map<Integer, EquipmentData> map;
    public Equipment(){
        this.map = new HashMap <Integer, EquipmentData>();
    }
    public void addEquipment(int key, String name, int job, int statBoost){
        EquipmentData newEquipment = new EquipmentData(key, name, job, statBoost);
        this.map.put(newEquipment.getKey(), newEquipment);
        return;
    }

}

