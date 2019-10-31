package com.yuda.tools.tabletoplantuml.plantuml;

public enum indexType {
    idx("KEY", "+"),
    uniq("UNIQUE", "-");

    private String typeName;
    private String sign;

    indexType(String typeName, String sign) {
        this.typeName = typeName;
        this.sign = sign;
    }
}
