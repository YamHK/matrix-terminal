package top.yamhk.terminal.web.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: YamHK
 * @Date: 2019/5/22 23:30
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class TargetCreateRequest extends BaseSrcCreateRequest {
    /**
     * 类型
     */
    @NotEmpty
    @ApiModelProperty("类型-1:倒计时,2:正计时")
    private String itemType;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String itemDesc;
    /**
     * 日期
     */
    @NotEmpty
    @ApiModelProperty("日期")
    private String date;
}
