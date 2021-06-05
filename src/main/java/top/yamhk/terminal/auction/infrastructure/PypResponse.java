package top.yamhk.terminal.auction.infrastructure;

import lombok.Data;

/**
 * @Author: YamHK
 * @Date: 2021/3/26 10:16
 */
@Data
public class PypResponse<T> {
    /**
     * 错误码
     */
    private String code;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * data
     */
    private T data;

    /**
     * 判定响应成功
     *
     * @return boolean
     */
    public boolean success() {
        return "0".equals(code) && this.getData() != null;
    }
}
