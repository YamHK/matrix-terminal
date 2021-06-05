package top.yamhk.terminal.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: YamHK
 * @Date: 2019/11/4 20:51
 */
@Data
public class RegisterRequest {
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;
}
