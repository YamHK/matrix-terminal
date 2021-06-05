package top.yamhk.terminal.web.jpa.datamodel.lib;

import lombok.Data;
import top.yamhk.boot.MatrixTable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author: YamHK
 * @Date: 2020/8/21 9:12
 */
@Data
@Entity
@MatrixTable(name = "cm_lib_nickname")
public class NickNamePo {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickType;
    private String nickShot;
    private String nickName;
}
