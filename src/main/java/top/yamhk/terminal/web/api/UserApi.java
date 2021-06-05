package top.yamhk.terminal.web.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.base.utils.CipherUtil;
import top.yamhk.core.base.utils.JsonUtils;
import top.yamhk.core.base.utils.JwtUtils;
import top.yamhk.core.boot.MatrixProxyFactory;
import top.yamhk.core.kernel.BeanCopyUtils;
import top.yamhk.core.kernel.ErrorCodes;
import top.yamhk.core.kernel.MatrixResponse;
import top.yamhk.core.kernel.TerminalStore;
import top.yamhk.terminal.web.AgentThreadLocal;
import top.yamhk.terminal.web.AuthThreadLocal;
import top.yamhk.terminal.web.DemoRequest;
import top.yamhk.terminal.web.LoginRequest;
import top.yamhk.terminal.web.MajorServerApi;
import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.PlayerResponse;
import top.yamhk.terminal.web.RegisterRequest;
import top.yamhk.terminal.web.UserInfo;
import top.yamhk.terminal.web.UserInfoPool;
import top.yamhk.terminal.web.UserInfoResponse;
import top.yamhk.terminal.web.UserInfoUpdateRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: YamHK
 * @Date: 2019/4/14 19:03
 */

@Slf4j
@RestController
@RequestMapping(value = "/v1/users")
@Api(value = "users", tags = {"users"}, description = "用户管理")
public class UserApi {
    /**
     * 服务
     */
    MajorServerApi jpaRepo = MatrixProxyFactory.getProxy(MajorServerApi.class);

    /**
     *
     */
    @Autowired
    TerminalStore terminalStore;

    /**
     * 增-改
     *
     * @return PypResponse
     */
    @PatchMapping("/accounts")
    public MatrixResponse<String> modifyAccounts(@RequestBody DemoRequest request) {
        List<Player> oldStore = terminalStore.getUserStore().stream()
                .map(e -> JsonUtils.GSON.fromJson(e.toString(), Player.class))
                .filter(Player::isAvailable)
                .filter(e -> e.getOwner().equals(request.getUsername()))
                .collect(Collectors.toList());
        //
        //request.getPlayers().forEach(e -> terminalStore.getUserMap().get(e.getUsername()).setAvailable(false));
        //
        //
        request.getPlayers().forEach(e -> {
            Player player = BeanCopyUtils.copyByGson(e, Player.class);
            //
            player.setVip(false);
            player.setAvailable(true);
            //
            Player storePlayer = (Player) terminalStore.getUserMap().get(player.getUsername());
            if (storePlayer != null) {
                BeanCopyUtils.copyProperties(player, storePlayer);
            } else {
                terminalStore.getUserStore().add(player);
                terminalStore.getUserMap().put(player.getUsername(), player);
            }
        });
        //
        List<Player> newStore = terminalStore.getUserStore().stream()
                .map(e -> JsonUtils.GSON.fromJson(e.toString(), Player.class))
                .filter(Player::isAvailable)
                .filter(e -> e.getOwner().equals(request.getUsername()))
                .collect(Collectors.toList());
        return MatrixResponse.ok().withMessage("添加账号成功[" + oldStore.size() + "->" + newStore.size() + "]");
    }

    /**
     * 删
     *
     * @return PypResponse
     */
    @DeleteMapping("/accounts")
    public MatrixResponse<String> deleteAccounts(@RequestBody DemoRequest request) {
        List<Player> oldStore = terminalStore.getUserStore().stream()
                .map(e -> JsonUtils.GSON.fromJson(e.toString(), Player.class))
                .filter(Player::isAvailable)
                .filter(e -> e.getOwner().equals(request.getUsername()))
                .collect(Collectors.toList());
        //
        //request.getPlayers().forEach(e -> terminalStore.getUserMap().get(e.getUsername()).setAvailable(false));
        //
        List<Player> newStore = terminalStore.getUserStore().stream()
                .map(e -> JsonUtils.GSON.fromJson(e.toString(), Player.class))
                .filter(Player::isAvailable)
                .filter(e -> e.getOwner().equals(request.getUsername()))
                .collect(Collectors.toList());
        return MatrixResponse.ok().withMessage("删除账号成功[{" + oldStore.size() + "->" + newStore.size() + "]");
    }

    /**
     * 查
     *
     * @return PypResponse
     */
    @GetMapping("/accounts")
    public MatrixResponse<PlayerResponse> getPlayers() {
        //
        String owner = CipherUtil.defaultDecrypt(AuthThreadLocal.get());
        //
        if (StringUtils.isBlank(owner)) {
            return MatrixResponse.ng().withMessage("请提供[owner]");
        }
        //
        PlayerResponse response = new PlayerResponse();
        //
        List<PlayerResponse.PlayerDTO> collect = terminalStore.getUserStore().stream()
                .map(e -> JsonUtils.GSON.fromJson(e.toString(), Player.class))
                .filter(Player::isAvailable)
                .filter(e -> e.getOwner().equals(owner) || e.getUsername().equals(owner))
                .map(e -> BeanCopyUtils.copyByGson(e, PlayerResponse.PlayerDTO.class))
                .collect(Collectors.toList());
        //
        response.setPlayerDTOS(collect);
        response.setOwner(owner);
        response.setCount(collect.size());
        log.warn("客户信息查询:-{}-{}", owner, response);
        return MatrixResponse.ok().withData(response);
    }


