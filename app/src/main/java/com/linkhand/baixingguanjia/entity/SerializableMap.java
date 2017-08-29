package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by JCY on 2017/7/27.
 * 说明：
 */

public class SerializableMap implements Serializable {
    private Map<String,Object> map;
    private Map<Integer,GoodsTag.Guige> mGuigeMap;

    public SerializableMap(Map<Integer, GoodsTag.Guige> objectMap) {
        mGuigeMap = objectMap;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Map<Integer, GoodsTag.Guige> getGuigeMap() {
        return mGuigeMap;
    }

    public void setGuigeMap(Map<Integer, GoodsTag.Guige> guigeMap) {
        mGuigeMap = guigeMap;
    }
}
