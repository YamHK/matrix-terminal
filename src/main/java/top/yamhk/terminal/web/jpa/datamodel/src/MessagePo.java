package top.yamhk.terminal.web.jpa.datamodel.src;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yamhk.boot.MatrixTable;
import top.yamhk.core.kernel.BaseSrcPo;
import top.yamhk.core.kernel.KernelNode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author YamHK
 * @Date: 2019/5/9 17:45
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@MatrixTable(name = "cm_src_msg")
public class MessagePo extends BaseSrcPo {
    /**
     * 消息编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 消息类型
     */
    private String itemType;
    /**
     * 消息类型
     */
    private String parentId;
    /**
     * 发给谁
     */
    private String addressee;
    /**
     * 发送人
     */
    private String msgFrom;
    /**
     * 消息频道
     */
    private String msgGroup;
    /**
     * 消息字符集
     */
    private String charset;
    /**
     * 渲染
     */
    private String rendering;
    /**
     * 消息内容
     */
    private String content;
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

    @Override
    public boolean visible(KernelNode kernelNode) {
        boolean notDelete = "0".equals(this.delFlag) || delFlag == null;
        boolean userData = false;
        boolean admin = "admin".equals(kernelNode.getUsername());
        boolean common = "1".equals(this.viewType);
        return notDelete && (common || admin || userData);
    }
}
