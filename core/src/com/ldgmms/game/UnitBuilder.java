package com.ldgmms.game;

public class UnitBuilder {
    public UnitParser.UnitObjectList unitList;
    public static GenericUnit createUnit(UnitParser.UnitObjectList unitList, int unitIndex, int team){ //will construct the unit which can then be assigned to a location or general army
        UnitParser.JsonUnit u = unitList.units.get(unitIndex);
        return new GenericUnit(u.getSpritePath(), u.getName(), u.getCutRes(), u.getPierceRes(), u.getPoisonRes(), u.getIceRes(), u.getFireRes(), u.getSlowRes(), u.getHpMax(), u.getMpMax(), u.getApMax(), team);

    }
}
