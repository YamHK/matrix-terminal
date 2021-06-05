package top.yamhk.terminal.web.api.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.yamhk.terminal.web.BaseSrcResponse;

import java.sql.Blob;

/**
 * @Author: YamHK
 * @Date: 2019/11/3 21:44
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class PictureResponse extends BaseSrcResponse {
    /**
     * 图片名称
     */
    private String picName;
    /**
     * 图片地址
     */
    private String picUrl;
    /**
     * 图片地址
     */
    private String picStr;
    /**
     * 图片内容
     */
    private Blob picData;
}
