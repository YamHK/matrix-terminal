package top.yamhk.terminal.web.api.src;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.base.MsgType;
import top.yamhk.core.base.utils.Dates;
import top.yamhk.core.base.utils.JsonUtils;
import top.yamhk.core.kernel.BeanCopyUtils;
import top.yamhk.core.kernel.ErrorCodes;
import top.yamhk.core.kernel.MatrixResponse;
import top.yamhk.terminal.irepo.MessageGroupJpaRepo;
import top.yamhk.terminal.irepo.MessageJpaRepo;
import top.yamhk.terminal.web.AgentThreadLocal;
import top.yamhk.terminal.web.MajorServerApi;
import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.api.request.MsgCreateRequest;
import top.yamhk.terminal.web.api.service.MessageGroupService;
import top.yamhk.terminal.web.jpa.datamodel.src.MessageGroupPo;
import top.yamhk.terminal.web.jpa.datamodel.src.MessagePo;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: YamHK
 * @Date: 2020/8/21 9:34
 */
@Slf4j
@Api(value = "message", tags = {"TalkToMe"}, description = "聊天/留言")
@RestController
@RequestMapping("/v1/message")
public class MsgApi {
    /**
     * jpa
     */
    @Autowired(required = false)
    private MessageJpaRepo jpaRepo;
    /**
     * 最近联系人
     */
    @Autowired(required = false)
    private MessageGroupJpaRepo messageGroupJpaRepo;
    /**
     * 用户信息查询
     */
    @Autowired
    private MajorServerApi userInfoJpaRepo;
    /**
     * msgGroup
     */
    @Autowired
    private MessageGroupService messageGroupService;
    /**
     * jdbc
     */
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 查询-所有
     *
     * @return List
     */
    @ApiOperation(value = "配置信息", notes = "支持的模块")
    @GetMapping("/config-msg-group")
    @Cacheable(cacheNames = "msg-group")
    public MatrixResponse getConfig() throws InterruptedException {
        //获取用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //配置
        Map<Integer, String> map = new HashMap<>(12);
        map.put(-1, MsgType.SINGLE2ALL.getDesc());
        map.put(MsgType.of(0).getCode(), MsgType.SINGLE2SELF.getDesc());
        map.put(MsgType.of(1).getCode(), MsgType.SINGLE2SINGLE.getDesc());
        map.put(MsgType.of(2).getCode(), MsgType.SINGLE2GROUP.getDesc());
        map.put(MsgType.of(3).getCode(), MsgType.MSG_BOARD.getDesc());
        map.put(MsgType.of(4).getCode(), MsgType.HEART_BEAT.getDesc());
        log.warn("{}:查询配置信息{}", userPo.getUsername(), map.size());
        return MatrixResponse.ok().withData(map);
    }