    /**
     * 注册
     *
     * @param request request
     * @return Response
     */
    @ApiOperation(value = "注册", notes = "注册")
    @PostMapping(value = "register")
    public MatrixResponse doRegister(@RequestBody RegisterRequest request) {
        //校验用户是否存在
        String repeat = jpaRepo.findByUsername(request.getUsername().trim());
        if (repeat != null) {
            return MatrixResponse.ng().withMessage("用户已存在");
        }
        //
        Player po = BeanCopyUtils.copyByHuTool(request, Player.class);
        jpaRepo.save(po);
        return MatrixResponse.ok().withMessage("注册成功");
    }

    /**
     * 登录
     *
     * @param request request
     * @return Response
     */
    @ApiOperation(value = "登录", notes = "使用用户名密码登录")
    @PostMapping(value = "/login")
    @CacheEvict(cacheNames = "AllUser", allEntries = true)
    public MatrixResponse doLogin(@RequestBody LoginRequest request) {
        //校验是否已登录-token 无状态
        //校验用户名密码
        String byUsernameAndPassword = jpaRepo.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        Player po = JsonUtils.GSON.fromJson(byUsernameAndPassword, Player.class);
        if (po == null) {
            log.error("登录失败" + request);
            return MatrixResponse.code(ErrorCodes.USER_NO_LOGIN).withMessage("用戶名密码错误!");
        }
        //深拷贝还是浅拷贝-failed to lazily initialize a collection of
        UserInfo userInfo = BeanCopyUtils.copyByHuTool(po, UserInfo.class);
        UserInfoPool.USER_CASH.put(request.getUsername(), Optional.of(userInfo));
        log.warn("在线用户数:{}", UserInfoPool.USER_CASH.size());
        //生成token
        String token = JwtUtils.sign(userInfo.getUsername());
        return MatrixResponse.ok().withData(token);
    }

    /**
     * 个人资料-查询
     *
     * @return Response
     */
    @ApiOperation(value = "个人资料-查询", notes = "个人资料")
    @GetMapping(value = "/info")
    public MatrixResponse doGetUserInfo(HttpServletRequest request) {
        //校验是否已登录-token 无状态
        //获取token+校验
        String token = request.getHeader("accessToken");
        if (StringUtils.isBlank(token)) {
            return MatrixResponse.code(ErrorCodes.USER_NO_LOGIN).withMessage("请先登录!");
        }
        String username = JwtUtils.getUsername(token);
        //校验用户名密码
        String byUsername = jpaRepo.findByUsername(username);
        Player po = JsonUtils.GSON.fromJson(byUsername, Player.class);
        if (po == null) {
            log.error("个人信息获取失败" + request);
            return MatrixResponse.ng().withMessage("个人信息获取失败!");
        }
        //深拷贝还是浅拷贝-failed to lazily initialize a collection of
        UserInfoResponse userInfo = BeanCopyUtils.copyByHuTool(po, UserInfoResponse.class);
        //生成token
        return MatrixResponse.ok().withData(userInfo);
    }

    /**
     * 个人资料-更新
     *
     * @return Response
     */
    @ApiOperation(value = "个人资料-更新", notes = "个人资料")
    @PutMapping(value = "/info")
    public MatrixResponse doUpdateUserInfo(HttpServletRequest request, @RequestBody UserInfoUpdateRequest updateRequest) {
        //校验是否已登录-token 无状态
        String token = request.getHeader("accessToken");
        if (StringUtils.isBlank(token)) {
            return MatrixResponse.code(ErrorCodes.USER_NO_LOGIN).withMessage("请先登录!");
        }
        String username = JwtUtils.getUsername(token);
        //校验用户名密码
        String byUsername = jpaRepo.findByUsername(username);
        Player po = JsonUtils.GSON.fromJson(byUsername, Player.class);
        if (po == null) {
            log.error("个人信息获取失败" + request.toString());
            return MatrixResponse.ng().withMessage("个人信息获取失败!");
        }
        //深拷贝还是浅拷贝-failed to lazily initialize a collection of
        log.warn("更新资料" + updateRequest);
        //BeanCopyUtils.copyByHuTool(updateRequest, Player.class);
        jpaRepo.save(po);
        return MatrixResponse.ok().withMessage("更新成功");
    }

    /**
     * 查询-所有
     * cash时间是多少？
     *
     * @return List
     */
    @ApiOperation(value = "获取所有记录", notes = "just notes")
    @GetMapping
    @Cacheable(cacheNames = "AllUser")
    public MatrixResponse getAllUser() {
        //登录用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询-所有
        List<Player> all = jpaRepo.findAll();
        List<UserInfoResponse> responses = all.stream()
                .map(e -> (UserInfoResponse) e.convertToResponse(UserInfoResponse.class))
                .peek(UserInfoResponse::desensitize)
                .sorted(Comparator.comparing(UserInfoResponse::getUsername).reversed())
                .collect(Collectors.toList());
        log.warn("{}:查询记录{}/{}", userPo.getUsername(), responses.size(), all.size());
        return MatrixResponse.ok().withData(responses);
    }
}

