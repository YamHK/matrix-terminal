package top.yamhk.terminal.web.jpa.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: YamHK
 * @Date: 2019/11/3 21:44
 */
@Data
public class Album {
    /**
     * 默认相册编码
     */
    public static final String DEFAULT_ALBUM_CODE = "DA";
    /**
     * 默认相册编号
     */
    public static final String DEFAULT_ALBUM_ID = DEFAULT_ALBUM_CODE + "12345678";
    /**
     * 编号
     */
    private Long id;
    /**
     * 相册编号
     */
    private String albumId;
    /**
     * 相册名称
     */
    private String albumName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
