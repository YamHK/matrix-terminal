package top.yamhk.terminal.web.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.yamhk.terminal.web.UserInfoPool;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * @Author: YamHK
 * @Date: 2020/9/1 18:05
 */
@Slf4j
@Component
public class LockTemplate {

    public void tryExecuteLock(String lockKey, long timeoutInMillis, Action action) {
        String value = UUID.randomUUID().toString();
        try {
            log.info("redisCallBack");
        } finally {
            try {
                if (UserInfoPool.REDIS_TEMPLATE.get(lockKey) != null) {
                    UserInfoPool.REDIS_TEMPLATE.cleanUp();
                }
            } catch (ExecutionException e) {
                log.error("清理异常", e);
            }
        }
    }

    /**
     * 动作
     */
    @FunctionalInterface
    public interface Action {
        /**
         * 执行动作
         */
        void execute();
    }
}
