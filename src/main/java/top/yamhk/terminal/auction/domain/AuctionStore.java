package top.yamhk.terminal.auction.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
import top.yamhk.terminal.web.AuctionGood;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: YingX
 * @Date: 2021/4/13 20:21
 */
@Data
@Component
public class AuctionStore {
    /**
     * 程序运转
     */
    private boolean machineRunning = false;
    /**
     * 模拟通知对象
     */
    private String mailMock = "www_yamhk_top@163.com";
    /**
     * 模拟通知对象
     */
    private String mailProd = "yingxunyao55@gmail.com";
    /**
     * 商品库
     */
    private Set<AuctionGood> auctionGoodStore = new CopyOnWriteArraySet<>();
    /**
     * 商品图谱
     */
    private Map<String, Integer> goodsMap = new HashMap<>();
}
