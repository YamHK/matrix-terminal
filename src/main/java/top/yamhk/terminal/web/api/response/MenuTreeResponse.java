package top.yamhk.terminal.web.api.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.yamhk.terminal.web.BaseSrcResponse;
import top.yamhk.terminal.web.jpa.datamodel.base.MenuPo;

import java.util.List;

/**
 * @Author: YamHK
 * @Date: 2020/8/30 14:18
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class MenuTreeResponse extends BaseSrcResponse {
    MenuPo node;
    List<MenuPo> childList;
}
