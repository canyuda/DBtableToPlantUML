package com.yuda.tools.tabletoplantuml.bean;

/**
 * CreateUser: canyuda
 * CreateTime: 2019/10/30 20:30
 * Description:
 */
public class ColumnInfo {
    private String ColName;
    private String dataTypeName;
    private String dataTypeArguments;
    private String defaultValue;
    private String OnUpdate;
    private String comment;
    private boolean isPrimaryKey;

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public String getColName() {
        return ColName;
    }

    public void setColName(String colName) {
        ColName = colName.replace("`","");
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName.replace("`","");
    }

    public String getDataTypeArguments() {
        return dataTypeArguments;
    }

    public void setDataTypeArguments(String dataTypeArguments) {
        this.dataTypeArguments = dataTypeArguments.replace("`","");
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue.replace("`","");
    }

    public String getOnUpdate() {
        return OnUpdate;
    }

    public void setOnUpdate(String onUpdate) {
        OnUpdate = onUpdate.replace("`","");
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment.replace("`","");
    }
}
