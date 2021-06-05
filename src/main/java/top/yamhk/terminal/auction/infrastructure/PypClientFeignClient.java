package top.yamhk.terminal.auction.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 蚂蚁社区
 * https://gw-api.miaoleta.com
 * http://gw-api.1688mayi.net
 *
 * @Author: YingX
 * @Date: 2021/4/17 8:13
 */
@FeignClient(name = "pyp", url = "http://gw-api.1688mayi.net")
public interface PypClientFeignClient {
    /**
     * doSignIn
     *
     * @param request request
     * @return String
     */
    @GetMapping("/signin")
    String login(@RequestBody Map<String, String> request);

    /**
     * 获取用户信息
     *
     * @param header header
     * @return String
     */
    @GetMapping("/getinfo")
    String getUserInfo(@RequestHeader Map<String, String> header);

    /**
     * goodShow
     *
     * @param header  header
     * @param request request
     * @return String
     */
    @GetMapping("/goodshow?limit=20")
    String getGoodsByPage(@RequestHeader Map<String, String> header,
                          @RequestParam Map<String, String> request);

    /**
     * goodAuction
     *
     * @param header  header
     * @param request request
     * @return String
     */
    @PostMapping("/goodauction")
    String doAuction(@RequestHeader Map<String, String> header,
                     @RequestBody Map<String, String> request);
}
