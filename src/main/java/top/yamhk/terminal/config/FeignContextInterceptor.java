package top.yamhk.terminal.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: YingX
 * @Date: 2021/2/23 19:31
 */
@Component
public class FeignContextInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("accessToken", "yingx");
    }
}
