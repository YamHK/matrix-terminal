package top.yamhk.terminal.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import top.yamhk.core.kernel.BaseSrcPo;
import top.yamhk.core.kernel.KernelNode;

/**
 * @Author: YingX
 * @Date: 2021/4/18 14:40
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class User extends BaseSrcPo {
    /**
     * 积分
     */
    protected double integral;
    /**
     * 账户
     */
    @JsonProperty("phone")
    protected String username;
    /**
     * token
     */
    @JsonIgnore
    @JsonProperty("token")
    private String authorization;
    /**
     * uid
     */
    private String uid;
    /**
     * 昵称
     */
    @JsonProperty("nikename")
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机
     */
    private int phoneNumber;
    /**
     * 银行卡信息
     */
    private Bank bank;

    @Override
    public boolean visible(KernelNode userPo) {
        return false;
    }


    @Data
    public static class Bank {
        /**
         * 真名
         */
        @JsonProperty("b_uname")
        private String realName;
    }
}
