package top.yamhk.terminal.web.prepare;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: YamHK
 * @Date: 2020/8/11 15:46
 */
@RestController
@RequestMapping("/v1/we-chat")
@Slf4j
public class DemoWeChatApi {
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        inputStream.close();
        inputStream = null;
        return map;
    }

    /**
     * 接入微信公众平台
     *
     * @return String
     */
    @ApiOperation(value = "接入", notes = "接入微信公众平台")
    @GetMapping("/access")
    public String access(AccessRequest request) {
        log.warn("收到微信公众平台参数：" + request);
        return request.getEchostr();
    }

    /**
     * 响应微信公众号
     *
     * @return String
     */
    @ApiOperation(value = "响应", notes = "响应微信公众号")
    @PostMapping("/access")
    public String access(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.warn("收到微信公众平台参数：" + request);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, String> parseXml = parseXml(request);
        String msgType = parseXml.get("MsgType");
        String content = parseXml.get("Content");
        String from = parseXml.get("FromUserName");
        String to = parseXml.get("ToUserName");
        System.out.println(msgType);
        System.out.println(content);
        System.out.println(from);
        System.out.println(to);
        return "";
    }

    /**
     * @return String
     */
    @GetMapping("/test")
    public String test() {
        String openIdList = GetOpenIdList(null, "36_wm9hlyHXr03Z8MdqEjHrW6gloLXvrOKx1Uq3tuwkYlGMVYWxxxtfmTAoYabWBYREGYjPws9E2G3volbfWo4TKbJe7tq-k8E3f-gHJBbPzU6EG7RxGM8H9rRPFAl3sXKLi3FNbZFDWACsKlQFQNOcAGAZWX");
        log.warn("列表" + openIdList);
        return "test end...";
    }

    /// <summary>
/// 获取关注者列表openid
/// </summary>
/// <param name="nextopenid"></param>
/// <param name="access_token"></param>
/// <returns></returns>
    public String GetOpenIdList(String nextopenid, String access_token) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + access_token;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return forEntity.toString();

    }

    /**
     * 微信公众平台接入请求
     */
    @Data
    private static class AccessRequest {
        /**
         * 微信加密签名
         */
        private String signature;
        /**
         * 时间戳
         */
        private String timestamp;
        /**
         * 随机数
         */
        private String nonce;
        /**
         * 随机字符串
         */
        private String echostr;
    }
}
