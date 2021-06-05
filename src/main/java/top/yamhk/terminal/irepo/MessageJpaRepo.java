package top.yamhk.terminal.irepo;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yamhk.terminal.web.jpa.datamodel.src.MessagePo;

import java.util.List;

/**
 * @Author: YamHK
 * @Date: 2020/8/21 10:15
 */
public interface MessageJpaRepo extends JpaRepository<MessagePo, Long> {
    /**
     * 根据消息分组查询消息
     *
     * @param msgGroup msgGroup
     * @return List
     */
    List<MessagePo> findByMsgGroup(String msgGroup);
}
