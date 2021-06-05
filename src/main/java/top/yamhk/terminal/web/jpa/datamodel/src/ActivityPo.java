package top.yamhk.terminal.web.jpa.datamodel.src;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import top.yamhk.core.kernel.BaseSrcPo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: YamHK
 * @Date: 2020/9/9 16:48
 */

@Entity
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ActivityPo extends BaseSrcPo implements Serializable {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 源ID
     */
    private Long originId;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 活动渠道
     */
    private Long activityChannel;
    /**
     * 活动名称
     */
    private Long activityName;
    /**
     * 活动标题
     */
    private Long activityTitle;
    /**
     * 活动类型
     */
    private Long activityType;
    /**
     * 开始时间
     */
    private Long startTime;
    /**
     * 结束时间
     */
    private Long endTime;
    /**
     * 需要积分
     */
    private Long integral;

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
