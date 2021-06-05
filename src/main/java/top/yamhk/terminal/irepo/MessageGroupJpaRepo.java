package top.yamhk.terminal.irepo;


import top.yamhk.terminal.web.jpa.datamodel.src.MessageGroupPo;

import java.util.List;

/**
 * @author YamHK
 */
public interface MessageGroupJpaRepo {
    /**
     * 最近联系人
     *
     * @param username username
     * @param msgGroup msgGroup
     * @return MessageGroupPo
     */
    MessageGroupPo findByUsernameAndMsgGroup(String username, String msgGroup);

    /**
     * save
     *
     * @param messageGroupPo messageGroupPo
     */
    void save(MessageGroupPo messageGroupPo);

    /**
     * findAll
     *
     * @return List
     */
    List<MessageGroupPo> findAll();
}
