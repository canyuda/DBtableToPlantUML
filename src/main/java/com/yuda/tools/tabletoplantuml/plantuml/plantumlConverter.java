package com.yuda.tools.tabletoplantuml.plantuml;

import com.alibaba.druid.util.StringUtils;
import com.yuda.tools.tabletoplantuml.bean.ColumnInfo;
import com.yuda.tools.tabletoplantuml.bean.KeyInfo;
import com.yuda.tools.tabletoplantuml.bean.TableInfo;
import com.yuda.tools.tabletoplantuml.plantuml.dictionary.Common;
import com.yuda.tools.tabletoplantuml.plantuml.dictionary.Template;

import java.util.List;

/**
 * CreateUser: canyuda
 * CreateTime: 2019/10/30 21:14
 * Description:
 */
public class plantumlConverter {
    public static String convertToPlantUmlString(List<TableInfo> tableInfos) {
        StringBuilder sb = new StringBuilder(Template.HEADER).append("\n\n\n");
        sb.append(Template.TABLES_INFO.replace(Common.TABLE_COUNT, String.valueOf(tableInfos.size()))).append("\n");
        for (TableInfo tableInfo : tableInfos) {
            sb.append(Template.TABLE_HEADER.replace(Common.tableName, tableInfo.getTableName()).replace(Common.tableComment, tableInfo.getTableComment())).append("\n");
            for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
                sb.append("\t");
                if (columnInfo.isPrimaryKey()) {
                    sb.append(Template.FIELD_PRIVATE_KEY.replace(Common.fieldName, columnInfo.getColName())).append(" ");
                } else if (!StringUtils.isEmpty(columnInfo.getDefaultValue()) && columnInfo.getDefaultValue().equals("NULL")) {
                    sb.append(Template.FIELD_NULL.replace(Common.fieldName, columnInfo.getColName())).append(" ");
                } else {
                    sb.append(Template.FIELD_NOT_NULL.replace(Common.fieldName, columnInfo.getColName())).append(" ");
                }
                sb.append(columnInfo.getDataTypeName());
                if (!StringUtils.isEmpty(columnInfo.getDataTypeArguments())) {
                    sb.append("[").append(columnInfo.getDataTypeArguments()).append("]").append(" ");
                } else {
                    sb.append(" ");
                }
                if (!StringUtils.isEmpty(columnInfo.getDefaultValue()) && !columnInfo.getDefaultValue().equals("NULL")) {
                    sb.append(columnInfo.getDefaultValue()).append(" ");
                }
                if (!StringUtils.isEmpty(columnInfo.getOnUpdate())) {
                    sb.append("on update").append(" ").append(columnInfo.getOnUpdate()).append(" ");
                }
                if (!StringUtils.isEmpty(columnInfo.getComment())) {
                    sb.append(columnInfo.getComment()).append(" ");
                }
                sb.append("\n");
            }
//            KeyInfo primaryKey = tableInfo.getPrimaryKey();
//
//            sb.append("\n\t").append(Template.INDEX_NULL.replace(Common.indexType, "-")
//                    .replace(Common.indexName, primaryKey.getKeyName())
//                    .replace(Common.indexFieldNames, primaryKey.getKeyCols().toString())).append("\n");
            sb.append("\n");
            for (KeyInfo uniqKey : tableInfo.getUniqKeys()) {
                sb.append("\t").append(Template.INDEX_NULL.replace(Common.indexType, "-")
                        .replace(Common.indexName, uniqKey.getKeyName())
                        .replace(Common.indexFieldNames, uniqKey.getKeyCols().toString().replace('[', ' ').replace(']', ' '))).append("\n");
            }
            for (KeyInfo idxKey : tableInfo.getIdxKeys()) {
                sb.append("\t").append(Template.INDEX_NULL.replace(Common.indexType, "+")
                        .replace(Common.indexName, idxKey.getKeyName())
                        .replace(Common.indexFieldNames, idxKey.getKeyCols().toString().replace('[', ' ').replace(']', ' '))).append("\n");
            }
            sb.append(Template.TABLE_FOOTER).append("\n\n");
        }
        sb.append("\n").append(Template.REMARKS).append("\n");
        for (int i = 0; i < tableInfos.size(); i++) {
            String direction = "down";
            if (i < tableInfos.size() - 1) {
                sb.append(tableInfos.get(i).getTableName()).append(" \"").append(i).append("\" <-").append(direction).append("-> \"").append(i).append("\" ").append(tableInfos.get(i + 1).getTableName()).append("\n");
            } else {
                sb.append(tableInfos.get(i).getTableName()).append(" \"").append(i).append("\" <-").append(direction).append("-> \"").append(i).append("\" ").append(tableInfos.get(0).getTableName()).append("\n");
            }
        }

        sb.append("\n").append(Template.REMARKS).append("\n\n").append(Template.FOOTER);
        return sb.toString();
    }
}
