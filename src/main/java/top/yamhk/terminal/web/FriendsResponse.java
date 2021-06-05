package top.yamhk.terminal.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class FriendsResponse extends BaseSrcResponse {
    /**
     * 好友id
     */
    private String friendId;
    private boolean online;
    private String ic;
    private String username;
    private String vLong;
    private String phone;

    public FriendsResponse desensitize() {
        try {
            this.online = UserInfoPool.USER_CASH.get(this.friendId).isPresent();
        } catch (Exception e) {
            log.error("判断好友在线状态异常:{}", e.getMessage());
        }
        return this;
    }
}
