package top.yamhk.terminal.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: YingX
 * @Date: 2021/4/19 14:15
 */
@Data
public class PlayerResponse {
    /**
     * 拥有者
     */
    private String owner;
    /**
     * 积分
     */
    private Integer count = 0;
    /**
     * 积分
     */
    private Integer integral = 999;
    /**
     * 玩家账号
     */
    @JsonProperty("players")
    private List<PlayerDTO> playerDTOS;

    @Data
    public static class PlayerDTO {
        /**
         * 在线
         */
        private Boolean online;
        /**
         * 已经命中-volatile
         */
        private Boolean hit;
        /**
         * 能够继续
         */
        private Boolean canHit;
        /**
         * 服务器自动下单
         */
        private Boolean vip;
        /**
         * 账户
         */
        private String username;
        /**
         * 昵称
         */
        private String nickname;
        /**
         * 密码
         */
        private String password;
        /**
         * 邮件通知地址
         */
        private String email;
        /**
         * 账号拥有者
         */
        private String owner;
        /**
         * 休息时间
         */
        private List<Integer> restTime;
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
    }
}
