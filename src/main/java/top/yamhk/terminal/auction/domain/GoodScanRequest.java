package top.yamhk.terminal.auction.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: YingX
 * @Date: 2021/4/12 11:08
 */
@Data
@Builder
public class GoodScanRequest {
    /**
     * 总价
     */
    private Integer[] sum;
    /**
     * 第几页
     */
    private int page;
    /**
     * 类型
     */
    private int i;
    /**
     * 类型
     */
    private String type;

    public String getTypeName() {
        //竞拍,新手,星级
        String type = "";
        type = this.i == 1 ? "JP" : type;
        type = this.i == 2 ? "XS" : type;
        type = this.i == 3 ? "XJ" : type;
        return type;
    }
}
