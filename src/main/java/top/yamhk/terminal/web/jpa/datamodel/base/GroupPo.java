package top.yamhk.terminal.web.jpa.datamodel.base;

import lombok.Data;
import top.yamhk.boot.MatrixTable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author: YamHK
 * @Date: 2019/5/23 22:58
 */
@Data
@Entity
@MatrixTable(name = "hk_base_group")
public class GroupPo {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
