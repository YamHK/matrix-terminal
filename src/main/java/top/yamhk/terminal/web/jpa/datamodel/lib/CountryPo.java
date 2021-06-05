package top.yamhk.terminal.web.jpa.datamodel.lib;

import lombok.Data;
import top.yamhk.boot.MatrixTable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author: YamHK
 * @Date: 2020/8/21 1:31
 */
@Data
@Entity
@MatrixTable(name = "cm_lib_code_county")
public class CountryPo {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
