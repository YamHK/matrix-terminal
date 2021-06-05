package top.yamhk.terminal.config;

import org.hibernate.dialect.MySQL55Dialect;

/**
 * @Author: YamHK
 * @Date: 2020/9/7 14:17
 */
public class DbConfig extends MySQL55Dialect {
    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
