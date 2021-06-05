package top.yamhk.terminal.irepo;

import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.jpa.datamodel.src.LocationPo;

import java.util.List;
import java.util.Optional;

/**
 * @Author: YamHK
 * @Date: 2019/4/14 19:03
 */
public interface LocationJpaRepo {

    /**
     * 用户名下所有位置
     *
     * @param userPo userPo
     * @return List
     */
    List<LocationPo> findByPlayer(Player userPo);

    LocationPo save(LocationPo po);

    Optional<LocationPo> findById(Long id);

    List<LocationPo> findAll();
}
