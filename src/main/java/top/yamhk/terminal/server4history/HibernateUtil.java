package top.yamhk.terminal.server4history;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import top.yamhk.core.kernel.BeanUtils8Spring;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        /*默认加载classpath中的hibernate.cfg.xml*/
//        Configuration cfg = new Configuration().configure(new File("src/main/resources/jpa/hibernate.cfg.xml"));
//        sessionFactory = cfg.buildSessionFactory();
        sessionFactory = (SessionFactory) BeanUtils8Spring.getBean("sessionFactory");
        System.out.println("hibernate sessionFactory...");
    }

    public static Session openSession() {
        Session session = sessionFactory.openSession();
        return session;
    }

    public static void close(Session session) {
        if (null != session) {
            session.close();
        }
    }
}
