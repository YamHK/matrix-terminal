package top.yamhk.terminal.auction.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.yamhk.core.base.utils.Dates;
import top.yamhk.terminal.web.AuctionGood;
import top.yamhk.terminal.web.Player;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Supplier;

/**
 * @Author: YingX
 * @Date: 2021/4/11 18:46
 */
@Slf4j
@Getter
@Setter
@Component
public class AsyncMethod {

    /**
     * 秒杀时间
     */
    private final List<Integer> killTime = Arrays.asList(9, 13, 19);
    /**
     * 测试邮件-赋值失败
     */
    String emailMock = "www_yamhk_top@163.com";
    /**
     * 生产邮件-赋值失败
     */
    String emailProd = "651978788@qq.com";
    /**
     * 终端标识
     */
    @Value("${user.name}")
    private String systemName;
    /**
     * 终端标识
     */
    @Value("${terminal.name}")
    private String terminalName;
    /**
     * 终端标识
     */
    @Value("${terminal.server}")
    private boolean terminalServer;
    /**
     * 终端标识
     */
    @Value("${terminal.client}")
    private boolean terminalClient;
    /**
     * 刚启动
     */
    private boolean startJustNow = true;
    /**
     * 刚启动
     */
    private boolean rpcServer = false;
    /**
     * SpiderMan
     */
    private Player spiderMan;
    /**
     * heard
     */
    private HashMap<String, String> requestHeader = new HashMap<>();
    /**
     * 玩家状态
     */
    private volatile boolean goodsReady = false;
    /**
     * 玩家状态
     */
    private volatile boolean userReady = false;
    /**
     * 用户线程是否启动
     * //基础类型是拷贝?静态变量存？
     */
    private volatile boolean auctionRunning = false;
    /**
     * 结果通知
     */
    private volatile boolean spiderManWorking = false;
    /**
     * 结果通知
     */
    private volatile boolean alreadyNotice = true;
    /**
     * 商品库
     */
    private Set<AuctionGood> auctionGoodStore = new CopyOnWriteArraySet<>();
    /**
     * 玩家-go
     */
    private List<Player> playerToGo = new CopyOnWriteArrayList<>();
    /**
     * 玩家
     */
    private Set<String> player;
    /**
     * 玩家
     */
    private Set<String> blackPlayer;
    /**
     * 模拟时间
     */
    private Set<Integer> mockTime;

    public AsyncMethod() {
        //player
        this.player = new HashSet<>(Arrays.asList());
        this.player = new HashSet<>(Arrays.asList("邵钢钢", "叶晓霞", "叶姗楠", "邵钢钢"));
        //mockTime
        this.mockTime = new HashSet<>();
        //onetime
        int mockHour = Calendar.getInstance().get(Calendar.MINUTE) / 10 == 5 ? (Dates.getHour() + 1) % 24 : Dates.getHour();
        this.mockTime.add(mockHour);
        //mock
        this.mockTime.add(8);
        this.mockTime.add(12);
        this.mockTime.add(18);
        //prod
        this.mockTime.remove(9);
        this.mockTime.remove(13);
        this.mockTime.remove(19);
    }


    /**
     * 生产-同步
     *
     * @param supplier supplier
     * @return String
     */
    public String synchronizationProduce(Supplier<String> supplier) {
        return supplier.get();
    }

    /**
     * 生产-异步-方法名不能包含？
     *
     * @param supplier supplier
     * @return String
     */
    @Async
    public String produce(Supplier<String> supplier) {
        return supplier.get();
    }


    /**
     * 消费-异步-秒杀
     *
     * @param player player
     * @return String
     */
    @Async
    public void doAuction(Player player) {
        player.getDoAuction().accept(player);
    }

    /**
     * 消费-异步-登录
     *
     * @param player player
     * @return String
     */
    @Async
    public void doLogin(Player player) {
        player.getDoLogin().accept(player);
    }

    /**
     * 判断蜘蛛侠
     *
     * @return boolean
     */
    public boolean isSpiderManOffline() {
        return this.spiderMan == null || !this.spiderMan.judgeOnline();
    }
}
