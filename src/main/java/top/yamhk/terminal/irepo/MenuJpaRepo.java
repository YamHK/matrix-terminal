package top.yamhk.terminal.irepo;

import top.yamhk.terminal.web.jpa.datamodel.base.MenuPo;

/**
 * @Author: YamHK
 * @Date: 2020/8/30 13:51
 */
public interface MenuJpaRepo extends MatrixJpa<MenuPo> {
    /**
     * 用户名下菜单
     *
     * @param phone   phone
     * @param menuUrl menuUrl
     * @return MenuPo
     */
    MenuPo findByPhoneAndMenuUrl(Integer phone, String menuUrl);
}