    /**
     * 新增
     *
     * @param request request
     * @return Response
     */
    @ApiOperation(value = "新增-talk to somebody", notes = "--->")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "MsgCreateRequest", paramType = "body")
    @PostMapping
    @Transactional
    public MatrixResponse addMsg(@RequestBody @Valid MsgCreateRequest request) {
        //获取用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //构建消息分组
        request.setMsgGroup(request.buildMsgGroup());
        //收件人
        String byUsername = userInfoJpaRepo.findByUsername(request.getAddressee());
        Player addressee = JsonUtils.GSON.fromJson(byUsername, Player.class);
        if (addressee == null) {
            return MatrixResponse.ng().withMessage("收件人不存在");
        }
        //对方未读消息加一
        messageGroupService.increase(addressee, 1, userPo, request);
        //我的未读消息加零
        messageGroupService.increase(userPo, 0, addressee, request);
        //构建对象
        MessagePo messagePo = new MessagePo();
        BeanCopyUtils.copyProperties(request, messagePo);
        //创建者赋值
        messagePo.setDelFlag("0");
        messagePo.setCreateTime(Dates.now());
        messagePo.setCreateBy(userPo.getNickname());
        MessagePo save = jpaRepo.save(messagePo);
        log.warn("创建记录>id:" + save.getId());
        return MatrixResponse.ok().withMessage("新增成功");
    }

    /**
     * 已读
     *
     * @param request request
     * @return Response
     */
    @ApiOperation(value = "已读", notes = "--->")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "MsgCreateRequest", paramType = "body")
    @PostMapping("/read")
    public MatrixResponse addRead(@RequestBody @Valid MsgCreateRequest request) {
        //获取用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //构建消息分组
        request.setMsgGroup(request.buildMsgGroup());
        //构建对象
        Map<String, String> map = new HashMap<>(5);
        map.put("msgId", request.getId().toString());
        map.put("status", "1");
        int update = namedParameterJdbcTemplate.update("insert into cm_src_msg_status(msg_id,user_id,status) values(:msgId,:userId,:status)", map);
        //未读消息加一
        messageGroupService.decrease(userPo, 1, request);
        log.warn("插入语句结果:{}", update);
        return MatrixResponse.ok().withMessage("新增成功");
    }

    /**
     * 单个删除
     *
     * @param id id
     * @return Response
     */
    @ApiOperation(value = "删除", notes = "单个")
    @DeleteMapping("/{id}")
    public MatrixResponse delete(@PathVariable("id") Long id) {
        Optional<MessagePo> byId = jpaRepo.findById(id);
        if (!byId.isPresent()) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        MessagePo save = jpaRepo.save((MessagePo) byId.get().logicDelete());
        log.warn("删除记录>id:" + save.getId());
        return MatrixResponse.ok().withMessage("删除成功");
    }

    /**
     * 查询-所有
     *
     * @return List
     */
    @ApiOperation(value = "获取最近聊天记录", notes = "just notes")
    @GetMapping("/recently")
    public MatrixResponse getAllMsg() {
        //登录用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询-所有
        List<MessageGroupPo> all = messageGroupJpaRepo.findAll();
        List<MessageGroupPo> responses = all.stream()
                .filter(e -> e.visible(userPo))
                .map(e -> (MessageGroupPo) e.convertToResponse(MessageGroupPo.class))
                .sorted(Comparator.comparing(MessageGroupPo::getCreateTime).reversed())
                .collect(Collectors.toList());
        log.warn("{}:查询记录{}/{}", userPo.getUsername(), responses.size(), all.size());
        return MatrixResponse.ok().withData(responses);
    }

    /**
     * 获取指定模块消息
     *
     * @return List
     */
    @ApiOperation(value = "获取指定模块消息", notes = "just notes")
    @PostMapping("/by-group")
    public MatrixResponse getGroup(@RequestBody @Valid MsgCreateRequest request) {
        //构建消息分组
        request.setMsgGroup(request.buildMsgGroup());
        //登录用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询-所有
        List<MessagePo> all = jpaRepo.findByMsgGroup(request.getMsgGroup());
        Map<String, String> map = new HashMap<>(5);
        List<MessagePo> responses = all.stream()
                .filter(e -> e.visible(userPo))
                .filter(e -> request.getMsgGroup().equals(e.getMsgGroup()))
                .map(e -> (MessagePo) e.convertToResponse(MessagePo.class))
                .sorted(Comparator.comparing(MessagePo::getId))
                .peek(e -> markMsgStatus(userPo, map, e))
                .collect(Collectors.toList());
        log.warn("{}:查询记录{}/{}", userPo.getUsername(), responses.size(), all.size());
        return MatrixResponse.ok().withData(responses);
    }

    /**
     * 标记消息状态
     *
     * @param userPo userPo
     * @param map    map
     * @param e      e
     */
    private void markMsgStatus(Player userPo, Map<String, String> map, MessagePo e) {
        //构建对象
        map.put("msgId", e.getId().toString());
        List<MsgStatus> statuses = namedParameterJdbcTemplate.query("select a.msg_id,a.user_id,a.`status` from cm_src_msg_status a where a.msg_id=:msgId and user_id=:userId "
                , map, (x, y) -> {
                    MsgStatus msgStatus = new MsgStatus();
                    msgStatus.setStatus(x.getString(3));
                    return msgStatus;
                });
        boolean b = statuses.size() > 0;
        e.setStatus(Boolean.toString(b));
    }

    @Data
    private static class MsgStatus {
        /**
         * 消息状态
         */
        private String status;
    }
}
