package top.yamhk.terminal.web.api.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yamhk.core.base.utils.Dates;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @Author: YamHK
 * @Date: 2020/8/21 0:11
 */
@Data
class BaseSrcCreateRequest {
    /**
     * 编号
     */
    @ApiModelProperty("编号-选填")
    private Long id;
    /**
     * 更新时间
     */
    @JsonIgnore
    @ApiModelProperty("更新时间")
    private Date updateTime = Dates.now();
    /**
     * 类型
     */
    @NotEmpty
    @ApiModelProperty("类型-1:共有,2:私有")
    private String viewType = "2";
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark = "备注";
    /**
     * 测试标记
     */
    @ApiModelProperty("测试标记")
    private String testFlag = "0";
    /**
     * 预留字段
     */
    @ApiModelProperty("预留字段")
    private String reserve = "预留字段";
}
