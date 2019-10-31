package com.yuda.tools.tabletoplantuml.plantuml.dictionary;

/**
 * CreateUser: canyuda
 * CreateTime: 2019/10/30 17:30
 * Description:
 */
public class Template {

    public static final String TABLES_INFO = "'共有表格{tablesCount}张";


    public static final String HEADER = "@startuml 表设计\n" +
            "\n" +
            "'类图\n" +
            "!define Class(name) class name\n" +
            "!define Class(name,desc) class name as \"name\\ndesc\"\n" +
            "!define Interface(name,desc) interface name as \"name\\ndesc\"\n" +
            "\n" +
            "'数据表图\n" +
            "!define Table(name,desc) class name as \"name\\ndesc\" << (T,#FFFFFF) >>\n" +
            "!define MongoDB(name,desc) class name as \"name\\ndesc\" << (M,#FFAAAA) >>\n" +
            "!define primary_key(x) -<b>x</b>\n" +
            "!define unique(x) <color:green>x</color>\n" +
            "!define not_null(x) +<u>x</u>\n" +
            "!define can_null(x) #<u>x</u>\n" +
            "'数据表图-索引\n" +
            "!unquoted function index($type, $idx_name, $idx_items=\"unknown\")\n" +
            "!return $type + $idx_name + \" (\" + $idx_items + \")\"\n" +
            "!endfunction";

    public static final String REMARKS = "'这里填写表关联信息(需要修改)\n";

    public static final String FOOTER = "@enduml";

    public static final String TABLE_HEADER = "Table({tableName},\"{tableComment}\") {";

    public static final String TABLE_FOOTER = "}";

    public static final String FIELD_PRIVATE_KEY = "primary_key({fieldName})";

    public static final String FIELD_NOT_NULL = "not_null({fieldName})";

    public static final String FIELD_NULL = "can_null({fieldName})";

    public static final String INDEX_NULL = "index(\"{indexType}\",\"{indexName}\",\"{indexFieldNames}\")";

}
