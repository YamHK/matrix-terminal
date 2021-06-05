package top.yamhk.terminal.server4history;

import lombok.extern.slf4j.Slf4j;
import top.yamhk.core.base.utils.XcodeUtils;
import top.yamhk.core.kernel.BeanUtils8Spring;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

/**
 * 1：注册驱动类
 * 2：创建数据库连接
 * 3：创建执行SQL的对象
 * 4：执行SQL，并获取返回结果
 * 5：处理返回结果，此处打印查询结果
 * 6：关闭数据库连接
 *
 * @author yingx
 */
@Slf4j
public class JdbcUtil {

    private static final String url = "jdbc:mysql://122.51.220.31:3306/xx252016?useUnicode=true&characterEncoding=utf8";
    private static final String username = "yamhk";
    private static final String password = "admin";
    static Properties properties = new Properties();
    /**
     * 定义数据源-normal,dbcp,c3p0,proxool
     */
    private static DataSource MyDataSource;
    private static long countConn = 0;

    static {
        Class<?> clazz = XcodeUtils.clazzFind("com.mysql.cj.jdbc.Driver");
        log.warn("Driver-类加载完毕:{}", Arrays.stream(Objects.requireNonNull(clazz).getAnnotations()).toArray());
        //加载配置
        try (FileInputStream fis = new FileInputStream("src/main/resources/application-core.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("flyusercenter加载配置失败", e);
        }
        log.warn("Properties-属性加载完毕:{}", properties.stringPropertyNames());
    }

    public static Connection getDbConnection() {
        return XcodeUtils.getDbConnection(DriverManager::getConnection
                , JdbcUtil.properties.getProperty("spring.datasource.db_url")
                , JdbcUtil.properties.getProperty("spring.datasource.username")
                , JdbcUtil.properties.getProperty("spring.datasource.password"));
    }

    /**
     * 获取数据库链接
     *
     * @return Connection
     */
    public static Connection getConnection() {
        try {
            MyDataSource = BeanUtils8Spring.getBean("dataSource", DataSource.class);
            return MyDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放数据库连接
     *
     * @param resultSet  resultSet
     * @param statement  statement
     * @param connection connection
     */
    public static void free(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            try {
                                connection.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        System.out.println("数据库连接关闭,获取次数-->" + ++countConn);
    }

    /* 释放数据库连接 */
    public static void freeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
