package top.yamhk.terminal.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: YamHK
 * @Date: 2021/2/26 14:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoRequest {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户名
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 操作类型-0,1
     */
    private String opType;
    /**
     * 密钥
     */
    private String authorization;
    /**
     * list
     */
    private List<String> players;

    /**
     * 以管理员身份
     *
     * @return DemoRequest
     */
    public static DemoRequest asAdmin() {
        return DemoRequest.builder().username("18958258589").build();
    }

    /**
     * 超管
     *
     * @return boolean
     */
    public boolean isAdmin() {
        return "18958258589".equals(username);
    }

    public boolean isAdd() {
        return "1".equals(this.opType);
    }

    public boolean isDel() {
        return "0".equals(this.opType);
    }
}
