package top.yamhk.terminal.server4history;

import lombok.extern.slf4j.Slf4j;
import top.yamhk.core.base.utils.XcodeUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author xx252016
 * 1：注册驱动类
 * 2：创建数据库连接
 * 3：创建执行SQL的对象
 * 4：执行SQL，并获取返回结果
 * 5：处理返回结果，此处打印查询结果
 * 6：关闭数据库连接
 * 注册驱动(一次)
 * String driver_class = "com.mysql.jdbc.Driver";
 * String driver_class = "oracle.jdbc.driver.OracleDriver";
 * String driver_class = "org.logicalcobwebs.proxool.ProxoolDriver";
 * ①× DriverManager.registerDriver(new com.mysql.jdbc.Driver());
 * ②× System.setProperty("jdbc.drivers","com.mysql.jdbc.Driver");
 * ③√ Class.forName("com.mysql.jdbc.Driver");
 * ③√ Class.forName("oracle.jdbc.driver.OracleDriver");
 */
@Slf4j
public class DataSourceNormal {


    public static void main(String[] args) throws SQLException {
//        String sql = "select * from hk_base_user where username=?";
//        String[] args1 = {"admin"};
//        List<Map<String, String>> queryResult = new DataSourceNormal().getQueryResult(sql, args1);
//        log.warn("-->{}", queryResult);
        new DataSourceNormal().scan();
    }

    public List<Map<String, String>> getQueryResult(String sql, String[] args) throws SQLException {
        List<Map<String, String>> list = new ArrayList();
        /*获取数据库连接*/
        try (Connection conn = JdbcUtil.getDbConnection()) {
            PreparedStatement pst = Objects.requireNonNull(conn).prepareStatement(sql);
            // 参数注入
            for (int i = 0; i < args.length; i++) {
                pst.setString(i + 1, args[i]);
            }
            /* 执行结果 */
            ResultSet res = pst.executeQuery();
            // 得到结果集(rs)的结构信息，比如字段数、字段名等
            ResultSetMetaData resultSetMetaData = res.getMetaData();
            //保存表头信息
            Map<Integer, String> heard = new HashMap<>(5);
            // 返回此 ResultSet 对象中的列数
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                heard.put(i, resultSetMetaData.getColumnName(i));
                System.out.print(resultSetMetaData.getColumnName(i) + ((i < columnCount) ? "," : "\n"));
            }
            while (res.next()) {
                Map<String, String> result = new HashMap<>(5);
                for (int i = 1; i <= columnCount; i++) {
                    result.put(heard.get(i), res.getString(i));
                }
                list.add(result);
            }
            JdbcUtil.free(res, pst, conn);
            return list;
        }
    }

    public void scan() {
        // 1、获取数据库所有表
        StringBuffer sbTables = new StringBuffer();
        List<String> tables = new ArrayList();
        StringBuffer sbCloumns = new StringBuffer();
        sbTables.append("-------------- 数据库中有下列的表 ----------<br/>");
        try (Connection conn = XcodeUtils.getDbConnection(DriverManager::getConnection
                , JdbcUtil.properties.getProperty("spring.datasource.db_url")
                , JdbcUtil.properties.getProperty("spring.datasource.username")
                , JdbcUtil.properties.getProperty("spring.datasource.password"))
        ) {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            ResultSet rs = dbMetaData.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                sbTables.append("表名：" + rs.getString("TABLE_NAME") + "<br/>");
                sbTables.append("表类型：" + rs.getString("TABLE_TYPE") + "<br/>");
                sbTables.append("表所属数据库：" + rs.getString("TABLE_CAT") + "<br/>");
                sbTables.append("表所属用户名：" + rs.getString("TABLE_SCHEM") + "<br/>");
                sbTables.append("表备注：" + rs.getString("REMARKS") + "<br/>");
                sbTables.append("------------------------------<br/>");
                tables.add(rs.getString("TABLE_CAT") + "." + rs.getString("TABLE_NAME"));
            }
            log.info("table:{}", tables);
            // 2、遍历数据库表，获取各表的字段等信息
            for (String tableName : tables) {
                String sql = "select * from " + tableName;
                try {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    ResultSetMetaData meta = rs.getMetaData();
                    int columeCount = meta.getColumnCount();
                    sbCloumns.append("表 " + tableName + "共有 " + columeCount + " 个字段。字段信息如下：<br/>");
                    for (int i = 1; i < columeCount + 1; i++) {
                        sbCloumns.append("字段名：" + meta.getColumnName(i) + "<br/>");
                        sbCloumns.append("类型：" + meta.getColumnType(i) + "<br/>");
                        sbCloumns.append("------------------------------<br/>");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                sbCloumns.append("------------------------------<br/>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("table:{}", sbCloumns);
    }
}