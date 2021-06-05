package top.yamhk.terminal.web.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author: YamHK
 * @Date: 2020/8/26 17:39
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class WageCreateRequest extends BaseSrcCreateRequest {
    /**
     * 账本类型
     */
    @ApiModelProperty("账本类型")
    private String itemType;
    /**
     * 收入标记
     */
    @ApiModelProperty("收入标记")
    private String incomeFlag;
    /**
     * 金额
     */
    @ApiModelProperty("金额")
    private BigDecimal amount;
    /**
     * 数据日期
     */
    @ApiModelProperty("数据日期")
    private String date;
}
