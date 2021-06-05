package top.yamhk.terminal.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yamhk.terminal.auction.infrastructure.InternetProtocolFeignClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: YingX
 * @Date: 2021/4/22 3:33
 */
@Slf4j
@Service
public class UtilsService {
    /**
     * ip
     */
    @Autowired
    InternetProtocolFeignClient internetProtocolFeignClient;

    public String getIp() {
        Map<String, String> request = new HashMap<>();
        //other
        request.put("Connection", "keep-alive");
        request.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
        request.put("Accept", "*/*");
        request.put("Host", "2021.ip138.com");
        request.put("Sec-Fetch-Site", "same-site");
        request.put("Sec-Fetch-Mode", "navigate");
        request.put("Sec-Fetch-Dest", "iframe");
        request.put("Referer", "https://www.ip138.com/");
        try {
            String ip = internetProtocolFeignClient.getIp(request);
            String title = ip.contains("电信") ? "[电信]" : "[移动]"
                    + "-"
                    + ip.substring(ip.indexOf("title") + 14, ip.indexOf("/title") - 1)
                    + "-"
                    + ip.substring(ip.indexOf("来自") + 3, ip.indexOf("user.ip138.com") - 54);
            log.debug("IP查询:{}", title);
            return title;
        } catch (Exception e) {
            log.error("ip查询异常", e);
            return "ip查询异常";
        }
    }
}
