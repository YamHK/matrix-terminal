package top.yamhk.terminal.auction;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.base.utils.Dates;
import top.yamhk.core.base.utils.JsonUtils;
import top.yamhk.core.base.utils.XcodeUtils;
import top.yamhk.core.boot.MatrixProxyFactory;
import top.yamhk.core.kernel.BeanCopyUtils;
import top.yamhk.core.kernel.TerminalStore;
import top.yamhk.terminal.auction.domain.AuctionStore;
import top.yamhk.terminal.auction.domain.SignIn;
import top.yamhk.terminal.auction.infrastructure.PypClientFeignClient;
import top.yamhk.terminal.auction.infrastructure.PypResponse;
import top.yamhk.terminal.auction.service.AsyncMethod;
import top.yamhk.terminal.auction.service.AuctionThread;
import top.yamhk.terminal.infrastructure.UtilsService;
import top.yamhk.terminal.mail.EmailService;
import top.yamhk.terminal.web.AuctionGood;
import top.yamhk.terminal.web.DemoRequest;
import top.yamhk.terminal.web.MajorServerApi;
import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.User;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

/**
 * 黄金三十秒
 *
 * @Author: YamHK
 * @Date: 2021/3/26 9:05
 */
@Slf4j
@RestController
@RequestMapping("/v1/terminal/auction")
public class AuctionTerminalApi {

    /**
     *
     */
    @Autowired
    TerminalStore terminalStore;
    /**
     * 酷库
     */
    @Autowired
    AuctionStore auctionStore;
    /**
     * Autowired
     */
    @Autowired
    PypClientFeignClient pypClientFeignClient;
    /**
     * utils
     */
    @Autowired
    UtilsService utilsService;
    /**
     * 信使-异步类中不生效?
     */
    @Autowired
    EmailService emailService;
    /**
     * 服务
     *
     * @Autowired(required = false)
     */
    MajorServerApi majorServerApi = MatrixProxyFactory.getProxy(MajorServerApi.class);

    /**
     * async
     */
    @Autowired
    AsyncMethod asyncMethod;

    /**
     * 用户参数初始化
     *
     * @param player player
     */
    public void userInit(Player player) {
        player.setHit(false);
        player.setCanHit(true);
        player.setHitAuctionGood(null);
        player.setCurrentAuction(null);
        player.setHitWithCount(0);
        player.setAuctionCount(0);
        //
        player.setAsyncAuction(asyncMethod::doAuction);
        player.setDoAuction(this::doAuction);
        //
        player.setAsyncLogin(asyncMethod::doLogin);
        player.setDoLogin(this::doLogin);
    }

    public synchronized Player doLogin(Player player) {
        //登陆
        if (isLoginForbidden(player.getUsername())) {
            log.error("[{}]「{}」-开始检查-玩家账号登录状态-[{}/{}]-非登录时间-禁止登陆...",
                    player.getUsername(), player.getNickname(), asyncMethod.getPlayer().size(), terminalStore.getUserStore().size());
            return player;
        }
        log.debug("[{}]「{}」->>>>>>>>>登陆开始-", player.getUsername(), player.getNickname());
        do {
            if (StringUtils.isBlank(player.getAuthorization())) {
                //登录
                log.info("[{}]-1.重新登录-「{}」-token为空", player.getUsername(), player.getNickname());
                PypResponse loginResponse = getTokenBySignIn(player);
                //判断登录失败
                if (!loginResponse.success()) {
                    log.error("loginResponse:{}", loginResponse);
                    String ipDtl = utilsService.getIp()
                            + "\n-" + asyncMethod.getSystemName()
                            + "\n@" + asyncMethod.getTerminalName();
                    log.error("[{}]「{}」-网络异常-[开始挂一天]-[{}]", player.getUsername(), player.getNickname(), ipDtl.replaceAll("\n", ""));
                    emailService.sendMail(auctionStore.getMailMock(), "网络异常-[开始挂一天]", ipDtl);
                    player.setOnline(false);
                    XcodeUtils.sleepSecondThread(86400);
                    log.error("[{}]「{}」-网络异常-[挂了一天了]-[<>]", player.getUsername(), player.getNickname());
                    return player;
                }
                log.info("[{}]-2.登录成功-「{}」", player.getUsername(), player.getNickname());
                //
                String authorization = JsonUtils.GSON.fromJson(loginResponse.getData().toString(), SignIn.class).getToken();
                //
                HashMap<String, String> loginUserRequestHead = new HashMap<>(5);
                loginUserRequestHead.put("Authorization", authorization);
                //
                player.setRequestHead(loginUserRequestHead);
                player.setAuthorization(authorization);
                //临时存储
                asyncMethod.setRequestHeader(loginUserRequestHead);
                //更新个人信息
            }
            //查看
            getUserInfoByToken(player);
            //
            log.warn("[{}]-5.登陆成功-「{}」-online-{}", player.getUsername(), player.getNickname(), player.judgeOnline());
            XcodeUtils.sleepSecondThread(1);
        } while (!player.judgeOnline());
        log.debug("[{}]「{}」->>>>>>>>>登陆结束-online[{}]", player.getUsername(), player.getNickname(), player.isOnline());
        return player;
    }

