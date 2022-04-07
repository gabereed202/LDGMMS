package com.ldgmms.game;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class EquipmentData {
    private int key;
    private String name;
    private int job;
    private int statBoost;

    //constructor
    public EquipmentData(int key, String name, int job, int statBoost) {
        this.key = key;
        this.name = name;
        this.job = job;
        this.statBoost = statBoost;
    }

    public int getKey() {
        return key;
    }

    public String getEquipmentName() {
        return name;
    }

    public int getJob() {
        return job;
    }

    public int getStatBoost() {
        return statBoost;
    }
}
