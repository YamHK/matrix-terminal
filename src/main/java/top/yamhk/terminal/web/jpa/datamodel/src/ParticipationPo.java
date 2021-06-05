package top.yamhk.terminal.web.jpa.datamodel.src;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.yamhk.boot.MatrixTable;
import top.yamhk.core.kernel.BaseSrcPo;
import top.yamhk.core.kernel.KernelNode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: YamHK
 * @Date: 2020/10/8 11:15
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@MatrixTable(name = "cm_src_participation")
public class ParticipationPo extends BaseSrcPo {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 数据类型
     */
    private String itemType;
    /**
     * 描述
     */
    private String itemDesc;
    /**
     * 时间
     */
    private String date;
    /**
     * #########################-useless/useful
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
        return null;
    }

    @Override
    public boolean visible(KernelNode kernelNode) {
        return false;
    }
}
