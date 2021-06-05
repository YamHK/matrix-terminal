package top.yamhk.terminal.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * @Author: YingX
 * @Date: 2021/4/18 14:35
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Player extends User {
    /**
     * 可获得
     */
    private boolean available;
    /**
     * 已经命中-volatile
     */
    private volatile boolean hit;
    /**
     * 能够继续
     */
    private volatile boolean canHit;
    /**
     * 在线
     */
    private boolean online;
    /**
     * 服务器自动下单
     */
    private boolean vip;
    /**
     * 账号拥有者
     */
    private String owner;
    /**
     * 休息时间
     */
    private List<Integer> restTime;
    /**
     * 邮件通知地址
     */
    private String email;
    /**
     * 下单次数
     */
    private int auctionCount;
    /**
     * 第几次命中
     */
    private int hitWithCount;
    /**
     * 命中的商品
     */
    private AuctionGood hitAuctionGood;
    /**
     * 计时器
     */
    @JsonIgnore
    private CountDownLatch countDownLatch;
    /**
     * 异步秒杀
     */
    @JsonIgnore
    private Consumer<Player> asyncAuction;
    /**
     * 秒杀
     */
    @JsonIgnore
    private Consumer<Player> doAuction;
    /**
     * 异步秒杀
     */
    @JsonIgnore
    private Consumer<Player> asyncLogin;
    /**
     * 秒杀
     */
    @JsonIgnore
    private Consumer<Player> doLogin;

    /**
     * 当前秒杀
     */
    @JsonIgnore
    private AuctionGood currentAuction;
    /**
     * 请求头
     */
    @JsonIgnore
    private Map<String, String> requestHead;
    /**
     * 请求参数
     */
    @JsonIgnore
    private Map<String, String> requestParam;
    /**
     * 商品列表
     */
    @JsonIgnore
    private BlockingQueue<AuctionGood> goodsToAuction;
    /**
     * 真名
     */
    private String realName;

    @Override
    public boolean judgeOnline() {
        return this.isOnline()
                && getAuthorization() != null
                && getAuthorization().length() > 111
                && getRequestHead() != null
                && StringUtils.isNotBlank(this.getRequestHead().get("Authorization"));
    }

    public Integer getMaxHit() {
        return Math.toIntExact(Math.round(getIntegral() * 100 / 3));
    }
}
