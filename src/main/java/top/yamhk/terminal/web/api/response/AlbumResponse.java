package top.yamhk.terminal.web.api.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.yamhk.terminal.web.BaseSrcResponse;

/**
 * @Author: YamHK
 * @Date: 2019/11/3 21:44
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class AlbumResponse extends BaseSrcResponse {
    /**
     * 相册编号
     */
    private String albumId;
    /**
     * 相册名称
     */
    private String albumName;
    /**
     * 图片数量
     */
    private String pictureNumber;
}
