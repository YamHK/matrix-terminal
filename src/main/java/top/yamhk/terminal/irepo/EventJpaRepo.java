package top.yamhk.terminal.irepo;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yamhk.terminal.web.jpa.datamodel.src.EventPo;

/**
 * @Author: YamHK
 * @Date: 2020/8/21 10:15
 */
public interface EventJpaRepo extends JpaRepository<EventPo, Long> {
}
