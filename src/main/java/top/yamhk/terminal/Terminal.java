package top.yamhk.terminal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import top.yamhk.boot.EnableMatrixClient;
import top.yamhk.core.base.utils.XcodeUtils;
import top.yamhk.core.kernel.BeanUtils8Spring;
import top.yamhk.core.kernel.CoreServer4Mq;
import top.yamhk.core.kernel.TerminalStore;
import top.yamhk.terminal.web.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 启动类
 * spring.profiles.active=terminal
 *
 * @Author: YamHK
 * @Date: 2019/4/14 19:03
 */
@Slf4j
@EnableAsync
@EnableCaching
@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"top.yamhk.core", "top.yamhk.boot", "top.yamhk.terminal"})
@EnableMatrixClient
public class Terminal {

    /**
     * main
     *
     * @param args args
     */
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        log.warn("width -{}", screenSize.width);
        log.warn("height-{}", screenSize.height);
        log.warn("***** -{}", Math.sqrt(screenSize.width * screenSize.width + screenSize.height * screenSize.height));
        SpringApplication.run(Terminal.class, args);
        //虚拟机关闭狗子
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("[ERROR]>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>虚拟机关闭狗子");
                CoreServer4Mq.kernelNodes.forEach(e -> {
                    try {
                        e.getServerSocket().close();
                    } catch (Exception exception) {
                        log.error("", exception);
                    }
                });
                System.out.println("[ERROR]>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>虚拟机关闭狗子");
            }
        });
        //
        buildUserStore();
    }

    /**
     * 构建账号数据
     */
    private static void buildUserStore() {
        ArrayList<Player> userAccounts = new ArrayList<>(Arrays.asList(
                Player.builder().owner("18958258589").username("13429208001").vip(false).available(true).password("111111").restTime(Arrays.asList(9, 13, 19, 8, 12, 18)).nickname("小昊").authorization("").requestHead(new HashMap<>(250)).build(),
                //f
                Player.builder().owner("18958258589").username("18869972168").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("王满婷").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("13780063431").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("周伟盛").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("13175172997").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("王晓镕").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("13758600984").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("周燚").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("13618233210").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("祁佳霞").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("15158364555").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("蟑螂").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("13706542071").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("叶焘").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("13816847713").vip(false).available(true).password("123456").restTime(Arrays.asList(9, 13, 19, 8, 12, 18)).nickname("黄威").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("15000800094").vip(false).available(true).password("123456").restTime(Arrays.asList(9, 13, 19, 8, 12, 18)).nickname("林祖威").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("15267647995").vip(false).available(true).password("123456").restTime(Arrays.asList(9, 13, 19, 8, 12, 18)).nickname("叶丹敏").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("15157649141").vip(false).available(true).password("123456").restTime(Arrays.asList(9, 13, 19, 8, 12, 18)).nickname("倪君满").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("15058495237").vip(false).available(true).password("tt3200304").restTime(Arrays.asList(9, 13, 19, 8, 12, 18)).nickname("周涛涛-").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("12111111111").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("叶姗楠").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("12122222222").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("叶晓霞").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("13989677814").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("林俊伟").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("19857625459").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("林辉灵").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("12100000000").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("邵钢钢").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("12155555555").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("邵钢钢").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("19857604640").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("邵钢钢").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("15968615691").vip(false).available(false).password("111111").restTime(Arrays.asList()).nickname("邵钢钢").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("12166666660").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("姜雪雪").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("12199999999").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("周衍云").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("18906622833").vip(false).available(true).password("000000").restTime(Arrays.asList()).nickname("Nobody").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("19883674085").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("周杨").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("19883630041").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("周杨").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("19857647813").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("周杨").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("18352965133").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("周杨").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("19883651264").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("周杨").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("19857647693").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("周杨").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("15355630813").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("周杨").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("13732137762").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("周杨").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("13989654942").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("周杨").authorization("").requestHead(new HashMap<>(250)).build(),

                Player.builder().owner("18958258589").username("19884203447").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("19857604640").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("15968625327").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("19884222643").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("12100000000").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("17100750110").vip(true).available(false).password("111111").restTime(Arrays.asList()).nickname("").authorization("").requestHead(new HashMap<>(250)).build(),
                Player.builder().owner("18958258589").username("18958258589").vip(false).available(true).password("000000").restTime(Arrays.asList(9, 13, 19, 8, 12, 18)).nickname("Spider-Man").authorization("").requestHead(new HashMap<>(250)).build()
        ));
        TerminalStore terminalStore = BeanUtils8Spring.getBean(TerminalStore.class);
        String serverPort = terminalStore.getServerPort();
        String username = terminalStore.getTerminalUsername();
        String url = "http://localhost:" + serverPort + "/actuator";
        url = "http://localhost:" + serverPort + "/swagger-ui.html";
        url = "http://localhost:" + serverPort + "/actuator";
        url = "http://localhost:" + serverPort + "";
        url = "http://localhost:" + serverPort + "/swagger-ui.html";
        XcodeUtils.openUrl(url);
        userAccounts.forEach(e -> {
            terminalStore.getUserStore().add(e);
            terminalStore.getUserMap().put(e.getUsername(), e);
        });
        log.warn("用户构建完毕-{}", terminalStore.getUserStore().size());
    }
}
