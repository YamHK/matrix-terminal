package top.yamhk.terminal.auction.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

/**
 * pyp
 *
 * @Author: YingX
 * @Date: 2021/4/17 8:13
 */
@FeignClient(name = "ip", url = "https://2021.ip138.com/")
public interface InternetProtocolFeignClient {
    /**
     * doSignIn
     *
     * @param header header
     * @return String
     */
    @GetMapping
    String getIp(@RequestHeader Map<String, String> header);
}
