package com.cec6.yhgl.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cec6.yhgl.domain.Department;
import com.cec6.yhgl.domain.Role;
import com.cec6.yhgl.domain.User;
import com.cec6.yhgl.service.DepartmentService;
import com.cec6.yhgl.service.RoleService;
import com.cec6.yhgl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RoleService roleService;

    /**
     * 获取用户列表
     ***************/
    @GetMapping("/userList")
    public List<User> userList() {
        List<User> ret = userService.findAll();
        ret.stream().forEach((user) -> {
            Department department = departmentService.findByUserID(user.getId());
            if (department != null)
                user.setDeptName(department.getName());
        });
        ret.stream().forEach((user) -> {
            List<Role> roles = roleService.findByUserID(user.getId());
            for (int i = 0; i < roles.size(); i++) {
                if (i == 0)
                    user.setRoles(roles.get(i).getRole());
                else
                    user.setRoles(user.getRoles() + "," + roles.get(i).getRole());
            }
        });

        ret.sort((a, b) -> (int) (a.getCreateTime().getTime() - b.getCreateTime().getTime()));
        return ret;
    }

    /**
     * 新增用户
     *************/
    @PostMapping
    public String addUser(@RequestBody Map<String, Object> map) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String chName = (String) map.get("chName");
        String telephone = (String) map.get("telephone");
        String descript = (String) map.get("descript");
        String departmentId = (String) map.get("departmentId");

        User newOne = new User(username, password);
        newOne.setChName(chName);
        newOne.setTelephone(telephone);
        newOne.setDescript(descript);

        userService.createUser(newOne);
        userService.correlationDept(newOne.getId(), departmentId);
        return "success";
    }

    /**
     * 删除用户
     ***********/
    @DeleteMapping("/{ids}")
    public String deleteUser(@PathVariable List<String> ids) {
        for (String id : ids)
            userService.deleteUser(id);
        return "success";
    }

    @GetMapping("/getUserId")
    public User getUserId(@RequestParam("username") String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/userTree")
    public String userTree() {
        JSONArray ret = new JSONArray();
        List<Department> deptList = departmentService.findAll();
        for (Department dept : deptList) {
            JSONObject node = new JSONObject();
            node.put("id", dept.getId());
            node.put("parentId", dept.getParentId());
            node.put("name", dept.getName());
            node.put("open", "true");
            ret.add(node);
        }
        List<User> userList = userService.findAll();
        for (User user : userList) {
            Department dept = departmentService.findByUserID(user.getId());
            JSONObject node = new JSONObject();
            node.put("id", user.getId());
            node.put("parentId", dept == null ? "" : dept.getId());
            node.put("name", user.getUsername());
            node.put("open", "true");
            ret.add(node);
        }
        return ret.toString();
    }

    /**
     * 修改用户是否有备件修改权限
     *******************************/
    @PostMapping("/appUpdate")
    public String appUpdate(@RequestParam("userID") String userID, @RequestParam("appUpdate") Boolean appUpdate) {
        Boolean ret = userService.setAppUpdate(userID, appUpdate);
        return ret ? "success" : "failed";
    }

    /**
     * 获取用户是否有备件修改权限
     ****/
    @GetMapping("/appUpdate")
    public Boolean appUpdate(@RequestParam("userID") String userID) {
        Boolean ret = userService.getAppUpdate(userID);
        return (ret != null && ret) ? true : false;
    }


}