package top.yamhk.terminal.irepo;

import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.jpa.datamodel.src.AlbumPo;

import java.util.List;
import java.util.Optional;

/**
 * @author yingx
 */
public interface AlbumJpaRepo {
    /**
     * 通过相册编号查询相册
     *
     * @param albumId 相册编号
     * @return Optional
     */
    Optional<AlbumPo> findByAlbumId(String albumId);

    /**
     * 名下同名相册
     *
     * @param userPo    userPo
     * @param albumName albumName
     * @return TargetPo
     */
    AlbumPo findByPlayerAndAlbumName(Player userPo, String albumName);

    AlbumPo save(AlbumPo po);

    Optional<AlbumPo> findById(Long id);

    List<AlbumPo> findAll();

}
