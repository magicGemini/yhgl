package com.cec6.yhgl.controller;

import com.cec6.yhgl.domain.Operation;
import com.cec6.yhgl.domain.Permission;
import com.cec6.yhgl.domain.Role;
import com.cec6.yhgl.domain.User;
import com.cec6.yhgl.service.OperationService;
import com.cec6.yhgl.service.PermissionService;
import com.cec6.yhgl.service.RoleService;
import com.cec6.yhgl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private OperationService operationService;

    @GetMapping("/permissionList")
    public List<Permission> permissionList() {
        List<Permission> ret = permissionService.findAll();
        ret.stream().forEach((p) -> {
            String content = permissionService.findPermissionContent(p);
            p.setPermissionContent(content);
            List<Role> roles = roleService.findByPermissionID(p.getId());
            String roleStr = "";
            for (int i = 0; i < roles.size(); i++) {
                if (i == 0)
                    roleStr = roles.get(i).getRole();
                else
                    roleStr += "," + roles.get(i).getRole();
            }
            p.setRole(roleStr);
        });


        return ret;
    }

    @PostMapping
    public String createPermission(@RequestBody Map<String, Object> map) {
        String permission = (String) map.get("permission");
        String descript = (String) map.get("descript");
        String permissionType = (String) map.get("permissionType");
        Permission newOne = new Permission(permission, permissionType, descript, Boolean.TRUE);
        permissionService.createPermission(newOne);

        String permissionContent = (String) map.get("permissionContent");
        if ("menu".equals(permissionType)) {
            for (String menuId : permissionContent.split(",")) {
                permissionService.correlationMenu(newOne.getId(), menuId);
            }
        }
        if ("htmlElement".equals(permissionType)) {
            for (String htmlElementId : permissionContent.split(",")) {
                permissionService.correlationHtmlElement(newOne.getId(), htmlElementId.replace("element_", ""));
            }
        }
        if ("operation".equals(permissionType)) {
            for (String operationId : permissionContent.split(",")) {
                permissionService.correlationOperation(newOne.getId(), operationId);
            }
        }

        return "success";
    }

    @DeleteMapping("/{ids}")
    public String deletePermission(@PathVariable List<String> ids) {
        for (String id : new HashSet<>(ids)) {
            permissionService.deletePermission(id);
        }
        return "success";
    }

    @PostMapping("/correlateRole")
    public String correlateRole(@RequestBody Map<String, String> map) {
        String roleId = map.get("roleId");
        String permissionId = map.get("permissionId");
        Boolean flag = permissionService.correlationRole(permissionId, roleId);
        return flag ? "success" : "failed";
    }

    @GetMapping("/isPermitted")
    public String isPermitted(
            @RequestParam("url") String url,
            @RequestParam("operation") String operation) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Operation oper = operationService.findByUrlAndMethod(url, operation);
        Boolean isPermitted = permissionService.isPermitted(user.getId(), oper);

        return isPermitted ? "success" : "failed";
    }
}