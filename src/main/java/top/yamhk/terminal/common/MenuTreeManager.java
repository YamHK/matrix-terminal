package top.yamhk.terminal.common;

import top.yamhk.terminal.web.jpa.datamodel.base.MenuPo;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: YamHK
 * @Date: 2020/8/30 14:25
 */
public class MenuTreeManager {
    /**
     * 根节点的父节点
     */
    private final Long ROOT_PARENT_ID = -1L;
    /**
     * 数据
     */
    List<MenuPo> demoTrees;
    /**
     * 根节点
     */
    private MenuPo root;

    /**
     * 构造函数
     */
    public MenuTreeManager(List<MenuPo> demoTrees) {
        this.demoTrees = demoTrees;
        generateTree();
    }

    private void generateTree() {
        HashMap<Long, MenuPo> nodeMap = buildNodeMap();
        putChildIntoParent(nodeMap);
    }

    private HashMap<Long, MenuPo> buildNodeMap() {
        HashMap<Long, MenuPo> nodeMap = new HashMap<>(55);
        this.demoTrees.forEach(e -> {
            if (ROOT_PARENT_ID.equals(e.getOriginId())) {
                this.root = e;
            }
            nodeMap.put(e.getId(), e);
        });
        return nodeMap;
    }

    private void putChildIntoParent(HashMap<Long, MenuPo> nodeMap) {
        nodeMap.values().forEach(e -> {
            Long parentId = e.getOriginId();
            if (nodeMap.containsKey(parentId)) {
                //nodeMap.get(parentId).addChildNode(e);
            }
        });
    }

    public MenuPo getRoot() {
        return this.root;
    }
}
