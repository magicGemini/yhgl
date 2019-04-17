package com.cec6.yhgl.controller;

import com.cec6.yhgl.domain.HtmlElement;
import com.cec6.yhgl.service.HtmlElementService;
import com.cec6.yhgl.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/htmlElement")
public class HtmlElementController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private HtmlElementService htmlElementService;

    @GetMapping("/elementList")
    public List<HtmlElement> elementList() {
        return htmlElementService.findAll();
    }

    @PostMapping
    public String createElement(@RequestBody HtmlElement htmlElement) {
        htmlElementService.addOne(htmlElement);
        return "success";
    }

    @DeleteMapping("/{ids}")
    public String deleteElement(@PathVariable List<String> ids) {
        for (String id : new HashSet<>(ids)) {
            htmlElementService.deleteElement(id);
        }
        return "success";
    }

//    @GetMapping("/htmlElementTree")
//    public String htmlElementTree() {
//        // get menu tree
//        List<TreeNode> treeNodes = menuService.getMenuTree();
//        // get all htmlElement
//        List<HtmlElement> elements = htmlElementService.findAll();
//        for (HtmlElement element : elements) {
//            TreeNode one = new TreeNode();
//            one.setId(element.getId());
//            one.setParentId(element.getMenuId());
//            one.setName(element.getName());
//            //add element to treeNode
//            TreeNode.addNodeToTree(one,treeNodes);
//        }
//        //tree to JSON
//        JSONArray ret = TreeNode.toJSON(treeNodes);
//        return ret.toJSONString();
//    }
//
//    @GetMapping("/elementTree")
//    public String elementTree() {
//        JSONArray ret = new JSONArray();
//
//        List<Menu> menus = menuService.findAll();
//        for (Menu menu : menus) {
//            JSONObject node = new JSONObject();
//            node.put("id", menu.getId());
//            node.put("parentId", menu.getParentId());
//            node.put("name", menu.getName());
//            ret.add(node);
//        }
//        List<HtmlElement> htmlElements = htmlElementService.findAll();
//        for (HtmlElement htmlElement : htmlElements) {
//            JSONObject node = new JSONObject();
//            node.put("id", "element_" + htmlElement.getId());
//            node.put("name", htmlElement.getName());
//            node.put("parentId", htmlElement.getMenuId());
//            ret.add(node);
//        }
//        return ret.toString();
//    }

}
