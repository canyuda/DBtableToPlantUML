package com.yuda.tools.tabletoplantuml.bean;

/**
 * CreateUser: canyuda
 * CreateTime: 2019/10/30 16:53
 * Description:
 */
public class TableCreateInfo {
    private String tableName;
    private String createTable;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCreateTable() {
        return createTable;
    }

    public void setCreateTable(String createTable) {
        this.createTable = createTable;
    }

    @Override
    public String toString() {
        return "TableCreateInfo{" +
                "tableName='" + tableName + '\'' +
                ", createTable='" + createTable + '\'' +
                '}';
    }
}
