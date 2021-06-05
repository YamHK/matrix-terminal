package top.yamhk.terminal.web.jpa.datamodel.src;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yamhk.boot.MatrixTable;
import top.yamhk.core.kernel.BaseSrcPo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: YamHK
 * @Date: 2019/11/3 22:17
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@MatrixTable(name = "cm_src_account_book")
public class AccountBookPo extends BaseSrcPo {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 归属用户
     */
    /**
     * 账本类型
     */
    private String itemType;
    /**
     * 账本类型
     */
    private String incomeFlag;
    /**
     * 金额
     */
    private Double amount;
    /**
     * 数据日期
     */
    private String date;
    /**
     * 日期
     */
    private String dataDate;
    /**
     * #########################-useless/useful
     * 状态
     */
    private String status;
    /**
     * view类型-系统权限
     */
    private String viewType;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 删除标记
     */
    private String delFlag;
    /**
     * 预留字段
     */
    private String reserve;

    @Override
    public Object logicDelete() {
        this.delFlag = "1";
        return this;
    }
}
