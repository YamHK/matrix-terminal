package top.yamhk.terminal.irepo;

import java.util.List;
import java.util.Optional;

/**
 * @Author: YamHK
 * @Date: 2021/5/22 22:25
 */
public interface MatrixJpa<T> {
    T save(T po);

    Optional<T> findById(long id);

    List<T> findAll();
}
