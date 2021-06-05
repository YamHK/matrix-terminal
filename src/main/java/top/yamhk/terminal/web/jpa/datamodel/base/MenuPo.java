package top.yamhk.terminal.web.jpa.datamodel.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.yamhk.boot.MatrixTable;
import top.yamhk.core.kernel.BaseSrcPo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: YamHK
 * @Date: 2019/11/3 21:47
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@MatrixTable(name = "hk_base_menu")
public class MenuPo extends BaseSrcPo {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 上级菜单编号
     */
    private Long originId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单路径
     */
    private String menuUrl;
    /**
     * 菜单优先级
     */
    private String menuLevel;
    /**
     * 菜单排序
     */
    private String menuSort;
    /**
     * 菜单图标
     */
    private String menuIcon;
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
