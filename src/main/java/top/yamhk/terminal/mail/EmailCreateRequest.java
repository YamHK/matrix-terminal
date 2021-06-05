package top.yamhk.terminal.mail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: YamHK
 * @Date: 2019/11/3 21:44
 */
@Data
public class EmailCreateRequest {

    /**
     * 收件人
     */
    @ApiModelProperty("收件人")
    private String addressee = "xx252016@163.com";
    /**
     * 邮件主题
     */
    @ApiModelProperty("邮件主题")
    private String subject = "邮件推送测试标题";
    /**
     * 邮件内容
     */
    @ApiModelProperty("邮件内容")
    private String content = "helloWorld,java mail";
}
