package com.cec6.yhgl.service;

import com.cec6.yhgl.dao.MenuMapper;
import com.cec6.yhgl.domain.Menu;
import com.cec6.yhgl.table.TB_MENU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    public List<Menu> findAll() {
        List<Menu> ret = new ArrayList<>();
        menuMapper.selectList(null).stream().forEach((menu) -> ret.add(new Menu(menu)));
        return ret;
    }

    public Menu createMenu(Menu menu) {
        if (menu.getIsValid() == null || menu.getIsValid() == false)
            menu.setIsValid(true);
        if (menu.getId() == null)
            menu.setId(UUID.randomUUID().toString());
        menuMapper.insert(new TB_MENU(menu));
        return menu;
    }

    public void deleteMenus(Set<String> ids) {
        for (String id : ids)
            menuMapper.deleteById(id);
    }

    public List<Menu> findMenusByUsername(String username) {
        List<TB_MENU> menus = menuMapper.findMenusByUsername(username);
        List ret = new ArrayList();
        menus.stream().forEach((menu) -> ret.add(new Menu(menu)));
        return ret;
    }

//    public List<TreeNode> getMenuTree() {
//        List<Menu> menus = menuDao.findAll();
//        // list to tree
//        List<TreeNode> treeNodes = new ArrayList<>();
//        for (Menu menu : menus) {
//            if (menu.getParentId() == null || menu.getParentId().trim().isEmpty()) {
//                TreeNode node = new TreeNode();
//                node.setId(menu.getId());
//                node.setParentId(menu.getParentId());
//                node.setName(menu.getName());
//                List<TreeNode> chilren = findChildren(menu, menus);
//                node.setChildren(chilren.size() == 0 ? null : chilren);
//                treeNodes.add(node);
//            }
//        }
//        return treeNodes;
//    }
//
//    private List<TreeNode> findChildren(Menu parentMenu, List<Menu> menus) {
//        List<TreeNode> children = new ArrayList<>();
//        for (Menu menu : menus) {
//            if (menu.getParentId() == null || menu.getParentId().trim().isEmpty())
//                continue;
//            if (menu.getParentId().equals(parentMenu.getId())) {
//                TreeNode child = new TreeNode();
//                child.setName(menu.getName());
//                child.setId(menu.getId());
//                child.setParentId(menu.getParentId());
//                List<TreeNode> grandChild = findChildren(menu, menus);
//                child.setChildren(grandChild.size() == 0 ? null : grandChild);
//                children.add(child);
//            }
//        }
//        return children;
//    }
//
//    public String menuList2TreeStr(List<Menu> menus) {
//        JSONArray ret = new JSONArray();
//        // add all root nodes
//        for (Menu menu : menus) {
//            if (menu.getParentId() == null || menu.getParentId().trim().isEmpty()) {
//                JSONObject node = new JSONObject();
//                node.put("id", menu.getId());
//                node.put("text", menu.getName());
//                if (menu.getUrl() != null && !menu.getUrl().trim().equals(""))
//                    node.put("href", menu.getUrl());
//                ret.add(node);
//            }
//        }
//        // add child nodes
//        for (Menu menu : menus) {
//            if (menu.getParentId() != null) {
//                JSONObject node = new JSONObject();
//                node.put("id", menu.getId());
//                node.put("text", menu.getName());
//                if (menu.getUrl() != null && !menu.getUrl().trim().equals(""))
//                    node.put("href", menu.getUrl());
//
//                JSONObject parent = findNode(ret, menu.getParentId());
//                if (parent == null)
//                    System.err.println("Cannot find node[id:" + menu.getId() + ",parentId:" + menu.getParentId() + "]'s parent!");
//                if (parent.get("nodes") == null) {
//                    parent.put("nodes", new JSONArray());
//                }
//                ((JSONArray) parent.get("nodes")).add(node);
//            }
//        }
//        return ret.toJSONString();
//    }
//
//    private JSONObject findNode(JSONArray tree, String nodeId) {
//        for (int i = 0; i < tree.size(); i++) {
//            JSONObject node = (JSONObject) tree.get(i);
//            if (node.get("id").equals(nodeId))
//                return node;
//            if (node.get("nodes") == null || ((JSONArray) node.get("nodes")).size() == 0)
//                continue;
//            return findNode((JSONArray) node.get("nodes"), nodeId);
//        }
//        return null;
//    }
}
