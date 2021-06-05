package top.yamhk.terminal.web.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yamhk.core.base.utils.Dates;
import top.yamhk.terminal.irepo.MessageGroupJpaRepo;
import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.api.request.MsgCreateRequest;
import top.yamhk.terminal.web.jpa.datamodel.src.MessageGroupPo;

/**
 * @Author: YamHK
 * @Date: 2020/9/1 8:22
 */
@Slf4j
@Service
public class MessageGroupService {
    /**
     * 最近联系人
     */
    @Autowired(required = false)
    private MessageGroupJpaRepo messageGroupJpaRepo;

    /**
     * 未读消息加一
     *
     * @param addressee addressee
     * @param msgCount  msgCount
     * @param from      from
     * @param request   request
     * @return boolean
     */
    public void increase(Player addressee, int msgCount, Player from, MsgCreateRequest request) {

        MessageGroupPo messageGroupPo = messageGroupJpaRepo.findByUsernameAndMsgGroup(addressee.getUsername(), request.getMsgGroup());
        if (messageGroupPo != null) {
            increase(messageGroupPo, msgCount, request, from);
            log.warn("消息组更新+{}", addressee.getUsername());
        } else {
            log.warn("消息组新增+{}", addressee.getUsername());
            create(addressee, msgCount, request, from);
        }
    }

    public void decrease(Player userPo, int msgCount, MsgCreateRequest request) {
        MessageGroupPo messageGroupPo = messageGroupJpaRepo.findByUsernameAndMsgGroup(userPo.getUsername(), request.getMsgGroup());
        if (messageGroupPo != null) {
            decrease(messageGroupPo, msgCount, request);
            log.warn("消息组更新-{}", userPo.getUsername());
        } else {
            log.error("消息组新增???不存在的{}", userPo.getUsername());
        }
    }

    /**
     * 消息组创建
     *
     * @param userPo   userPo
     * @param msgCount msgCount
     * @param request  request
     * @param from     from
     */
    public void create(Player userPo, int msgCount, MsgCreateRequest request, Player from) {
        MessageGroupPo messageGroupPo = new MessageGroupPo();
        messageGroupPo.setMsgCount(msgCount);
        messageGroupPo.setMsgGroup(request.getMsgGroup());
        messageGroupPo.setAddressee(from.getUsername());
        //
        messageGroupPo.setGroupNick(from.getNickname());
        messageGroupPo.setCreateTime(Dates.now());
        messageGroupPo.setLastType(request.getMsgType());
        messageGroupPo.setLastContent(request.getContent());
        messageGroupJpaRepo.save(messageGroupPo);
    }

    /**
     * 未读消息加一
     *
     * @param messageGroupPo messageGroupPo
     * @param msgCount       msgCount
     * @param request        request
     * @param from           from
     */
    public void increase(MessageGroupPo messageGroupPo, int msgCount, MsgCreateRequest request, Player from) {
        messageGroupPo.setMsgCount(messageGroupPo.getMsgCount() + msgCount);
        //
        messageGroupPo.setGroupNick(from.getNickname());
        messageGroupPo.setCreateTime(Dates.now());
        messageGroupPo.setLastType(request.getMsgType());
        messageGroupPo.setLastContent(request.getContent());
        messageGroupJpaRepo.save(messageGroupPo);
    }

    /**
     * 未读消息减一
     *
     * @param messageGroupPo messageGroupPo
     * @param msgCount       msgCount
     * @param request        request
     */
    public void decrease(MessageGroupPo messageGroupPo, int msgCount, MsgCreateRequest request) {
        messageGroupPo.setMsgCount(messageGroupPo.getMsgCount() + msgCount);
        //
        messageGroupPo.setGroupIcon(request.getReserve());
        messageGroupPo.setCreateTime(Dates.now());
        messageGroupPo.setLastType(request.getMsgType());
        messageGroupPo.setLastContent(request.getContent());
        messageGroupJpaRepo.save(messageGroupPo);
    }
}
