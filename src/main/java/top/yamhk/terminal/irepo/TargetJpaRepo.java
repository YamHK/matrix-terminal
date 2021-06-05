package top.yamhk.terminal.irepo;

import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.jpa.datamodel.src.TargetPo;

/**
 * @Author: YamHK
 * @Date: 2019/4/14 19:03
 */
public interface TargetJpaRepo extends MatrixJpa<TargetPo> {

    /**
     * 查看-用户名下-日期
     *
     * @param userPo userPo
     * @param date   date
     * @return TargetPo
     */
    TargetPo findByPlayerAndDate(Player userPo, String date);
}
