package top.yamhk.terminal.web.api.request;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.yamhk.core.base.MsgType;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

/**
 * @Author: YamHK
 * @Date: 2020/8/28 8:36
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class MsgCreateRequest extends BaseSrcCreateRequest {
    /**
     * 消息类型
     */
    @NotBlank
    @ApiModelProperty("消息类型")
    private String itemType = "1";
    /**
     * 父id
     */
    @ApiModelProperty("父id")
    private String parentId = "-1";
    /**
     * 发给谁
     */
    @NotBlank
    @ApiModelProperty("发给谁")
    private String addressee = "yingx";
    /**
     * 发送人
     */
    @NotBlank
    @ApiModelProperty("发送人")
    private String msgFrom;
    /**
     * 消息频道
     */
    @ApiModelProperty("消息频道")
    private String msgGroup;
    /**
     * 消息频道
     */
    @ApiModelProperty("消息类型-1:文字,2:图片")
    private String msgType = "1";
    /**
     * 消息字符集
     */
    @ApiModelProperty("消息字符集")
    private String charset = "utf-8";
    /**
     * 渲染
     */
    @ApiModelProperty("渲染")
    private String rendering = "渲染配置";
    /**
     * 消息内容
     */
    @ApiModelProperty("消息内容")
    @NotBlank
    private String content = "消息内容";

    public String buildMsgGroup() {
        ArrayList<String> msgGroup = Lists.newArrayList(this.msgFrom, this.addressee);
        if (this.itemType.equals(Integer.toString(MsgType.SINGLE2SINGLE.getCode()))) {
            msgGroup.sort(String::compareTo);
            return msgGroup.toString();
        } else {
            return this.msgGroup;
        }
    }

}
