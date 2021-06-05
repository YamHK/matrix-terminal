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
import top.yamhk.terminal.common.MenuTreeManager;
import top.yamhk.terminal.irepo.MenuJpaRepo;
import top.yamhk.terminal.web.AgentThreadLocal;
import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.api.request.MenuCreateRequest;
import top.yamhk.terminal.web.jpa.datamodel.base.MenuPo;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: YamHK
 * @Date: 2020/8/30 13:47
 */
@Slf4j
@RestController
@RequestMapping("/v1/menu")
@Api(value = "菜单管理", tags = {"Menu"}, description = "菜单管理")
public class MenuApi {
    /**
     * jpa
     */
    @Autowired(required = false)
    private MenuJpaRepo jpaRepo;

    /**
     * 新增
     *
     * @param request request
     * @return Response
     */
    @ApiOperation(value = "新增", notes = "--->")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "MenuCreateRequest", paramType = "body")
    @PostMapping
    public MatrixResponse addMenu(@RequestBody @Valid MenuCreateRequest request) {
        //获取用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //验重
        if (judgeRepeat(userPo, request)) {
            return MatrixResponse.code(ErrorCodes.DATA_REPEAT);
        }
        //构建对象
        MenuPo po = new MenuPo();
        BeanCopyUtils.copyProperties(request, po);
        //创建者赋值
        po.setDelFlag("0");
        po.setCreateTime(Dates.now());
        po.setCreateBy(userPo.getNickname());
        MenuPo save = jpaRepo.save(po);
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
    @ApiOperation(value = "更新_", notes = "--->")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "MenuCreateRequest", paramType = "body")
    @PutMapping("/{id}")
    public MatrixResponse updateMenu(@RequestBody @Valid MenuCreateRequest request, @PathVariable long id) {
        //获取用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询记录
        Optional<MenuPo> byId = jpaRepo.findById(id);
        if (!byId.isPresent() || !byId.get().visible(userPo)) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        //验重
        if (judgeRepeat(userPo, request)) {
            return MatrixResponse.code(ErrorCodes.DATA_REPEAT);
        }
        //构建对象-赋值
        MenuPo po = byId.get();
        BeanCopyUtils.copyProperties(request, po);
        //更新更新人
        MenuPo save = jpaRepo.save(po);
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
    private boolean judgeRepeat(Player userPo, MenuCreateRequest request) {
        MenuPo repeat = jpaRepo.findByPhoneAndMenuUrl(userPo.getPhoneNumber(), request.getMenuUrl());
        return repeat != null && repeat.visible(userPo);
    }

    /**
     * 删除-单个
     *
     * @param id id
     * @return Response
     */
    @ApiOperation(value = "删除", notes = "--->")
    @DeleteMapping("/{id}")
    public MatrixResponse deleteByMenuId(@PathVariable("id") Long id) {
        //获取用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询记录
        Optional<MenuPo> byId = jpaRepo.findById(id);
        if (!byId.isPresent() || !byId.get().visible(userPo)) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        //删除
        MenuPo save = jpaRepo.save((MenuPo) byId.get().logicDelete());
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
    public MatrixResponse<MenuPo> getByMenuId(@PathVariable("id") Long id) {
        Optional<MenuPo> byId = jpaRepo.findById(id);
        if (!byId.isPresent()) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        return MatrixResponse.ok().withData((byId.get().convertToResponse(MenuPo.class)));
    }

    /**
     * 查询-所有
     *
     * @return List
     */
    @ApiOperation(value = "获取-所有", notes = "--->")
    @GetMapping
    public MatrixResponse<MenuPo> getAllMenu() {
        //用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询-所有
        List<MenuPo> all = jpaRepo.findAll();
        List<MenuPo> responses = all.stream()
                .filter(e -> e.visible(userPo))
                .map(e -> (MenuPo) e.convertToResponse(MenuPo.class))
                .sorted(Comparator.comparing(MenuPo::getId).reversed())
                .collect(Collectors.toList());
        log.warn("{}:查询记录{}/{}", userPo.getUsername(), responses.size(), all.size());
        MenuTreeManager menuTreeManager = new MenuTreeManager(responses);
        MenuPo po = menuTreeManager.getRoot();
        return MatrixResponse.ok().withData(po);
    }
}
