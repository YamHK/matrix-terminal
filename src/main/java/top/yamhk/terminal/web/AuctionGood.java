package top.yamhk.terminal.web;

import lombok.Data;

import java.util.HashMap;

/**
 * @Author: YingX
 * @Date: 2021/4/18 12:52
 */
@Data
public class AuctionGood {
    /**
     * 竞拍-新手-星级
     */
    private Integer batch;
    /**
     * 商品id
     */
    private Integer id;
    /**
     * 所在页码
     */
    private Integer page;
    /**
     * 商品价格
     */
    private Integer price;
    /**
     * 下单请求
     */
    private HashMap<String, String> autoOrderingRequest;
}