    /**
     * 登陆
     *
     * @param player player
     * @return PypResponse<SignIn>
     */
    private PypResponse getTokenBySignIn(Player player) {
        HashMap<String, String> requestMap = new HashMap<>(5);
        requestMap.put("phone", player.getUsername());
        requestMap.put("psd", player.getPassword());
        requestMap.put("deviceInfo", "{\"windowTop\":0,\"windowBottom\":0,\"windowWidth\":375,\"windowHeight\":812,\"pixelRatio\":3.0000001192092896,\"screenWidth\":375,\"screenHeight\":812,\"language\":\"zh-CN\",\"statusBarHeight\":0,\"system\":\"iOS 13.2.3\",\"platform\":\"ios\",\"model\":\"iPhone\",\"safeArea\":{\"left\":0,\"right\":375,\"top\":0,\"bottom\":812,\"width\":375,\"height\":812},\"safeAreaInsets\":{\"top\":0,\"right\":0,\"bottom\":0,\"left\":0},\"deviceId\":\"16166744211431396727\"}");
        requestMap.put("sessionId", "01UZRhfVAkgcV5-KYYm20CtKkgSl2yoTeUDwETd0qUZJqCubd1E2S-2l23rYXBqBspSvqDD2PIEZ6aEmrNzqs8m9ILksAsi_jOiYiArUQCcwdB868MvOl8tcUwL1pP4CneOVuufrF1N6VxpwXtXvzAmkQlFXRO9sctZ07a-wh-X6HjKIywGmLETNJRsqBBoQGuRlin0SxEZNbPva4gFyeQ9Q");
        requestMap.put("token", "05XBa8EtGVqtX0rzvVYJMIN6qz1rGzNys5vgxpQtxAY99BucJ_sBp-U7mtYcK8LeixmaIADhiaYxZeVv4ln_W81mOyldazdBBPaYTJm3R5A-ixTjWwFwt_K5H-12EPRXQEJFLV_2jwiHjNa-q1AUZ_HERZP0BztotM7tw3tsJNd3vaXzovwN3i4SnKZQwBnauIt_TahkP4LcDfQIn1UWaIo7N_216UFZQs_CXnLhQnNyGJ5qhY_MrcYaNOV0HVaB0v01Ar3YahFiLVfGqfBS59zu-_2HAbcj7FPyf09X5RP_iwGRjkpbhRjrRnuR4Mu3vowO3zskPalRV9GMXAAs6x20sN9piNqdQIqLQoRLAx6PqEVLjk_glSENq2vM0tjM_6TgpVoyC3a4CyfjhQN1CANj0ZFS1mz4z4HP5D9PkHdQT286wYIgH9acvuTUZhxbnHRmiSAoMnzS_gIfPK8WtENuFZGHueI7tQGkIpaSXVKAU");
        requestMap.put("sig", "FFFF0N0N000000009D7E:1620489691335:0.2991737882981156");
        requestMap.put("scene", "nc_login_h5");
        try {
            String response = pypClientFeignClient.login(requestMap);
            log.debug("响应:{}", response);
            return JsonUtils.GSON.fromJson(response, PypResponse.class);
        } catch (Exception e) {
            log.error("[{}]「{}」-登录异常,{}", player.getUsername(), player.getNickname(), e.getMessage());
            return new PypResponse<>();
        }
    }

