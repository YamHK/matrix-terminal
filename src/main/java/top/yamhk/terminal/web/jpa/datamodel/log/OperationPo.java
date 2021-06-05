package top.yamhk.terminal.web.jpa.datamodel.log;

import lombok.Data;
import top.yamhk.boot.MatrixTable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author: YamHK
 * @Date: 2019/11/3 21:57
 */

@Data
@Entity
@MatrixTable(name = "cm_base_operation")
public class OperationPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String item_id;
    private String item_name;
}
