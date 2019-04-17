package com.cec6.yhgl.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cec6.yhgl.domain.Role;
import com.cec6.yhgl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/roleList")
    public List<Role> roleList() {
        List<Role> ret = roleService.findAll();
        ret.sort((a, b) -> (int) (a.getCreateTime().getTime() - b.getCreateTime().getTime()));
        return ret;
    }

    @PostMapping
    public String createRole(@RequestBody Map<String, Object> map) {
        String role = (String) map.get("role");
        String descript = (String) map.get("descript");
        Role newRole = new Role(role, descript, Boolean.TRUE);

        Role ret = roleService.createRole(newRole);
        if (ret != null)
            return "success";
        return "failed";
    }

    @PutMapping("/correlationUser")
    public String correlationUser(@RequestBody Map<String, Object> map) {
        String roleId = (String) map.get("roleId");
        String userId = (String) map.get("userId");
        roleService.correlationUsers(roleId, userId);
        return "success";
    }

    @PutMapping("/correlationDept")
    public String correlationDept(@RequestBody Map<String, Object> map) {
        String roleId = (String) map.get("roleId");
        String deptId = (String) map.get("deptId");
        roleService.correlationDepartments(roleId, deptId);
        return "success";
    }

    @DeleteMapping("/{ids}")
    public String delRole(@PathVariable List<String> ids) {
        for (String id : new HashSet<>(ids)) {
            roleService.deleteRole(id);
        }
        return "success";
    }



    @GetMapping("/roleTree")
    public String roleTree() {
        List<Role> roleList = roleService.findAll();
        roleList.sort((a, b) -> (int) (a.getCreateTime().getTime() - b.getCreateTime().getTime()));

        JSONArray ret = new JSONArray();
        roleList.stream().forEach((role) -> {
            JSONObject one = new JSONObject();
            one.put("id", role.getId());
            one.put("name", role.getRole());
            ret.add(one);
        });
        return ret.toString();
    }

}