    private Player getUserInfoByToken(Player player) {
        //do
        try {
            String authorization = StringUtils.isNotBlank(player.getAuthorization()) ? player.getAuthorization() : "";
            HashMap<String, String> loginUserRequestHead = new HashMap<>(5);
            loginUserRequestHead.put("Authorization", authorization);
            player.setRequestHead(loginUserRequestHead);
            asyncMethod.setRequestHeader(loginUserRequestHead);
            //other-加个头
            //do
            String response = pypClientFeignClient.getUserInfo(loginUserRequestHead);
            log.debug("响应:{}", response);
            PypResponse getUserInfoResponse = JsonUtils.GSON.fromJson(response, PypResponse.class);
            if (getUserInfoResponse.success()) {
                //获取用户信息
                User onlineUser = JsonUtils.GSON.fromJson(getUserInfoResponse.getData().toString(), User.class);
                //
                log.debug("[{}]「{}」-copy:{}", player.getUsername(), player.getNickname(), onlineUser);
                log.debug("[{}]「{}」-copy:{}", player.getUsername(), player.getNickname(), player);
                log.info("[{}]-3.获取信息-「{}」-@[{}]「{}」", player.getUsername(), player.getNickname(), onlineUser.getUsername(), onlineUser.getNickname());
                //保存token
                BeanCopyUtils.copyProperties(onlineUser, player);
                player.setRealName(StringUtils.isEmpty(onlineUser.getBank().getRealName()) ? "" : onlineUser.getBank().getRealName());
                //去重
                Optional<Player> dp = terminalStore.getUserStore().stream()
                        .map(e -> JsonUtils.GSON.fromJson(e.toString(), Player.class))
                        .filter(f -> f.getUsername().equalsIgnoreCase(player.getUsername())).findFirst();
                if (dp.isPresent()) {
                    Player storePlayer = (Player) terminalStore.getUserMap().get(dp.get().getUsername());
                    BeanCopyUtils.copyProperties(player, storePlayer);
                    log.debug("[{}]「{}」-存在并更新:{}", player.getUsername(), player.getNickname(), player);
                    log.debug("[{}]「{}」-存在并更新:{}", player.getUsername(), player.getNickname(), storePlayer);
                } else {
                    terminalStore.getUserStore().add(player);
                    terminalStore.getUserMap().put(player.getUsername(), player);
                    log.debug("[{}]「{}」-新增保存:{}", player.getUsername(), player.getNickname(), player);
                }
                //
                loginUserRequestHead.put("Uid", onlineUser.getUid());
                log.info("[{}]-4.更新信息-「{}」", onlineUser.getUsername(), onlineUser.getNickname());
                player.setOnline(true);
            } else {
                player.setOnline(false);
                player.setAuthorization("");
            }
            log.debug("[{}]「{}」-userinfo:{}", player.getUsername(), player.getNickname(), getUserInfoResponse);
        } catch (Exception e) {
            log.error("[{}]「{}」-6.获取异常-[{}]", player.getUsername(), player.getNickname(), e);
            player.setOnline(false);
            player.setAuthorization("");
        }
        return player;
    }

    /**
     * 秒杀
     */
    @SneakyThrows
    public synchronized String auctionGood() {
        //秒杀进行中
        asyncMethod.setAuctionRunning(true);
        //检查状态-去瘠薄
        List<Player> playerToGo = asyncMethod.getPlayerToGo();
        if (playerToGo.isEmpty()) {
            log.error("PLAYER IS NULL");
        } else {
            //清空商品库-吸收新鲜血液?闹呢?
            log.warn("启动线程-KILLING...");
            //
            final CountDownLatch countDownLatch = new CountDownLatch(playerToGo.size());
            //stream 会新生成对象
            playerToGo.forEach(e -> {
                e.setCountDownLatch(countDownLatch);
                AuctionThread auctionThread = new AuctionThread(e).build();
                new Thread(auctionThread).start();
            });
            log.warn("阻塞当前线程,直到计数器的值为0");
            countDownLatch.await();//阻塞当前线程，直到计数器的值为0
            log.warn("全部线程结束");
            //让子弹飞一会儿
            XcodeUtils.sleepSecondThread(5);
        }
        return "-完成所有线程-anyway";
    }

