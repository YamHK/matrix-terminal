package top.yamhk.terminal.web.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.yamhk.terminal.common.CommonConstant;

import java.util.HashMap;

/**
 * @Author: YamHK
 * @Date: 2020/9/1 18:21
 */


@Slf4j
@Component
@Lazy(false)
public class CleanMsgJob {
    /**
     * jdbc
     */
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * run
     */
    @Scheduled(cron = "0 0 0/5 * * ?")
    public void run() {
        log.warn("{}CleanJob job doing...", CommonConstant.LOG_START);
        String sql = "update cm_src_msg set del_flag=1 where msg_group is null";
        HashMap<String, Object> paramMap = new HashMap<>(1);
        int update = namedParameterJdbcTemplate.update(sql, paramMap);
        log.error("逻辑删除异常聊天记录:{}", update);
        log.warn("{}CleanJob job done...", CommonConstant.LOG_END);
    }
}
