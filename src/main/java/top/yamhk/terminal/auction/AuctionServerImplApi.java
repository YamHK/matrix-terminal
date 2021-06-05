package top.yamhk.terminal.auction;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.base.utils.Dates;
import top.yamhk.core.base.utils.JsonUtils;
import top.yamhk.core.base.utils.MD5Utils;
import top.yamhk.core.base.utils.XcodeUtils;
import top.yamhk.core.kernel.MatrixResponse;
import top.yamhk.core.kernel.TerminalStore;
import top.yamhk.terminal.auction.domain.AuctionStore;
import top.yamhk.terminal.auction.domain.GoodScanRequest;
import top.yamhk.terminal.auction.domain.GoodsPage;
import top.yamhk.terminal.auction.infrastructure.PypClientFeignClient;
import top.yamhk.terminal.auction.infrastructure.PypResponse;
import top.yamhk.terminal.auction.service.AsyncMethod;
import top.yamhk.terminal.web.AuctionGood;
import top.yamhk.terminal.web.DemoRequest;
import top.yamhk.terminal.web.MajorServerApi;
import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.PlayerResponse;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * 黄金三十秒
 *
 * @Author: YamHK
 * @Date: 2021/3/26 9:05
 */
@Slf4j
@RestController
@RequestMapping("/v1/server/auction")
@Primary
public class AuctionServerImplApi implements MajorServerApi {
    /**
     * Autowired
     */
    @Autowired
    PypClientFeignClient pypClientFeignClient;

    /**
     * async
     */
    @Autowired
    AsyncMethod asyncMethod;
    /**
     * 秒杀终端
     */
    @Autowired
    AuctionTerminalApi auctionTerminalApi;

    /**
     * 商品库
     */
    @Autowired
    AuctionStore auctionStore;

    /**
     *
     */
    @Autowired
    TerminalStore terminalStore;

    @Override
    public String findByUsername(String username) {
        return null;
    }

    @Override
    public String findByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public MatrixResponse<String> modifyAccounts(DemoRequest request) {
        return null;
    }

    @Override
    public MatrixResponse<String> deleteAccounts(DemoRequest request) {
        return null;
    }

    @Override
    public MatrixResponse<PlayerResponse> getPlayers() {
        return null;
    }

    /**
     * 登陆并返回玩家信息
     *
     * @return Player
     */
    @Override
    @PatchMapping("/user")
    public String login(Player player) {
        log.debug("[{}]「{}」-开始检查-玩家账号登录状态-[{}]-登录中-[>>>>>>>>>]",
                player.getUsername(), player.getNickname(), asyncMethod.getPlayer().size());
        auctionTerminalApi.doLogin(player);
        return "[登录中]";
    }

    @Override
    public void save(Player po) {

    }

