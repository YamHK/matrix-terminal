package top.yamhk.terminal.web.prepare;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: YamHK
 * @Date: 2020/8/13 10:16
 */
@RestController
@RequestMapping("/v1/alipay")
@Slf4j
public class DemoAlipayApi {
    /**
     * 首页
     *
     * @return String
     */
    @GetMapping
    public String accessGetAlipay() {
        return "hello-world-1";
    }

    /**
     * 首页
     *
     * @return String
     */
    @PostMapping
    public String accessPostAlipay(String request) {
        return "hello-world-2";
    }
}
