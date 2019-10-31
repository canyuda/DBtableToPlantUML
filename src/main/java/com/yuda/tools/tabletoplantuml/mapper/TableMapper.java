package com.yuda.tools.tabletoplantuml.mapper;

import com.yuda.tools.tabletoplantuml.bean.TableCreateInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * CreateUser: canyuda
 * CreateTime: 2019/10/30 16:25
 * Description:
 */
@Mapper
public interface TableMapper {

    @Select("show create table ${tableName}")
    @Results({
            @Result(property = "tableName", column = "Table", javaType = String.class),
            @Result(property = "createTable", column = "Create Table", javaType = String.class)
    })
    TableCreateInfo findTableCreateInfo(@Param(value = "tableName") String tableName);
}
