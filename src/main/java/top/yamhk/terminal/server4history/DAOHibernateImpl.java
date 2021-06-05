package top.yamhk.terminal.server4history;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.yamhk.core.base.CommonDAO;

import java.io.Serializable;
import java.util.List;

public class DAOHibernateImpl implements CommonDAO {
    Logger logger = LoggerFactory.getLogger(DAOHibernateImpl.class);
    private Session session;
    //private HibernateTemplate hibernateTemplate;

    @Deprecated
    @Override
    public boolean doCreate(Object entity) {
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();
        Integer key = (Integer) (session.save(entity));
        tx.commit();
        session.close();
        return null != key || !"".equals(key);
    }

    @Override
    public Integer doNew(Object entity) {
        Integer key = null;
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Serializable bk = session.save(entity);
//            logger.error("↘↘↘???"+bk.toString());
            key = (Integer) bk;
            tx.commit();
        } catch (Exception ex) {
            logger.error(ex.toString());
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
        return doExecute(sql, args);
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
        return false;
    }

    @Override
    public boolean doExecute(String sql, String[] args) {
        return doExecute(sql, args);
    }

    @Override
    public List<?> getResultList(Class<?> entityClass, Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<?> getResultList(String hql) {
        session = HibernateUtil.openSession();
        List<?> list = session.createQuery(hql).list();
        session.close();
        return list;
    }

    @Override
    public List<?> getResultList(String sql, String[] args) {
        session = HibernateUtil.openSession();
        List<?> list = session.createQuery(sql).list();
        session.close();
        return list;
    }

    @Override
    public Object[][] getResultArray(String hql) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object[][] getResultArray(String sql, String[] args) {
        return getResultArray(sql, args);
    }

}
