package top.yamhk.terminal.web;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author: YamHK
 * @Date: 2019/11/4 19:47
 */
@Slf4j
@Component
public class UserInfoPool {
    /**
     * 在线用户池
     */
    public static final LoadingCache<String, Optional<UserInfo>> USER_CASH = CacheBuilder.newBuilder()
            .concurrencyLevel(8)
            .expireAfterAccess(24, TimeUnit.HOURS)
            //不需要刷新Exception thrown during refresh
            .refreshAfterWrite(24 * 60 * 60, TimeUnit.SECONDS)
            .initialCapacity(5)
            .maximumSize(1024)
            .recordStats()
            .build(new CacheLoader<String, Optional<UserInfo>>() {
                @Override
                public Optional<UserInfo> load(String key) {
                    log.error("缓存中没有key:[{}]", key);
                    return Optional.empty();
                }
            });
    /**
     * 在线用户池
     */
    public static final LoadingCache<String, String> REDIS_TEMPLATE = CacheBuilder.newBuilder()
            .concurrencyLevel(8)
            .expireAfterAccess(12, TimeUnit.HOURS)
            //不需要刷新Exception thrown during refresh
            .refreshAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
            .initialCapacity(5)
            .maximumSize(1024)
            .recordStats()
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    log.error("缓存中没有key:[{}]", key);
                    return null;
                }
            });
}
