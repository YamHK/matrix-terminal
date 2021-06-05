package top.yamhk.terminal.server4rpc.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: YamHK
 * @Date: 2020/9/24 16:44
 */
@Slf4j
public class RestTemplateTest {
    public static void main(String[] args) {
        //向qq获取信息
        RestTemplate restTemplate = new RestTemplate();
        String access_token = "";
        String openid = "";
        String uri = "https://graph.qq.com/user/get_user_info?access_token=" + access_token +
                "&oauth_consumer_key=oauth_consumer_key&openid=" + openid;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String strbody = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
        //解析nickname，以及头像
        log.warn("{}", strbody);
    }
}
