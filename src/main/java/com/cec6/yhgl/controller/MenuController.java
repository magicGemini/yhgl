package com.cec6.yhgl.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cec6.yhgl.domain.Menu;
import com.cec6.yhgl.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     *
     * **/
    @GetMapping("/menuList")
    public List<Menu> menuList() {
        List<Menu> ret = menuService.findAll();
        ret.sort(Comparator.comparingInt(Menu::getIndex));
        return ret;
    }

    /**
     * 根据username获得当前用户能够查看的菜单
     * **/
    @GetMapping("/myMenu")
    public List<Menu> myMenu() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Menu> menus = menuService.findMenusByUsername(userDetails.getUsername());
        menus.sort(Comparator.comparingInt(Menu::getIndex));
        return menus;
    }

    /**
     *
     * **/
    @PostMapping
    public String addMenu(@RequestBody Menu menu) {
        System.out.println(menu);
        Menu ret = menuService.createMenu(menu);
        if (ret.getId() != null)
            return "success";
        return "failed";
    }

    /**
     *
     * **/
    @DeleteMapping("/{ids}")
    public String delMenu(@PathVariable List<String> ids) {
        System.out.println(new HashSet<>(ids));
        menuService.deleteMenus(new HashSet<>(ids));
        return "success";
    }

    // menu tree for selector
//    @GetMapping("/menuTree")
//    public String menuTree(){
//        List<TreeNode> treeNodes = menuService.getMenuTree();
//        //tree to JSON
//        JSONArray ret = TreeNode.toJSON(treeNodes);
//        return ret.toJSONString();
//    }

    /**
     * menu tree for navigator
     **/
    @GetMapping("/menuBars")
    public String getMenuTree() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //TODO: 根据username获得当前用户能够查看的菜单
        List<Menu> menus = menuService.findMenusByUsername(userDetails.getUsername());
        menus.sort(Comparator.comparingInt(Menu::getIndex));
//        return menuService.menuList2TreeStr(menus);
        JSONArray ret = new JSONArray();
        menus.stream().forEach((menu) -> {
            JSONObject oneBar = new JSONObject();
            oneBar.put("index", menu.getIndex());
            oneBar.put("text", menu.getName());
            oneBar.put("href", menu.getUrl());
            ret.add(oneBar);
        });
        return ret.toJSONString();
    }

}
