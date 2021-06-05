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
import top.yamhk.terminal.irepo.AccountBookJpaRepo;
import top.yamhk.terminal.web.AgentThreadLocal;
import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.api.request.WageCreateRequest;
import top.yamhk.terminal.web.api.response.AccountBookResponse;
import top.yamhk.terminal.web.jpa.datamodel.src.AccountBookPo;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: YamHK
 * @Date: 2020/8/21 9:34
 */
@Slf4j
@Api(value = "???", tags = {"account-book"}, description = "记账本")
@RestController
@RequestMapping("/v1/account-book")
public class AccountBookApi {
    /**
     * jpa
     */
    @Autowired(required = false)
    private AccountBookJpaRepo jpaRepo;

    /**
     * 新增
     *
     * @param request request
     * @return Response
     */
    @ApiOperation(value = "新增", notes = "--->")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "WageCreateRequest", paramType = "body")
    @PostMapping
    public MatrixResponse addAccountBook(@RequestBody @Valid WageCreateRequest request) {
        //获取用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //构建对象
        AccountBookPo po = new AccountBookPo();
        BeanCopyUtils.copyProperties(request, po);
        //创建者赋值
        po.setDelFlag("0");
        po.setCreateTime(Dates.now());
        po.setCreateBy(userPo.getNickname());
        AccountBookPo save = jpaRepo.save(po);
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
    @ApiOperation(value = "更新", notes = "--->")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "WageCreateRequest", paramType = "body")
    @PutMapping("/{id}")
    public MatrixResponse updateOne(@RequestBody @Valid WageCreateRequest request, @PathVariable long id) {
        return MatrixResponse.ng().withMessage("不支持更新");
    }

    /**
     * 删除-单个
     *
     * @param id id
     * @return Response
     */
    @ApiOperation(value = "删除", notes = "--->")
    @DeleteMapping("/{id}")
    public MatrixResponse deleteByAccountBookId(@PathVariable("id") Long id) {
        //获取用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询记录
        Optional<AccountBookPo> byId = jpaRepo.findById(id);
        if (!byId.isPresent() || !byId.get().visible(userPo)) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        //删除
        AccountBookPo save = jpaRepo.save((AccountBookPo) byId.get().logicDelete());
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
    public MatrixResponse<AccountBookPo> getByAccountBookId(@PathVariable("id") Long id) {
        Optional<AccountBookPo> byId = jpaRepo.findById(id);
        if (!byId.isPresent()) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        return MatrixResponse.ok().withData((byId.get().convertToResponse(AccountBookPo.class)));
    }

    /**
     * 查询-所有
     *
     * @return List
     */
    @ApiOperation(value = "获取-所有", notes = "--->")
    @GetMapping
    public MatrixResponse<AccountBookPo> getAllAccountBook() {
        //用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询-所有
        List<AccountBookPo> all = jpaRepo.findByPlayerAndDelFlag(userPo, "0");
        List<AccountBookPo> responses = all.stream()
                .filter(e -> e.visible(userPo))
                .map(e -> (AccountBookPo) e.convertToResponse(AccountBookPo.class))
                .sorted(Comparator.comparing(AccountBookPo::getId).reversed())
                .collect(Collectors.toList());
        log.warn("{}:查询记录{}/{}", userPo.getUsername(), responses.size(), all.size());
        return MatrixResponse.ok().withData(responses);
    }

    /**
     * 查询-按月份查询
     *
     * @return List
     */
    @ApiOperation(value = "获取-按月份查询", notes = "--->")
    @GetMapping("/by-month")
    public MatrixResponse<AccountBookPo> getByMonth(String month) {
        //用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询-所有
        String monthLike = "all".equals(month) ? "" : month + "%";
        List<AccountBookPo> all = jpaRepo.findByPlayerAndDateLikeAndDelFlag(userPo, monthLike, "0");
        List<AccountBookPo> collect = all.stream()
                .filter(e -> e.visible(userPo))
                .map(e -> (AccountBookPo) e.convertToResponse(AccountBookPo.class))
                .sorted(Comparator.comparing(AccountBookPo::getDate).reversed())
                .collect(Collectors.toList());
        log.warn("{}:查询记录{}/{}", userPo.getUsername(), collect.size(), all.size());
        AccountBookResponse response = new AccountBookResponse();
        response.setAccountBooks(collect);
        response.setSum(collect.stream().mapToDouble(AccountBookPo::getAmount).sum());
        response.setMonth(month);
        return MatrixResponse.ok().withData(response);
    }
}
