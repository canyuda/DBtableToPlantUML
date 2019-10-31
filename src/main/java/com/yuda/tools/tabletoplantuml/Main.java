package com.yuda.tools.tabletoplantuml;

import com.alibaba.druid.util.StringUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.yuda.tools.tabletoplantuml.bean.TableCreateInfo;
import com.yuda.tools.tabletoplantuml.bean.TableInfo;
import com.yuda.tools.tabletoplantuml.converter.TableConverter;
import com.yuda.tools.tabletoplantuml.plantuml.plantumlConverter;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * CreateUser: canyuda
 * CreateTime: 2019/10/30 16:09
 * Description:
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
        String tables = properties.getProperty("db.tables", "");
        String outPutPath = properties.getProperty("output.path", System.getProperty("user.dir"));
        String[] tableArr = tables.split(";");
        Set<String> tableArrSet = new HashSet<String>(Arrays.asList(tableArr));
        List<TableInfo> tableInfos = new ArrayList<>(tableArr.length);
        for (String tableName : tableArrSet) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
            SqlSession sqlSession = sqlSessionFactory.openSession();
            TableCreateInfo tableCreateInfo = null;
            try {
                tableCreateInfo = sqlSession.selectOne("findTableCreateInfo", tableName);
            } catch (Exception e) {
                if (e instanceof MySQLSyntaxErrorException) {
                    System.out.println("not found table:" + tableName);
                }
            }
            if (tableCreateInfo != null) {
                String createTable = tableCreateInfo.getCreateTable();
                TableInfo tableInfo = TableConverter.convertToTableInfo(createTable);
                tableInfos.add(tableInfo);
            } else {
                System.out.println(tableName + "not found");
            }
        }
        String plantuml = plantumlConverter.convertToPlantUmlString(tableInfos);
        if (!StringUtils.isEmpty(plantuml)) {
            Files.write(Paths.get(outPutPath), plantuml.getBytes());
            System.out.println("已生成文件" + outPutPath);
        }
    }
}
