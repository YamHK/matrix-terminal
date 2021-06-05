package top.yamhk.terminal.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoUpdateRequest {
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;
    /**
     * 个签
     */
    @ApiModelProperty("个签")
    private String signature;
    /**
     * 头像-图片id
     */
    @ApiModelProperty("头像-图片id")
    private String avatar;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phoneNumber;
    /**
     * 身份证号码
     */
    @ApiModelProperty("身份证号码")
    private String identityCard;
    /**
     * 居住地
     */
    @ApiModelProperty("居住地")
    private String address;
    /**
     * 配置信息
     */
    @ApiModelProperty("配置信息")
    private String configJson;
}
