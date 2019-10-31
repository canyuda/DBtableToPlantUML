package com.yuda.tools.tabletoplantuml.bean;

import java.util.List;

/**
 * CreateUser: canyuda
 * CreateTime: 2019/10/30 20:30
 * Description:
 */
public class KeyInfo {
    private String keyName;
    private List<String> keyCols;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName.replace("`","");
    }

    public List<String> getKeyCols() {
        return keyCols;
    }

    public void setKeyCols(List<String> keyCols) {
        this.keyCols = keyCols;
    }
}
