package top.yamhk.terminal.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: YingX
 * @Date: 2021/2/23 19:53
 */
@Configuration
public class FeignClientConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.NONE;
    }
}
