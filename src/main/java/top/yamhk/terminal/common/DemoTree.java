package top.yamhk.terminal.common;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @Author: YamHK
 * @Date: 2020/8/30 13:45
 */
@Data
public class DemoTree {
    /**
     *
     */
    private List<DemoTree> childNode = Lists.newArrayList();

    /**
     * 子节点
     *
     * @param demoTree demoTree
     */

    public void addChildNode(DemoTree demoTree) {
        this.childNode.add(demoTree);
    }
}
