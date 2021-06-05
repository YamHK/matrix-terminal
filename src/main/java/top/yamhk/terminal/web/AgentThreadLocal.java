package top.yamhk.terminal.web;

import top.yamhk.core.kernel.BeanCopyUtils;

/**
 * @Author: YamHK
 * @Date: 2019/5/31 21:23
 */
public class AgentThreadLocal {
    /**
     * 线程本地
     */
    private static final ThreadLocal<UserInfo> LOCAL = ThreadLocal.withInitial(UserInfo::new);

    /**
     * 防止实例化
     */
    private AgentThreadLocal() {
    }

    /**
     * 添加
     *
     * @param userInfo 用户信息
     */
    public static void set(UserInfo userInfo) {
        LOCAL.set(userInfo);
    }

    /**
     * 获取
     *
     * @return UserInfo
     */
    public static UserInfo get() {
        return LOCAL.get();
    }

    /**
     * 移除
     */
    public static void remove() {
        LOCAL.remove();
    }

    /**
     * 获取用户Po信息
     *
     * @return Player
     */
    public static Player convertToPo() {
        return BeanCopyUtils.copyByGson(get(), Player.class);
    }
}
