package top.yamhk.terminal.web.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.sql.Blob;

/**
 * @Author: YamHK
 * @Date: 2019/11/3 21:44
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class PictureCreateRequest extends BaseSrcCreateRequest {
    /**
     * 归属相册
     */
    @NonNull
    @ApiModelProperty("归属相册")
    private String albumId;
    /**
     * 图片名称
     */
    @NotEmpty
    @ApiModelProperty("图片名称")
    private String picName;
    /**
     * 图片内容
     */
    @ApiModelProperty("图片内容-base64")
    private String picStr;
    /**
     * 图片内容
     */
    @ApiModelProperty("图片内容-Blob")
    private Blob picData;
    /**
     * 图片地址-外链
     */
    @ApiModelProperty("图片地址-外链")
    private String picUrl;
}
