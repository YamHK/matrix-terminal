package top.yamhk.terminal.web;

import top.yamhk.core.base.Remote;
import top.yamhk.core.kernel.MatrixResponse;

import java.util.List;

/**
 * @Author: YingX
 * @Date: 2021/4/22 20:20
 */
@Remote
public interface MajorServerApi {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return UserInfo
     */
    String findByUsername(String username);

    /**
     * 根据用户名密码查询用户
     *
     * @param username 用户名
     * @return UserInfo
     */
    String findByUsernameAndPassword(String username, String password);

    MatrixResponse<String> modifyAccounts(DemoRequest request);

    MatrixResponse<String> deleteAccounts(DemoRequest request);

    MatrixResponse<PlayerResponse> getPlayers();

    String login(Player player);

    void save(Player po);

    String buildGoods();

    String buildPlayer();

    String console(DemoRequest request);

    /**
     * 是否登录时间
     *
     * @return boolean
     */
    boolean isServerLoginTime();

    List<Player> findAll();
}
