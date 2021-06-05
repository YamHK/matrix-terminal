package top.yamhk.terminal.web.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: YamHK
 * @Date: 2019/11/3 21:44
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class AlbumCreateRequest extends BaseSrcCreateRequest {
    /**
     * 相册名称
     */
    @ApiModelProperty("相册名称")
    private String albumName;
}

