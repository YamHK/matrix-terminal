package top.yamhk.terminal.web;

/**
 * @Author: YamHK
 * @Date: 2019/5/31 21:23
 */
public class AuthThreadLocal {
    /**
     * 线程本地
     */
    private static final ThreadLocal<String> LOCAL = ThreadLocal.withInitial(String::new);

    /**
     * 防止实例化
     */
    private AuthThreadLocal() {
    }

    /**
     * 添加
     *
     * @param str str
     */
    public static void set(String str) {
        LOCAL.set(str);
    }

    /**
     * 获取
     *
     * @return UserInfo
     */
    public static String get() {
        return LOCAL.get();
    }

    /**
     * 移除
     */
    public static void remove() {
        LOCAL.remove();
    }
}