    /**
     * 构建商品
     */
    @Override
    public synchronized String buildGoods() {
        if (isNotScanTime()) {
            log.warn("###[SpiderMan-WORK]###-君不见高堂明镜悲白发");
            XcodeUtils.sleepSecondThread(60);
            return "君不见高堂明镜悲白发";
        } else {
            log.info("###[SpiderMan-WORK]###-君不见黄河之水天上来");
        }
        //登录
        while (asyncMethod.isSpiderManOffline()) {
            auctionTerminalApi.doLogin(asyncMethod.getSpiderMan());
        }
        //蜘蛛侠开始工作
        asyncMethod.setSpiderManWorking(true);
        //总数-计数器
        final Integer[] sum = {0};
        Map<String, Integer> goodsMap = new HashMap<>();
        asyncMethod.setAuctionGoodStore(new CopyOnWriteArraySet<>());
        //查询商品
        Integer totalGoods = 555;
        //扫描
        for (int i = 1; i <= 1; i++) {
            int page = 1;
            do {
                GoodScanRequest scanRequest = GoodScanRequest.builder()
                        .i(i)
                        .page(page)
                        .sum(sum)
                        .build();
                try {
                    PypResponse<GoodsPage> goodShow = doGetGoodsByPage(scanRequest);
                    log.debug("goodShow:{}", goodShow);
                    if (goodShow.success()) {
                        //max-count
                        Integer total = goodShow.getData().getTotal();
                        totalGoods = total >= totalGoods ? total : totalGoods;
                        //goods-build-map
                        List<AuctionGood> goods = goodShow.getData().getData();
                        goods.forEach(e -> {
                            String key = Integer.toString(e.getPrice() / 1000);
                            int count = goodsMap.get(key) != null ? goodsMap.get(key) : 0;
                            goodsMap.put(key, count + 1);
                        });
                        //goods-add-store
                        asyncMethod.getAuctionGoodStore().addAll(goods);
                    } else {
                        log.error("SCAN-ERROR-@-[i-page]|[{}]", scanRequest);
                    }
                    if (goodShow.getData().getCurrentPageSize() == 10) {
                        page++;
                    } else {
                        log.warn("页面数据异常:sleep");
                        XcodeUtils.sleepSecondThread(55);
                    }
                } catch (Exception e) {
                    log.error("商品查询异常-[{}]", e.getMessage());
                    XcodeUtils.sleepSecondThread(60);
                }
                //1000页-十六分钟
                XcodeUtils.sleepSecondThread(1);
            } while (totalGoods > page * 10);
        }
        asyncMethod.setGoodsReady(!asyncMethod.getAuctionGoodStore().isEmpty());
        asyncMethod.setSpiderManWorking(false);
        //TODO 存储
        log.error("[这是一条忽略的消息]结束-异步构建商品-{}@{}-{}", asyncMethod.getAuctionGoodStore().size(), sum[0], goodsMap);
        return "[这是一条忽略的消息]结束-异步构建商品-[" + sum[0] + "]/[" + goodsMap + "]]";
    }


    /**
     * 获取某一页的商品
     *
     * @param scanRequest scanRequest
     * @return PypResponse
     */
    private PypResponse<GoodsPage> doGetGoodsByPage(GoodScanRequest scanRequest) {
        //continue
        int pageSize;
        try {
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("type", String.valueOf(scanRequest.getI()));
            requestMap.put("page", String.valueOf(scanRequest.getPage()));
            log.debug("[{}]「{}」-[请求参数]-{}",
                    asyncMethod.getSpiderMan().getUsername(),
                    asyncMethod.getSpiderMan().getNickname(),
                    requestMap);
            String response = pypClientFeignClient.getGoodsByPage(asyncMethod.getRequestHeader(), requestMap);
            log.debug("响应:{}", response);
            PypResponse goodShow = JsonUtils.GSON.fromJson(response, PypResponse.class);
            //PypResponse<GoodsPage> goodShow = pypClientFeignClient.getGoodsByPage(asyncMethod.getRequestHeader(), requestMap);
            //存储
            GoodsPage goodPage = JsonUtils.GSON.fromJson(goodShow.getData().toString(), GoodsPage.class);
            List<AuctionGood> auctionGoods = goodPage.getData();
            //遍历
            int finalI = scanRequest.getI();
            int finalPage = scanRequest.getPage();
            auctionGoods.forEach(e -> {
                e.setBatch(finalI);
                e.setPage(finalPage);
                //构建下单请求
                e.setAutoOrderingRequest(buildOrderRequest(e));
                //求和
                int price = e.getPrice();
                scanRequest.getSum()[0] += price;
                //分布
                int priceKey = (price / 1000);
                String key = priceKey + "[" + scanRequest.getTypeName() + "]";
                int count = auctionStore.getGoodsMap().get(key) != null ? auctionStore.getGoodsMap().get(key) + 1 : 1;
                auctionStore.getGoodsMap().put(key, count);
            });
            //log
            pageSize = auctionGoods.size();
            goodPage.setCurrentPageSize(pageSize);
            return goodShow;
        } catch (Exception e) {
            log.error("SCAN-ERROR-@-[i-page]|[{}]:", scanRequest, e);
            asyncMethod.getSpiderMan().setOnline(false);
            return new PypResponse<>();
        }
    }

