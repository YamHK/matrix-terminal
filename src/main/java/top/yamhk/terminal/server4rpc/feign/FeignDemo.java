package top.yamhk.terminal.server4rpc.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Param;
import feign.Request;
import feign.RequestLine;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
public class FeignDemo {
    /**
     *
     */
    @GetMapping
    public void test() {
        log.error("======================================");
        HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(new ObjectMapper());
        ObjectFactory<HttpMessageConverters> converter = () -> new HttpMessageConverters(jsonConverter);

        RemoteService service = Feign.builder()
                .encoder(new SpringEncoder(converter))
                .decoder(new SpringDecoder(converter))
                .options(new Request.Options(1000, 3500))
                .retryer(new Retryer.Default(5000, 5000, 3))
                .target(RemoteService.class, "http://www.baidu.com");
        String spring = service.getSearch("spring");
        log.warn(">>>" + spring);
    }

    /**
     * 接口定义
     */
    public interface RemoteService {
        @RequestLine("GET  /s?ie=utf-8&wd={name}")
        String getSearch(@Param(value = "name") String name);
    }

}
