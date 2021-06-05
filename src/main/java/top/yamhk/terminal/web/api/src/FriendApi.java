package top.yamhk.terminal.web.api.src;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.base.utils.Dates;
import top.yamhk.core.kernel.BeanCopyUtils;
import top.yamhk.core.kernel.ErrorCodes;
import top.yamhk.core.kernel.MatrixResponse;
import top.yamhk.terminal.irepo.FriendJpaRepo;
import top.yamhk.terminal.web.AgentThreadLocal;
import top.yamhk.terminal.web.FriendsResponse;
import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.api.request.FriendCreateRequest;
import top.yamhk.terminal.web.api.response.TargetResponse;
import top.yamhk.terminal.web.jpa.datamodel.src.FriendPo;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/v1/friends")
@Api(value = "好友管理", tags = {"friends"}, description = "好友管理")
public class FriendApi {
    /**
     * jpa
     */
    @Autowired(required = false)
    private FriendJpaRepo jpaRepo;

    /**
     * 新增
     *
     * @param request request
     * @return Response
     */
    @ApiOperation(value = "新增", notes = "--->添加好友")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "FriendCreateRequest", paramType = "body")
    @PostMapping
    public MatrixResponse addFriend(@RequestBody @Valid FriendCreateRequest request) {
        //获取用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //验重
        if (judgeRepeat(userPo, request)) {
            return MatrixResponse.code(ErrorCodes.DATA_REPEAT);
        }
        //构建对象
        FriendPo po = new FriendPo();
        BeanCopyUtils.copyProperties(request, po);
        //创建者赋值
        po.setDelFlag("0");
        po.setCreateTime(Dates.now());
        po.setCreateBy(userPo.getNickname());
        FriendPo save = jpaRepo.save(po);
        log.warn("创建记录>id:" + save.getId());
        return MatrixResponse.ok().withMessage("新增成功");
    }

    /**
     * 验重
     *
     * @param request request
     * @param userPo  userPo
     * @return boolean
     */
    private boolean judgeRepeat(Player userPo, FriendCreateRequest request) {
        FriendPo repeat = jpaRepo.findByPlayerAndFriendId(userPo, request.getFriendId());
        return repeat != null;
    }

    /**
     * 删除-单个
     *
     * @param id id
     * @return Response
     */
    @ApiOperation(value = "删除", notes = "单个")
    @DeleteMapping("/{id}")
    public MatrixResponse deleteByFrindId(@PathVariable("id") Long id) {
        //获取用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询记录
        Optional<FriendPo> byId = jpaRepo.findById(id);
        if (!byId.isPresent() || !byId.get().visible(userPo)) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        //删除
        FriendPo save = jpaRepo.save((FriendPo) byId.get().logicDelete());
        log.warn("删除记录>id:" + save.getId());
        return MatrixResponse.ok().withMessage("删除成功");
    }

    /**
     * 获取-某个
     *
     * @param id id
     * @return Response
     */
    @ApiOperation(value = "获取-指定", notes = "--->")
    @GetMapping("/{id}")
    public MatrixResponse<TargetResponse> getByFriendId(@PathVariable("id") Long id) {
        Optional<FriendPo> byId = jpaRepo.findById(id);
        if (!byId.isPresent()) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        return MatrixResponse.ok().withData((byId.get().convertToResponse(FriendPo.class)));
    }

    /**
     * 查询-所有
     *
     * @return List
     */
    @ApiOperation(value = "获取-所有", notes = "--->")
    @GetMapping
    public MatrixResponse<TargetResponse> getAllFriend() {
        //用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询-所有
        List<FriendPo> all = jpaRepo.findAll();
        List<FriendsResponse> responses = all.stream()
                .filter(e -> e.visible(userPo))
                .map(e -> (FriendsResponse) e.convertToResponse(FriendsResponse.class))
                .peek(FriendsResponse::desensitize)
                .sorted(Comparator.comparing(FriendsResponse::getId).reversed())
                .collect(Collectors.toList());
        log.warn("{}:查询记录{}/{}", userPo.getUsername(), responses.size(), all.size());
        return MatrixResponse.ok().withData(responses);
    }
}
