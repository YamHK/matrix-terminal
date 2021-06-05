package top.yamhk.terminal.auction.domain;

import lombok.Data;
import top.yamhk.terminal.web.AuctionGood;

import java.util.List;

/**
 * @Author: YamHK
 * @Date: 2021/3/26 20:44
 */
@Data
public class GoodsPage {
    /**
     * 商品列表
     */
    List<AuctionGood> data;
    /**
     * 总数
     */
    private Integer total;
    /**
     * 当前页商品数量
     */
    private Integer currentPageSize;
}
