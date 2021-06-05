package top.yamhk.terminal.mail;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: YamHK
 * @Date: 2019/11/3 21:44
 */
@Api(value = "emails", tags = {"emails"}, description = "邮件发送")
@Slf4j
@RestController
@RequestMapping("/v1/emails")
public class EmailApi {
    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "发送邮件", notes = "单个")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "EmailCreateRequest", paramType = "body")
    @PostMapping("/single")
    public void saveEmail(@RequestBody EmailCreateRequest request) {
        log.warn("邮件发送接口调用" + new Date());
        log.warn("{},{},{}", request.getAddressee(), request.getSubject(), request.getContent());
        emailService.sendMail(request.getAddressee(), request.getSubject(), request.getContent());
    }
}
