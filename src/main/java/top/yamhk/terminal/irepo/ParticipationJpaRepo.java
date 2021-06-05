package top.yamhk.terminal.irepo;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yamhk.terminal.web.jpa.datamodel.src.ParticipationPo;

import java.util.List;

/**
 * @Author: YamHK
 * @Date: 2020/10/8 11:51
 */
public interface ParticipationJpaRepo extends JpaRepository<ParticipationPo, Long> {

    /**
     * 查询聊天记录
     *
     * @param username username
     * @return List
     */
    List<ParticipationPo> findByCreateBy(String username);
}
