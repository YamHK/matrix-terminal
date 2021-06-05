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

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@MatrixTable(name = "cm_src_msg_group")
public class MessageGroupPo extends BaseSrcPo {
    /**
     * 消息编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * 发给谁
     */
    private String addressee;
    /**
     * 消息分组
     */
    private String msgGroup;
    /**
     * 消息未读数量
     */
    private Integer msgCount;
    /**
     * 图片
     */
    private String groupIcon;
    /**
     * 分组名称
     */
    private String groupNick;
    /**
     * 分组状态
     */
    private String groupStatus;
    /**
     * 最近一条消息类型
     */
    private String lastType;
    /**
     * 最近一条消息内容
     */
    private String lastContent;

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
}
