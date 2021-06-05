package top.yamhk.terminal.server4history;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import top.yamhk.core.base.CommonDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DAONomalImpl implements CommonDAO {
    @Override
    public boolean doCreate(Object entity) {
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(entity);
            tx.commit();
        } catch (Exception ex) {
            if (null != tx) {
                tx.rollback();
            }
        } finally {
            HibernateUtil.close(session);
        }
        return false;
    }

    @Override
    public Integer doNew(Object entity) {
        Integer key = null;
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();
        try {
            key = (Integer) session.save(entity);
            tx.commit();
        } catch (Exception ex) {
            if (null != tx) {
                tx.rollback();
            }
        } finally {
            HibernateUtil.close(session);
        }
        return key;
    }

    @Override
    public boolean doCreate(String hql) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doCreate(String sql, String[] args) {
        return doExecute(sql, args);
    }

    @Override
    public boolean doRemove(Object entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doRemove(String hql) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doRemove(String sql, String[] args) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doUpdate(Object entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doUpdate(String hql) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doUpdate(String sql, String[] args) {
        return doExecute(sql, args);
    }

    @Override
    public boolean doExecute(String hql) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doExecute(String sql, String[] args) {
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            // 参数注入
            for (int i = 0; i < args.length; i++) {
                pst.setString(i + 1, args[i]);
            }
            /* 更改的条数 */
            int i = pst.executeUpdate();
            /* 判断是否执行成功 */
            return i != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(null, pst, conn);
        }
        return false;
    }

    @Override
    public List<?> getResultList(Class<?> entityClass, Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<?> getResultList(String hql) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<?> getResultList(String sql, String[] args) {
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement pst = null;
        ResultSet res = null;
        ResultSetMetaData rsmd;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            pst = conn.prepareStatement(sql);
            // 参数注入
            for (int i = 0; i < args.length; i++) {
                pst.setString(i + 1, args[i]);
            }
            /* 执行结果 */
            res = pst.executeQuery();
            // 得到结果集(rs)的结构信息，比如字段数、字段名等
            rsmd = res.getMetaData();
            // 返回此 ResultSet 对象中的列数
            int columnCount = rsmd.getColumnCount();
            while (res.next()) {
                Map<String, Object> rowData = new HashMap<String, Object>(16);
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(rsmd.getColumnName(i), res.getObject(i));
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(res, pst, conn);
        }
        return list;
    }

    @Override
    public Object[][] getResultArray(String hql) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object[][] getResultArray(String sql, String[] args) {
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement pst = null;
        ResultSet res = null;
        ResultSetMetaData rsmd;
        List<Object[]> list = new ArrayList<Object[]>();
        Object[] obj;
        int columnNum = 0, hangcount;
        try {
            pst = conn.prepareStatement(sql);
            // 参数注入
            for (int i = 0; i < args.length; i++) {
                pst.setString(i + 1, args[i]);
            }
            /* 执行结果 */
            res = pst.executeQuery();
            // 得到结果集(rs)的结构信息，比如字段数、字段名等
            rsmd = res.getMetaData();
            // 返回此 ResultSet对象中的列数
            columnNum = rsmd.getColumnCount();
            obj = new Object[columnNum + 1];
            obj[0] = "♂*♀";
            /* 添加表头信息 */
            for (int i = 1; i <= columnNum; i++) {
                obj[i] = rsmd.getColumnName(i);
            }
            list.add(obj);
            /* 遍历结果集 */
            hangcount = 1;
            while (res.next()) {
                obj = new Object[columnNum + 1];
                obj[0] = hangcount++;
                for (int i = 1; i <= columnNum; i++) {
                    obj[i] = res.getObject(i);
                }
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(res, pst, conn);
        }

        hangcount = list.size();
        Object[][] objs = new Object[hangcount][columnNum + 1];
        for (int i = 0; i < hangcount; i++) {
            objs[i] = list.get(i);
        }
        return objs;
    }
}
