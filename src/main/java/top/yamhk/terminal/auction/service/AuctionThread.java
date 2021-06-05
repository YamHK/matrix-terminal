package top.yamhk.terminal.auction.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.yamhk.core.base.utils.JsonUtils;
import top.yamhk.terminal.web.AuctionGood;
import top.yamhk.terminal.web.Player;

import java.util.concurrent.TimeUnit;

/**
 * @author YamHK
 */
@Slf4j
public class AuctionThread implements Runnable {
    /**
     * 当前用户
     */
    private final Player currentPlayer;

    public AuctionThread(Player player) {
        this.currentPlayer = player;
    }

    @SneakyThrows
    @Override
    public void run() {
        log.warn("[{}]-启动玩家线程-开始竞拍>>>[{} within {}]-<{}>",
                currentPlayer.getUsername(),
                currentPlayer.getGoodsToAuction().size(), currentPlayer.getMaxHit(),
                currentPlayer.getNickname()
        );
        log.debug(">>>>>>>>>用户状态:{}", currentPlayer);
        int round = 1;
        //max=9
        while (round <= 25 && !currentPlayer.isHit() && currentPlayer.isCanHit() && !currentPlayer.getGoodsToAuction().isEmpty()) {
            try {
                AuctionGood nextAuctionGood;
                nextAuctionGood = currentPlayer.getGoodsToAuction().poll();
                if (nextAuctionGood == null) {
                    nextAuctionGood = currentPlayer.getGoodsToAuction().poll(1, TimeUnit.SECONDS);
                    log.info("[{}]<{}>-JP<{}>-续一秒又何妨", currentPlayer.getUsername(), currentPlayer.getNickname(), currentPlayer.getAuctionCount());
                }
                if (nextAuctionGood == null) {
                    log.error("[{}]<{}>-JP<{}>-续一秒失败?", currentPlayer.getUsername(), currentPlayer.getNickname(), currentPlayer.getAuctionCount());
                    continue;
                }
                currentPlayer.getGoodsToAuction().remove(nextAuctionGood);
                //
                currentPlayer.setAuctionCount(round);
                currentPlayer.setCurrentAuction(nextAuctionGood);
                currentPlayer.setRequestParam(nextAuctionGood.getAutoOrderingRequest());
                //trace
                log.trace(">>>>{}", JsonUtils.toJson(currentPlayer));
                //异步下单
                currentPlayer.getAsyncAuction().accept(currentPlayer);
                //一秒提交4.5.6次-含请求时间?
                Thread.sleep(333);
            } catch (Exception e) {
                log.error("[{}]<{}>-JP<{}>-ERROR-TRY-RE-LOGIN-[行为存在异常?=]", currentPlayer.getUsername(), currentPlayer.getNickname(), currentPlayer.getAuctionCount(), e);
            }
            round++;
        }
        //结束秒杀
        log.warn("[{}][end kill]-JP<{}>-<{}>-", currentPlayer.getUsername(), currentPlayer.getNickname(), currentPlayer.getAuctionCount());
        currentPlayer.getCountDownLatch().countDown();
    }

    public AuctionThread build() {
        return this;
    }
}