    /**
     * 构建下单请求参数
     *
     * @param auctionGood good
     * @return HashMap
     */
    private HashMap<String, String> buildOrderRequest(AuctionGood auctionGood) {
        HashMap<String, String> orderRequest = new HashMap<>();
        orderRequest.put("oid", String.valueOf(auctionGood.getId()));
        orderRequest.put("type", String.valueOf(auctionGood.getBatch()));
        orderRequest.put("page", String.valueOf(auctionGood.getPage()));
        orderRequest.put("limit", "10");
        String token = MD5Utils.stringToMD5(String.valueOf(auctionGood.getId()) + auctionGood.getBatch() + auctionGood.getPage() + "10" + "weZXu2h0!@");
        orderRequest.put("token", token);
        return orderRequest;
    }

    /**
     * 构建玩家
     *
     * @return String
     */
    @Override
    public synchronized String buildPlayer() {
        //
        while (asyncMethod.getAuctionGoodStore().size() <= 99) {
            log.warn("waiting for 99 goods :{}@{}", asyncMethod.getAuctionGoodStore().size(), "60");
            XcodeUtils.sleepSecondThread(60);
        }
        //
        if (asyncMethod.isUserReady()) {
            log.warn(">>>player is ready>>>");
        } else {
            log.info(">>>begin to build player>>>");
        }
        //登陆分配
        List<Player> playerToGo = terminalStore.getUserStore().stream()
                .map(e -> JsonUtils.GSON.fromJson(e.toString(), Player.class))
                .filter(this::judgeCanLogin)
                .collect(Collectors.toList());
        //
        //平均分配
        ConcurrentLinkedQueue<List<AuctionGood>> collect = divideGoods(playerToGo.size());
        log.warn("商品分割:[player]:{}-[goods]{}", playerToGo.size(), collect.size());
        playerToGo.forEach(player -> {
            auctionTerminalApi.userInit(player);
            this.login(player);
            this.setAuctionGoods(collect, player);
        });
        //有玩家就绪
        if (!playerToGo.isEmpty()) {
            asyncMethod.setPlayerToGo(playerToGo);
            long online = playerToGo.stream().filter(Player::isOnline).count();
            log.warn("[>>>>>>>>>]-玩家就绪:[{}/{}]", online, playerToGo.size());
            asyncMethod.setUserReady(true);
        } else {
            log.error("{>>>>>>>>>}-Nobody-");
        }
        log.debug("查看用户信息:{}", playerToGo);
        return "[这是一条忽略的消息]-";
    }

    private boolean judgeCanLogin(Player player) {
        if (!(asyncMethod.getPlayer().contains(player.getUsername()) || player.isVip())) {
            log.debug("[{}]-[用户登录-未授权]-「{}」", player.getUsername(), player.getNickname());
            return false;
        } else if (!judgeWorkingHour(player)) {
            log.warn("[{}]-[用户登录-休息中]-「{}」", player.getUsername(), player.getNickname());
            return false;
        } else {
            log.info("[{}]-[用户登录-待分配]-[>>>>>>>>>]-「{}」", player.getUsername(), player.getNickname());
            return true;
        }
    }

    /**
     * 判断能够参加
     *
     * @param player player
     * @return boolean
     */
    private boolean judgeWorkingHour(Player player) {
        return !player.getRestTime().contains(Dates.getHour());
    }

