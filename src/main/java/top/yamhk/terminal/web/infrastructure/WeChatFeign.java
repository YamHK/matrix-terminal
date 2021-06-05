package top.yamhk.terminal.web.infrastructure;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: YamHK
 * @Date: 2020/8/12 9:02
 */
//@FeignClient(name = "we-chat", url = "https://api.weixin.qq.com")
public interface WeChatFeign {

    /**
     * 获取公众号关注用户
     *
     * @return String
     */
    @GetMapping("/cgi-bin/user/get?access_token={0}&next_openid=")
    String getUsers();

    @Data
    class UserResponse {

    }
}
