package top.yamhk.terminal.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.yamhk.terminal.web.LoginInterceptor;

/**
 * @Author: YamHK-WebMvcConfigurerAdapter deprecated
 * @Date: 2019/4/14 19:03
 */
@Slf4j
@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    /**
     * 拦截器的加载时间在Spring上下文之前加载
     * Configuration problem: @Bean method 'getLoginInterceptor' must not be private or final
     *
     * @return HandlerInterceptor
     */
    @Bean
    HandlerInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*")
                .maxAge(3600)
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //原来你是这样的路径-apk or swagger
        //registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        //registry.addResourceHandler("/**").addResourceLocations("classpath:/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截器
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**")
                //apk-依然会被拦截,因为此时变成了error这个路径
                .excludePathPatterns("/www.apk")
                //登录注册(其他地址为了获取token而拦截
                .excludePathPatterns("/v1/users/**")
                //首页资源
                .excludePathPatterns("/index*")
                .excludePathPatterns("/_lib/*")
                //测试
                .excludePathPatterns("/v1/demo/**")
                //接入
                .excludePathPatterns("/v1/we-chat/**")
                //swagger
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/swagger-ui.html");
        log.debug("拦截器添加成功");
    }
}