    /**
     * 分配商品
     *
     * @param queue  queue
     * @param player player
     */
    private void setAuctionGoods(ConcurrentLinkedQueue<List<AuctionGood>> queue, Player player) {
        List<AuctionGood> auctionGoods = queue.poll();
        //除非没有商品
        if (auctionGoods == null || auctionGoods.isEmpty()) {
            log.error("[不存在的]-商品不足-用户过载-{}", asyncMethod.getPlayer().size());
            return;
        }
        //乱序
        Collections.shuffle(auctionGoods);
        //赋值
        player.setGoodsToAuction(auctionGoods.stream()
                .filter(f -> f.getPrice() <= player.getMaxHit())
                .collect(Collectors.toCollection(LinkedBlockingQueue::new)));
    }

    /**
     * 预分配商品
     *
     * @param size size
     * @return ConcurrentLinkedQueue
     */
    private ConcurrentLinkedQueue<List<AuctionGood>> divideGoods(int size) {
        List<AuctionGood> goodsToAuction;
        do {
            //筛选商品
            goodsToAuction = asyncMethod.getAuctionGoodStore().stream()
                    .filter(e -> e.getPrice() >= 123)
                    .filter(e -> e.getPrice() <= 9999)
                    .sorted(Comparator.comparing(AuctionGood::getBatch)
                            .thenComparing(AuctionGood::getPrice).reversed()
                            .thenComparing(AuctionGood::getPage).reversed()
                    ).collect(Collectors.toList());
            log.error("商品分配异常-过滤后");
            XcodeUtils.sleepSecondThread(5);
        } while (goodsToAuction.isEmpty() || goodsToAuction.size() < size);
        //乱序
        Collections.shuffle(goodsToAuction);
        //分割
        int goodsGroupSize = goodsToAuction.size() >= size * 99 ? 99 : goodsToAuction.size() / size;
        return new ConcurrentLinkedQueue<>(Lists.partition(goodsToAuction, goodsGroupSize));
    }


    /**
     * 非扫描时间
     *
     * @return boolean
     */
    private boolean isNotScanTime() {
        return Dates.getMinute() >= 30 && asyncMethod.getKillTime().contains(Dates.getHour());
    }

    /**
     * 是否登录时间
     *
     * @return boolean
     */
    @Override
    public boolean isServerLoginTime() {
        return auctionTerminalApi.isTime2Login4Server();
    }

    @Override
    public List<Player> findAll() {
        return null;
    }

    /**
     * console
     *
     * @param request request
     */
    @Override
    public synchronized String console(@RequestBody DemoRequest request) {
        //
        if (auctionTerminalApi.notConsoleTime(request)) {
            log.trace("自挂东南枝@[{}]:{}", asyncMethod.getTerminalName(), request);
            return "console ignore";
        }
        //输出服务器信息
        //输出客户端信息
        return consoleServerStatus() + "-console";
    }

    /**
     * console
     *
     * @return String
     */
    private String consoleServerStatus() {
        //list
        log.debug(">>>-LIST-U-[mock]:{}@{}-[Hunting time][{}/{}/{}-{}:{}:{}]"
                , asyncMethod.getMockTime()
                , auctionTerminalApi.isMockTime()
                , Calendar.getInstance().get(Calendar.YEAR)
                , Calendar.getInstance().get(Calendar.MONTH)
                , Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                , Dates.getHour()
                , Dates.getMinute()
                , Dates.getSecond()
        );
        asyncMethod.getPlayerToGo().stream()
                .sorted(Comparator.comparing(Player::isHit).reversed().thenComparing(Player::getRealName).thenComparing(Player::getNickname))
                .forEach(e -> {
                    String win = e.isHit() ? "WIN" : "LOSE";
                    Object price = e.getHitAuctionGood() != null ? e.getHitAuctionGood().getPrice() : "";
                    log.warn(":<{}>-online:[{}]-goods:[{}]-[{}/{}]-[{}]-[{}]-[{}]-[{}]",
                            e.getUsername(), e.judgeOnline(), e.getGoodsToAuction() != null ? e.getGoodsToAuction().size() : 0
                            , e.getHitWithCount(), e.getAuctionCount()
                            , win, price
                            , e.getRealName(), e.getNickname());
                });
        return "服务端仪表盘";
    }
}

