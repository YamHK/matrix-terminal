package top.yamhk.terminal.auction;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.yamhk.core.base.utils.XcodeUtils;
import top.yamhk.core.kernel.BeanUtils8Spring;
import top.yamhk.core.kernel.TerminalStore;
import top.yamhk.terminal.annotation.EnableAuctionTerminal;
import top.yamhk.terminal.auction.domain.AuctionStore;
import top.yamhk.terminal.auction.service.AsyncMethod;
import top.yamhk.terminal.web.DemoRequest;
import top.yamhk.terminal.web.MajorServerApi;
import top.yamhk.terminal.web.Player;


/**
 * @Author: YamHK
 * @Date: 2020/9/1 18:21
 */


@Slf4j
@Component
@Lazy(false)
public class AuctionTerminalJob {
    /**
     * 酷库
     */
    @Autowired
    AuctionStore auctionStore;
    /**
     *
     */
    @Autowired
    TerminalStore terminalStore;
    /**
     * web core
     */
    @Autowired
    MajorServerApi majorServerApi;
    /**
     * web core
     */
    @Autowired
    private AuctionTerminalApi auctionTerminalApi;

    /**
     * async console
     */
    @Autowired
    private AsyncMethod asyncMethod;

    /**
     * run
     */
    @SneakyThrows
    @Scheduled(cron = "0/1 * * * * ?")
    public synchronized void run() {
        try {
            if (!asyncMethod.isTerminalClient() || !EnableAuctionTerminal.canRun) {
                log.error("@[{}]-客户端启动失败-{}-{}",
                        asyncMethod.getTerminalName(), asyncMethod.getKillTime(), asyncMethod.getMockTime());
                XcodeUtils.sleepSecondThread(60 * 60);
                return;
            } else if (!auctionStore.isMachineRunning()) {
                log.info("标记程序刚启动-用户数据初始化");
                auctionStore.setMachineRunning(true);
            }
            //构建-商品池
            if (!asyncMethod.isGoodsReady() && !asyncMethod.isSpiderManWorking()) {
                TerminalStore terminalStore = BeanUtils8Spring.getBean(TerminalStore.class);
                String username = terminalStore.getTerminalUsername();
                String usercode = terminalStore.getTerminalPassword();
                log.warn("蜘蛛俠-准备-[{}]-[{}]", username, usercode);
                Player spiderMan = (Player) terminalStore.getUserMap().get(username);
                //登陆
                asyncMethod.setSpiderMan(spiderMan);
                //TODO 秒杀过程中新增的商品处理
                asyncMethod.produce(majorServerApi::buildGoods);
            }
            //检查用户在线状态-
            if (!asyncMethod.isUserReady() && majorServerApi.isServerLoginTime()) {
                asyncMethod.synchronizationProduce(majorServerApi::buildPlayer);
            }
            //Hunting time
            if (!asyncMethod.isAuctionRunning() && asyncMethod.isUserReady() && auctionTerminalApi.isSeckillTime()) {
                if (asyncMethod.isTerminalClient()) {
                    String killRes = auctionTerminalApi.auctionGood();
                    log.error("结束-秒杀执行完成-{}", killRes);
                } else {
                    log.error("结束-秒杀执行完成-{}", "客户端未启动");
                }
                //restore
                auctionTerminalApi.machineInit();
                log.warn(">>>>>>>>>[机器重启-机器重启-机器重启]>>>>>>>>>");
            }
            //console
            majorServerApi.console(DemoRequest.builder().build());
            //console
            auctionTerminalApi.console(DemoRequest.builder().build());
        } catch (Exception e) {
            log.error("定时任务异常", e);
            XcodeUtils.sleepSecondThread(60);

        }
    }
}