    /**
     * function
     *
     * @param player player
     * @return String
     */
    public void doAuction(Player player) {
        //
        AuctionGood currentAuction = player.getCurrentAuction();
        int round = player.getAuctionCount();
        //
        player.setCurrentAuction(currentAuction);
        log.debug("[{}]<{}>getRequestHead:{}", player.getUsername(), player.getNickname(), player.getRequestHead());
        log.debug("[{}]<{}>getRequestParam:{}", player.getUsername(), player.getNickname(), player.getRequestParam());
        String auctionRes = "";
        try {
            auctionRes = pypClientFeignClient.doAuction(player.getRequestHead(), player.getRequestParam());
        } catch (Exception e) {
            int errorIndex = e.getMessage() != null ? e.getMessage().indexOf("异常") : 0;
            String message;
            if (e.getMessage() != null) {
                message = e.getMessage().contains("存在异常") ? e.getMessage().substring(errorIndex - 9, errorIndex + 9) : "OTHER";
            } else {
                message = "???getMessage为空";
            }
            log.error("[{}]<{}>-JP<{}>-秒杀方法调用异常-{}-{}",
                    player.getUsername(), player.getNickname(),
                    round,
                    message, e.getMessage());
            //防风秘籍
            if (e.getMessage() != null && e.getMessage().contains("存在异常")) {
                player.setCanHit(false);
            }
        }
        if (StringUtils.isBlank(auctionRes)) {
            log.error("没有相应");
        } else {
            //查看是否时间未到-未到回笼
            if (isNotStarted(auctionRes)) {
                player.getGoodsToAuction().add(currentAuction);
            }
            //会话失效-回笼
            if (auctionRes.contains("-100")) {
                player.setCanHit(false);
                player.setOnline(false);
                log.error("[{}]<{}>-JP<{}>-掉线了", player.getUsername(), player.getNickname(), round);
            }
            //会话失效-回笼
            if (auctionRes.contains("次数已用完")) {
                player.setCanHit(false);
                player.setHit(true);
                log.error("[{}]<{}>-JP<{}>-已经秒中了", player.getUsername(), player.getNickname(), round);
            }
            //
            boolean hit = mockHit(round) || auctionRes.contains("成功") || auctionRes.contains("-2");
            //
            if (hit && isMockTime()) {
                log.error("[{}]<{}>-JP<{}>-模拟中奖延迟", player.getUsername(), player.getNickname(), round);
            }
            if (hit) {
                //
                player.setCanHit(false);
                player.setHit(true);
                //命中的信息
                player.setHitWithCount(round);
                player.setHitAuctionGood(currentAuction);
            }
            log.warn("[{}]-JP<{}>-ERR?:{}-JG[{}]\t-with-[KILL-RES]:{}-<{}>-{}",
                    player.getUsername(),
                    round,
                    auctionRes.contains("次数已用完") || auctionRes.contains("异常"),
                    currentAuction.getPrice(),
                    auctionRes.contains("次数已用完") || auctionRes.contains("异常") ? "ERROR" : "INFO",
                    player.getNickname(),
                    auctionRes);
        }
    }

    /**
     * 未开始
     *
     * @param auctionRes auctionRes
     * @return boolean
     */
    private boolean isNotStarted(String auctionRes) {
        return StringUtils.isBlank(auctionRes) || auctionRes.contains("时间未到");
    }

    /**
     * 初始化
     */
    public void machineInit() {
        //通知-输出
        asyncMethod.setAlreadyNotice(false);
        console(DemoRequest.asAdmin());
        //我已不是当初的少年
        asyncMethod.setStartJustNow(false);
        //mock
        asyncMethod.getMockTime().add(8);
        asyncMethod.getMockTime().add(12);
        asyncMethod.getMockTime().add(18);
        asyncMethod.getMockTime().remove(9);
        asyncMethod.getMockTime().remove(13);
        asyncMethod.getMockTime().remove(19);
        asyncMethod.getMockTime().remove(Dates.getHour());
        //other
        asyncMethod.setUserReady(false);
        asyncMethod.setGoodsReady(false);
        asyncMethod.setAuctionRunning(false);
    }

    private boolean mockHit(int round) {
        return isMockTime() && round == 5;
    }

    public boolean notConsoleTime(DemoRequest request) {
        return Dates.getSecond() < 55
                //&& !asyncMethod.getKillTime().contains(new Date().getHours())
                //&& !isTestTime()
                && !request.isAdmin();
    }

