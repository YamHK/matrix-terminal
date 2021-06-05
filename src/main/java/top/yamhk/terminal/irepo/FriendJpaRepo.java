package top.yamhk.terminal.irepo;

import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.jpa.datamodel.src.FriendPo;

import java.util.List;
import java.util.Optional;

/**
 * @author YamHK
 */
public interface FriendJpaRepo {
    /**
     * 明细
     *
     * @param userPo   userPo
     * @param friendId friendId
     * @return FriendPo
     */
    FriendPo findByPlayerAndFriendId(Player userPo, String friendId);

    FriendPo save(FriendPo po);

    Optional<FriendPo> findById(Long id);

    List<FriendPo> findAll();

}
