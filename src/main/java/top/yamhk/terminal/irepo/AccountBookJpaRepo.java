package top.yamhk.terminal.irepo;

import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.jpa.datamodel.src.AccountBookPo;

import java.util.List;
import java.util.Optional;

/**
 * @Author: YamHK
 * @Date: 2020/8/21 10:15
 */
public interface AccountBookJpaRepo {
    /**
     * 查询用户名下所有数据
     *
     * @param userPo  userPo
     * @param delFlag delFlag
     * @return List
     */
    List<AccountBookPo> findByPlayerAndDelFlag(Player userPo, String delFlag);

    /**
     * 按照月份查询
     *
     * @param userPo  userPo
     * @param month   month
     * @param delFlag delFlag
     * @return List
     */
    List<AccountBookPo> findByPlayerAndDateLikeAndDelFlag(Player userPo, String month, String delFlag);

    AccountBookPo save(AccountBookPo po);

    Optional<AccountBookPo> findById(Long id);
}
