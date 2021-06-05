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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.base.utils.Dates;
import top.yamhk.core.kernel.BeanCopyUtils;
import top.yamhk.core.kernel.ErrorCodes;
import top.yamhk.core.kernel.MatrixResponse;
import top.yamhk.terminal.irepo.TargetJpaRepo;
import top.yamhk.terminal.web.AgentThreadLocal;
import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.api.request.TargetCreateRequest;
import top.yamhk.terminal.web.api.response.TargetResponse;
import top.yamhk.terminal.web.jpa.datamodel.src.TargetPo;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: YamHK
 * @Date: 2019/4/14 19:03
 */
@Api(value = "记录特殊日期", tags = {"targets"}, description = "记录特殊日期")
@Slf4j
@RestController
@RequestMapping("/v1/targets")
public class TargetApi extends NormalApi<TargetPo> {

    /**
     * jpa
     */
    @Autowired(required = false)
    private TargetJpaRepo jpaRepo;

    /**
     * 新增
     *
     * @param request request
     * @return Response
     */
    @ApiOperation(value = "新增_计时器/其他", notes = "正计时,倒计时,日期格式yyyy-mm-dd")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "TargetCreateRequest", paramType = "body")
    @PostMapping
    public MatrixResponse addTarget(@RequestBody @Valid TargetCreateRequest request) {
        //获取用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //验重
        if (judgeRepeat(userPo, request)) {
            return MatrixResponse.code(ErrorCodes.DATA_REPEAT);
        }
        //构建对象
        TargetPo po = new TargetPo();
        BeanCopyUtils.copyProperties(request, po);
        //创建者赋值
        po.setDelFlag("0");
        po.setCreateTime(Dates.now());
        po.setCreateBy(userPo.getNickname());
        TargetPo save = jpaRepo.save(po);
        log.warn("创建记录>id:" + save.getId());
        return MatrixResponse.ok().withMessage("新增成功");
    }

    /**
     * 更新_计时器
     *
     * @param request request
     * @param id      id
     * @return Response
     */
    @ApiOperation(value = "更新_计时器/其他", notes = "正计时,倒计时,日期格式yyyy-mm-dd")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "TargetCreateRequest", paramType = "body")
    @PutMapping("/{id}")
    public MatrixResponse updateTarget(@RequestBody @Valid TargetCreateRequest request, @PathVariable long id) {
        //获取用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询记录
        Optional<TargetPo> byId = jpaRepo.findById(id);
        if (!byId.isPresent() || !byId.get().visible(userPo)) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        //验重
        if (judgeRepeat(userPo, request)) {
            return MatrixResponse.code(ErrorCodes.DATA_REPEAT);
        }
        //构建对象-赋值
        TargetPo po = byId.get();
        BeanCopyUtils.copyProperties(request, po);
        //更新更新人
        TargetPo save = jpaRepo.save(po);
        log.warn("更新记录>id:" + save.getId());
        return MatrixResponse.ok().withMessage("更新成功");
    }

    /**
     * 验重
     *
     * @param request request
     * @param userPo  userPo
     * @return boolean
     */
    private boolean judgeRepeat(Player userPo, TargetCreateRequest request) {
        TargetPo repeat = jpaRepo.findByPlayerAndDate(userPo, request.getDate());
        return repeat != null && repeat.visible(userPo);
    }

    /**
     * 删除-单个
     *
     * @param id id
     * @return Response
     */
    @ApiOperation(value = "删除", notes = "单个")
    @DeleteMapping("/{id}")
    public MatrixResponse deleteByTargetId(@PathVariable("id") Long id) {
        //获取用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询记录
        Optional<TargetPo> byId = jpaRepo.findById(id);
        if (!byId.isPresent() || !byId.get().visible(userPo)) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        //删除
        TargetPo save = jpaRepo.save((TargetPo) byId.get().logicDelete());
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
    public MatrixResponse<TargetResponse> getByTargetId(@PathVariable("id") Long id) {
        Optional<TargetPo> byId = jpaRepo.findById(id);
        if (!byId.isPresent()) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        return MatrixResponse.ok().withData((byId.get().convertToResponse(TargetResponse.class)));
    }

    /**
     * 查询-所有
     *
     * @return List
     */
    @ApiOperation(value = "获取-所有", notes = "--->")
    @GetMapping
    public MatrixResponse<TargetResponse> getAllTarget() {
        //用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询-所有
        List<TargetPo> all = jpaRepo.findAll();
        List<TargetResponse> responses = all.stream()
                .filter(e -> e.visible(userPo))
                .map(e -> (TargetResponse) e.convertToResponse(TargetResponse.class))
                .sorted(Comparator.comparing(TargetResponse::getDate).reversed())
                .collect(Collectors.toList());
        log.warn("{}:查询记录{}/{}", userPo.getUsername(), responses.size(), all.size());
        return MatrixResponse.ok().withData(responses);
    }
}
