package com.yuda.tools.tabletoplantuml.converter;

import com.alibaba.druid.sql.ast.SQLDataType;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLNumericLiteralExpr;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlUnique;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.yuda.tools.tabletoplantuml.bean.ColumnInfo;
import com.yuda.tools.tabletoplantuml.bean.KeyInfo;
import com.yuda.tools.tabletoplantuml.bean.TableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * CreateUser: canyuda
 * CreateTime: 2019/10/30 20:33
 * Description:
 */
public class TableConverter {
    public static TableInfo convertToTableInfo(String createTableSql) {
        TableInfo result = new TableInfo();

        List<ColumnInfo> columnInfos = new ArrayList<>();
        KeyInfo primaryKeyInfo = new KeyInfo();
        List<KeyInfo> uniqKeyInfos = new ArrayList<>();
        List<KeyInfo> idxKeyInfos = new ArrayList<>();

        MySqlStatementParser parser = new MySqlStatementParser(createTableSql);
        SQLCreateTableStatement statement = parser.parseCreateTable();
        result.setTableName(statement.getName().getSimpleName());
        if (statement.getComment() != null) {
            result.setTableComment(statement.getComment().toString());
        } else {
            result.setTableComment("'无注释'");
        }
        // 临时记录主键
        List<SQLTableElement> tableElementList = statement.getTableElementList();
        for (SQLTableElement element : tableElementList) {
            // 字段
            if (element instanceof SQLColumnDefinition) {
                ColumnInfo columnInfo = getColumnInfo((SQLColumnDefinition) element);
                columnInfos.add(columnInfo);
            }
            if (element instanceof MySqlPrimaryKey) {
                // 主键
                primaryKeyInfo = getPrimaryKeyInfo((MySqlPrimaryKey) element);
            } else if (element instanceof MySqlUnique) {
                // 唯一键
                KeyInfo uniqKey = getUniqKeyInfo((MySqlUnique) element);
                uniqKeyInfos.add(uniqKey);
            } else if (element instanceof MySqlKey) {
                // 普通键
                KeyInfo idxKey = getIdxKeyInfo((MySqlKey) element);
                idxKeyInfos.add(idxKey);
            }
        }
        for (ColumnInfo columnInfo : columnInfos) {
            if (primaryKeyInfo.getKeyCols() != null && primaryKeyInfo.getKeyCols().contains(columnInfo.getColName())) {
                columnInfo.setPrimaryKey(true);
            }
        }
        result.setColumnInfos(columnInfos);
        result.setPrimaryKey(primaryKeyInfo);
        result.setUniqKeys(uniqKeyInfos);
        result.setIdxKeys(idxKeyInfos);
        return result;
    }

    private static KeyInfo getPrimaryKeyInfo(MySqlPrimaryKey element) {
        KeyInfo primaryKeyInfo = new KeyInfo();
        List<String> keyCols = new ArrayList<>(element.getColumns().size());
        for (int i = 0; i < element.getColumns().size(); i++) {
            SQLIdentifierExpr expr = (SQLIdentifierExpr) element.getColumns().get(i).getExpr();
            keyCols.add(expr.getName().replace("`", ""));
        }
        primaryKeyInfo.setKeyName("PRIMARY KEY");
        primaryKeyInfo.setKeyCols(keyCols);
        return primaryKeyInfo;
    }

    private static KeyInfo getIdxKeyInfo(MySqlKey element) {
        KeyInfo idxKey = new KeyInfo();
        MySqlKey key = element;
        idxKey.setKeyName(key.getName().toString());
        List<String> idxKeyCols = new ArrayList<>(key.getColumns().size());
        for (int i = 0; i < key.getColumns().size(); i++) {
            SQLIdentifierExpr expr = (SQLIdentifierExpr) key.getColumns().get(i).getExpr();
            idxKeyCols.add(expr.getName().replace("`", ""));
        }
        idxKey.setKeyCols(idxKeyCols);
        return idxKey;
    }

    private static KeyInfo getUniqKeyInfo(MySqlUnique element) {
        KeyInfo uniqKey = new KeyInfo();
        MySqlUnique uniqueKey = element;
        uniqKey.setKeyName(uniqueKey.getName().toString());
        List<String> uniqKeyCols = new ArrayList<>(uniqueKey.getColumns().size());
        for (int i = 0; i < uniqueKey.getColumns().size(); i++) {
            SQLIdentifierExpr expr = (SQLIdentifierExpr) uniqueKey.getColumns().get(i).getExpr();
            uniqKeyCols.add(expr.getName().replace("`", ""));
        }
        uniqKey.setKeyCols(uniqKeyCols);
        return uniqKey;
    }

    private static ColumnInfo getColumnInfo(SQLColumnDefinition element) {
        ColumnInfo columnInfo = new ColumnInfo();
        SQLColumnDefinition scd = element;
        columnInfo.setColName(scd.getName().toString());
        SQLDataType dataType = scd.getDataType();
        columnInfo.setDataTypeName(dataType.getName());
        for (int i = 0; i < dataType.getArguments().size(); i++) {
            if (i == 0) {
                SQLNumericLiteralExpr expr = (SQLNumericLiteralExpr) dataType.getArguments().get(i);
                columnInfo.setDataTypeArguments(expr.getNumber().toString());
            }
        }
        if (element.getDefaultExpr() != null) {
            columnInfo.setDefaultValue(element.getDefaultExpr().toString());
        }
        if (element.getOnUpdate() != null) {
            columnInfo.setOnUpdate(element.getOnUpdate().toString());
        }
        if (element.getComment() != null) {
            columnInfo.setComment(element.getComment().toString());
        }
        return columnInfo;
    }
}
