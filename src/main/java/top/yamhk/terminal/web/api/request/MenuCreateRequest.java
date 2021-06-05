package top.yamhk.terminal.web.api.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: YamHK
 * @Date: 2020/8/30 13:53
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class MenuCreateRequest extends BaseSrcCreateRequest {
    /**
     * 上级菜单编号
     */
    @NotNull
    private Long originId;
    /**
     * 菜单名称
     */
    @NotBlank
    private String menuName;
    /**
     * 菜单路径
     */
    @NotBlank
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
}
