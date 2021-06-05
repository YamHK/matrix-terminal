package top.yamhk.terminal.web.jpa.datamodel.log;

import lombok.Data;
import top.yamhk.boot.MatrixTable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author YamHK
 * @Date: 2019/5/9 17:45
 */
@Data
@Entity
@MatrixTable(name = "cm_log_operate", schema = "xx252016")
public class OperatePo {
    @Id
    private int operId;
    private Integer loginId;
    private String visitUrl;
    private String dateCreate;
}