    /**
     * 服务端登录时间
     *
     * @return boolean
     */
    public boolean isTime2Login4Server() {
        return (Dates.getMinute() >= 25 && Dates.getMinute() <= 29
                && asyncMethod.getKillTime().contains(Dates.getHour())) || isMockTime();
    }

    /**
     * 客户端登录时间
     *
     * @return boolean
     */
    public boolean isTime2Login4Terminal() {
        return (Dates.getMinute() >= 27 && Dates.getMinute() <= 29
                && asyncMethod.getKillTime().contains(Dates.getHour())) || isMockTime();
    }

    /**
     * 禁止登陆
     *
     * @param username username
     * @return boolean
     */
    public boolean isLoginForbidden(String username) {
        return (Dates.getMinute() < 25 && Dates.getHour() == 10
                || Dates.getMinute() < 25 && Dates.getHour() == 14
                || Dates.getMinute() < 25 && Dates.getHour() == 20
                || Dates.getMinute() > 33 && Dates.getHour() == 9
                || Dates.getMinute() > 33 && Dates.getHour() == 13
                || Dates.getMinute() > 33 && Dates.getHour() == 19)
                && !Arrays.asList("18958258589", "18906622833").contains(username);
    }

    /**
     * 是否秒杀时刻
     *
     * @return boolean
     */
    public boolean isSeckillTime() {
        return Dates.getSecond() >= 57 && (
                Dates.getMinute() == 29 && asyncMethod.getKillTime().contains(Dates.getHour()) || isMockTime());

    }

    public boolean isMockTime() {
        return asyncMethod.getMockTime().contains(Dates.getHour());
    }


    /**
     * console
     *
     * @param request request
     */
    public synchronized String console(@RequestBody DemoRequest request) {
        //
        if (notConsoleTime(request)) {
            log.trace("自挂东南枝@[{}]:{}", asyncMethod.getTerminalName(), request);
            return "console ignore";
        }
        //输出服务器信息
        //输出客户端信息
        return consoleTerminalStatus() + "-console";
    }

    /**
     * console
     *
     * @return String
     */
    private String consoleTerminalStatus() {
        log.warn("###-[mock]:{}-[user]:{}-[{}/{}]-[goods]:{}-{}-[auction]:{}",
                this.isMockTime(),
                asyncMethod.isUserReady(), asyncMethod.getPlayerToGo().size(), terminalStore.getUserStore().size(),
                asyncMethod.isGoodsReady(), asyncMethod.getAuctionGoodStore().size(), asyncMethod.isAuctionRunning());
        //输出命中信息-邮件通知
        mailNotice();
        return "客户端仪表盘";
    }


    private void mailNotice() {
        final String[] notice = {"命中账号:"
                + "~[" + asyncMethod.getPlayerToGo().stream().filter(Player::isHit).count() + "/" + asyncMethod.getPlayerToGo().size() + "]"
                + "@[" + asyncMethod.getTerminalName() + "]"
                + "\n\n"};
        asyncMethod.getPlayerToGo().stream()
                .sorted(Comparator.comparing(Player::isHit).reversed().thenComparing(Player::getRealName).thenComparing(Player::getNickname))
                .forEach(e -> {
                    String win = e.isHit() ? "WIN" : "LOSE";
                    Object price = e.getHitAuctionGood() != null ? e.getHitAuctionGood().getPrice() : "";
                    notice[0] += "" +
                            "<" + e.getUsername() + ">" +
                            "-[" + win + "]" +
                            "-[" + price + "]" +
                            "-[" + e.getHitWithCount() + "@" + e.getAuctionCount() + "]" +
                            "-[" + e.getNickname() + "]";
                });
        if (!asyncMethod.isAlreadyNotice() && !asyncMethod.getPlayerToGo().isEmpty()) {
            String toMock = auctionStore.getMailMock();
            String toProd = "651978788@qq.com";
            toProd = auctionStore.getMailProd();
            if (isMockTime()) {
                emailService.sendMail(toMock, "JP结果通知-MOCK", notice[0]);
            } else if (!isMockTime()) {
                emailService.sendMail(toMock, "JP结果通知-PROD", notice[0]);
                emailService.sendMail(toProd, "JP结果通知-PROD", notice[0]);
            } else {
                emailService.sendMail(toMock, "JP结果通知-ERROR", notice[0]);
            }
            asyncMethod.setAlreadyNotice(true);
        }
    }
}
