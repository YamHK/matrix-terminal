package top.yamhk.terminal.web.jpa.datamodel.src;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @Date: 2019/4/14 19:03
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@MatrixTable(name = "hk_base_user")
public class UserPo extends BaseSrcPo {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 个签
     */
    private String signature;
    /**
     * 头像-X
     */
    private String avatar;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private Integer phoneNumber;
    /**
     * 身份证号码
     */
    private String identityCard;
    /**
     * 居住地
     */
    private String address;
    /**
     * 配置信息
     */
    private String configJson;
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

    /**
     * 角色编号
     * //级联保存、更新、删除、刷新;延迟加载。当删除用户，会级联删除该用户的所有文章
     * //拥有mappedBy注解的实体类为关系被维护端
     * //mappedBy="user_id"中的author是RolePo中的author属性
     */
//    @OneToMany(mappedBy = "userPo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<RolePo> rolePos;
    @Override
    public Object logicDelete() {
        this.delFlag = "1";
        return this;
    }

    public boolean visible(UserPo userPo) {
        return false;
    }
}
