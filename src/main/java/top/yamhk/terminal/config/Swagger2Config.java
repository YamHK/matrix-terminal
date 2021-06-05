package top.yamhk.terminal.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 新增对swagger.properties 的引入
 *
 * @Author: YamHK
 * @Date: 2019/4/14 19:03
 * <p>
 * 当访问swagger-ui.html时,html页面会发送四个请求:
 * 1./swagger-resources/configuration/ui   获取页面UI配置信息
 * 2./swagger-resources
 * 3./v2/api-docs                          获取接口配置信息
 * 4./swagger-resources/configuration/security     安全配置
 * @Author: YamHK
 * @Date: 2019/4/14 19:03
 */
@Slf4j
@EnableWebMvc
@Configuration
@EnableSwagger2
public class Swagger2Config {

    public Swagger2Config() {
        log.warn("[init]-Swagger2Config");
    }

    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Talk_To_Me>API")
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                /*麻痹包名要写对*/
                .apis(RequestHandlerSelectors.basePackage("top.yamhk.terminal.web.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        String url = "http://www.yamhk.top";
        return new ApiInfoBuilder()
                //页面标题
                .title("Representational State Transfer API")
                //创建人
                .contact(new Contact("YamHK", url, "www_yamhk_top@163.com"))
                //描述
                .description(" Talk to Me")
                //版本号
                .version("1.0")
                .termsOfServiceUrl("www.yamhk.top")
                .build();
    }
}

