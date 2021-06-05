package top.yamhk.terminal.web;

import lombok.Data;

/**
 * @Author: YamHK
 * @Date: 2019/5/23 22:14
 */
@Data
public class UserInfoResponse {
    /**
     * 用户名
     */
    private String username;
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
    private Long avatar;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 居住地
     */
    private String address;
    /**
     * 配置信息
     */
    private String configJson;

    /**
     * 脱敏
     */
    public UserInfoResponse desensitize() {
        this.email = "***";
        this.phoneNumber = "***";
        this.address = "***";
        return this;
    }
}
