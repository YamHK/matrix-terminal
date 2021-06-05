package top.yamhk.terminal.web.api.src;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.kernel.ErrorCodes;
import top.yamhk.core.kernel.MatrixResponse;
import top.yamhk.terminal.irepo.EventJpaRepo;
import top.yamhk.terminal.web.AgentThreadLocal;
import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.jpa.datamodel.src.EventPo;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: YamHK
 * @Date: 2020/8/21 9:33
 */
@Slf4j
@Api(value = "event", tags = {"events"}, description = "记录历史事件")
@RestController
@RequestMapping("/v1/event")
public class EventApi {
    /**
     * jpa
     */
    @Autowired
    private EventJpaRepo eventJpaRepo;

    /**
     * 单个删除
     *
     * @param id id
     * @return Response
     */
    @ApiOperation(value = "删除", notes = "单个")
    @DeleteMapping("/{id}")
    public MatrixResponse deleteByEventId(@PathVariable("id") Long id) {
        Optional<EventPo> byId = eventJpaRepo.findById(id);
        if (!byId.isPresent()) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        EventPo save = eventJpaRepo.save((EventPo) byId.get().logicDelete());
        log.warn("删除记录>id:" + save.getId());
        return MatrixResponse.ok().withMessage("删除成功");
    }

    /**
     * 查询-所有
     *
     * @return List
     */
    @ApiOperation(value = "获取所有记录", notes = "just notes")
    @GetMapping
    public MatrixResponse getAllEvent() {
        //登录用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询-所有
        List<EventPo> all = eventJpaRepo.findAll();
        List<EventPo> responses = all.stream()
                .filter(e -> e.visible(userPo))
                .map(e -> (EventPo) e.convertToResponse(EventPo.class))
                .sorted(Comparator.comparing(EventPo::getId).reversed())
                .collect(Collectors.toList());
        log.warn("{}:查询记录{}/{}", userPo.getUsername(), responses.size(), all.size());
        return MatrixResponse.ok().withData(responses);
    }
}
