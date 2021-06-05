package top.yamhk.terminal.web.api.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.yamhk.terminal.web.BaseSrcResponse;

/**
 * @author yingx
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class TargetResponse extends BaseSrcResponse {

    /**
     * 类型
     */
    private String itemType;
    /**
     * 描述
     */
    private String itemDesc;
    /**
     * 时间
     */
    private String date;
}
