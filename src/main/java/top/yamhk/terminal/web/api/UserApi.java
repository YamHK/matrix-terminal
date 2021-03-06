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
@Api(value = "users", tags = {"users"}, description = "????????????")
public class UserApi {
    /**
     * ??????
     */
    MajorServerApi jpaRepo = MatrixProxyFactory.getProxy(MajorServerApi.class);

    /**
     *
     */
    @Autowired
    TerminalStore terminalStore;

    /**
     * ???-???
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
        return MatrixResponse.ok().withMessage("??????????????????[" + oldStore.size() + "->" + newStore.size() + "]");
    }

    /**
     * ???
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
        return MatrixResponse.ok().withMessage("??????????????????[{" + oldStore.size() + "->" + newStore.size() + "]");
    }

    /**
     * ???
     *
     * @return PypResponse
     */
    @GetMapping("/accounts")
    public MatrixResponse<PlayerResponse> getPlayers() {
        //
        String owner = CipherUtil.defaultDecrypt(AuthThreadLocal.get());
        //
        if (StringUtils.isBlank(owner)) {
            return MatrixResponse.ng().withMessage("?????????[owner]");
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
        log.warn("??????????????????:-{}-{}", owner, response);
        return MatrixResponse.ok().withData(response);
    }


    /**
     * ??????
     *
     * @param request request
     * @return Response
     */
    @ApiOperation(value = "??????", notes = "??????")
    @PostMapping(value = "register")
    public MatrixResponse doRegister(@RequestBody RegisterRequest request) {
        //????????????????????????
        String repeat = jpaRepo.findByUsername(request.getUsername().trim());
        if (repeat != null) {
            return MatrixResponse.ng().withMessage("???????????????");
        }
        //
        Player po = BeanCopyUtils.copyByHuTool(request, Player.class);
        jpaRepo.save(po);
        return MatrixResponse.ok().withMessage("????????????");
    }

    /**
     * ??????
     *
     * @param request request
     * @return Response
     */
    @ApiOperation(value = "??????", notes = "???????????????????????????")
    @PostMapping(value = "/login")
    @CacheEvict(cacheNames = "AllUser", allEntries = true)
    public MatrixResponse doLogin(@RequestBody LoginRequest request) {
        //?????????????????????-token ?????????
        //?????????????????????
        String byUsernameAndPassword = jpaRepo.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        Player po = JsonUtils.GSON.fromJson(byUsernameAndPassword, Player.class);
        if (po == null) {
            log.error("????????????" + request);
            return MatrixResponse.code(ErrorCodes.USER_NO_LOGIN).withMessage("?????????????????????!");
        }
        //????????????????????????-failed to lazily initialize a collection of
        UserInfo userInfo = BeanCopyUtils.copyByHuTool(po, UserInfo.class);
        UserInfoPool.USER_CASH.put(request.getUsername(), Optional.of(userInfo));
        log.warn("???????????????:{}", UserInfoPool.USER_CASH.size());
        //??????token
        String token = JwtUtils.sign(userInfo.getUsername());
        return MatrixResponse.ok().withData(token);
    }

    /**
     * ????????????-??????
     *
     * @return Response
     */
    @ApiOperation(value = "????????????-??????", notes = "????????????")
    @GetMapping(value = "/info")
    public MatrixResponse doGetUserInfo(HttpServletRequest request) {
        //?????????????????????-token ?????????
        //??????token+??????
        String token = request.getHeader("accessToken");
        if (StringUtils.isBlank(token)) {
            return MatrixResponse.code(ErrorCodes.USER_NO_LOGIN).withMessage("????????????!");
        }
        String username = JwtUtils.getUsername(token);
        //?????????????????????
        String byUsername = jpaRepo.findByUsername(username);
        Player po = JsonUtils.GSON.fromJson(byUsername, Player.class);
        if (po == null) {
            log.error("????????????????????????" + request);
            return MatrixResponse.ng().withMessage("????????????????????????!");
        }
        //????????????????????????-failed to lazily initialize a collection of
        UserInfoResponse userInfo = BeanCopyUtils.copyByHuTool(po, UserInfoResponse.class);
        //??????token
        return MatrixResponse.ok().withData(userInfo);
    }

    /**
     * ????????????-??????
     *
     * @return Response
     */
    @ApiOperation(value = "????????????-??????", notes = "????????????")
    @PutMapping(value = "/info")
    public MatrixResponse doUpdateUserInfo(HttpServletRequest request, @RequestBody UserInfoUpdateRequest updateRequest) {
        //?????????????????????-token ?????????
        String token = request.getHeader("accessToken");
        if (StringUtils.isBlank(token)) {
            return MatrixResponse.code(ErrorCodes.USER_NO_LOGIN).withMessage("????????????!");
        }
        String username = JwtUtils.getUsername(token);
        //?????????????????????
        String byUsername = jpaRepo.findByUsername(username);
        Player po = JsonUtils.GSON.fromJson(byUsername, Player.class);
        if (po == null) {
            log.error("????????????????????????" + request.toString());
            return MatrixResponse.ng().withMessage("????????????????????????!");
        }
        //????????????????????????-failed to lazily initialize a collection of
        log.warn("????????????" + updateRequest);
        //BeanCopyUtils.copyByHuTool(updateRequest, Player.class);
        jpaRepo.save(po);
        return MatrixResponse.ok().withMessage("????????????");
    }

    /**
     * ??????-??????
     * cash??????????????????
     *
     * @return List
     */
    @ApiOperation(value = "??????????????????", notes = "just notes")
    @GetMapping
    @Cacheable(cacheNames = "AllUser")
    public MatrixResponse getAllUser() {
        //??????????????????
        Player userPo = AgentThreadLocal.convertToPo();
        //??????-??????
        List<Player> all = jpaRepo.findAll();
        List<UserInfoResponse> responses = all.stream()
                .map(e -> (UserInfoResponse) e.convertToResponse(UserInfoResponse.class))
                .peek(UserInfoResponse::desensitize)
                .sorted(Comparator.comparing(UserInfoResponse::getUsername).reversed())
                .collect(Collectors.toList());
        log.warn("{}:????????????{}/{}", userPo.getUsername(), responses.size(), all.size());
        return MatrixResponse.ok().withData(responses);
    }
}

