package top.yamhk.terminal.irepo;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yamhk.terminal.web.jpa.datamodel.src.PicturePo;

/**
 * @author yingx
 */
public interface PictureJpaRepo extends JpaRepository<PicturePo, Long> {
}
