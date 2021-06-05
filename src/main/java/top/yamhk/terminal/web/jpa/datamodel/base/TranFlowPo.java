package top.yamhk.terminal.web.jpa.datamodel.base;

import lombok.Data;
import top.yamhk.boot.MatrixTable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: YamHK
 * @Date: 2020/9/7 16:07
 */
@Data
@Entity
@MatrixTable(name = "cm_base_tran_flow")
public class TranFlowPo {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 请求时间
     */
    private Date reqTime;
    /**
     * 请求参数
     */
    private String requestBody;
}
