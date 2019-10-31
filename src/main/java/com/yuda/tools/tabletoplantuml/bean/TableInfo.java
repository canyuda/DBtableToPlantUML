package com.yuda.tools.tabletoplantuml.bean;

import java.util.List;

/**
 * CreateUser: canyuda
 * CreateTime: 2019/10/30 20:29
 * Description:
 */
public class TableInfo {

    private String tableName;
    private String tableComment;

    private List<ColumnInfo> columnInfos;
    private KeyInfo primaryKey;
    private List<KeyInfo> uniqKeys;
    private List<KeyInfo> idxKeys;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName.replace("`","");
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment.replace("`","");
    }

    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(List<ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
    }

    public KeyInfo getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(KeyInfo primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<KeyInfo> getUniqKeys() {
        return uniqKeys;
    }

    public void setUniqKeys(List<KeyInfo> uniqKeys) {
        this.uniqKeys = uniqKeys;
    }

    public List<KeyInfo> getIdxKeys() {
        return idxKeys;
    }

    public void setIdxKeys(List<KeyInfo> idxKeys) {
        this.idxKeys = idxKeys;
    }
}
