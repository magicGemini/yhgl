package com.cec6.yhgl.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cec6.yhgl.dao.*;
import com.cec6.yhgl.domain.Operation;
import com.cec6.yhgl.domain.Permission;
import com.cec6.yhgl.table.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private PermissionMenuMapper permissionMenuMapper;
    @Autowired
    private PermissionElementMapper permissionElementMapper;
    @Autowired
    private PermissionOperationMapper permissionOperationMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private OperationMapper operationMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;


    public Permission createPermission(Permission permission) {
        if (permission.getId() == null)
            permission.setId(UUID.randomUUID().toString());
        permissionMapper.insert(new TB_PERMISSION(permission));
        return permission;
    }

    public void deletePermission(String id) {
        permissionMapper.deleteById(id);
    }

    public List<Permission> findAll() {
        List<Permission> ret = new ArrayList<>();
        permissionMapper.selectList(null).stream().forEach((p) -> ret.add(new Permission(p)));
        return ret;
    }

    public void correlationMenu(String permissionId, String menuId) {
        TB_PERMISSION_MENU one = new TB_PERMISSION_MENU(permissionId, menuId);
        one.setId(UUID.randomUUID().toString());
        permissionMenuMapper.insert(one);
    }

    public void correlationHtmlElement(String permissionId, String htmlElementId) {
        TB_PERMISSION_ElEMENT one = new TB_PERMISSION_ElEMENT(permissionId, htmlElementId);
        one.setId(UUID.randomUUID().toString());
        permissionElementMapper.insert(one);
    }

    public void correlationOperation(String permissionid, String operationId) {
        TB_PERMISSION_OPERATION one = new TB_PERMISSION_OPERATION(permissionid, operationId);
        one.setId(UUID.randomUUID().toString());
        permissionOperationMapper.insert(one);
    }

    public String findPermissionContent(Permission permission) {
        String ret = null;
        if ("menu".equals(permission.getType())) {
            List<TB_PERMISSION_MENU> menuList = permissionMenuMapper.selectList(new QueryWrapper<TB_PERMISSION_MENU>().eq("right_id", permission.getId()));
            String[] menus = new String[menuList.size()];
            for (int i = 0; i < menuList.size(); i++) {
                String menuId = menuList.get(i).getMenuId();
                TB_MENU menu = menuMapper.selectById(menuId);
                if (menu == null)
                    continue;
                menus[i] = menu.getName();
            }
            ret = StringUtils.join(menus, ",");
        }
        if ("operation".equals(permission.getType())) {
            List<TB_PERMISSION_OPERATION> opreations = permissionOperationMapper.selectList(new QueryWrapper<TB_PERMISSION_OPERATION>().eq("right_id", permission.getId()));
            String[] ops = new String[opreations.size()];
            for (int i = 0; i < opreations.size(); i++) {
                TB_OPERATION opt = operationMapper.selectById(opreations.get(i).getOperationId());
                if (opt == null)
                    continue;
                ops[i] = opt.getName() + ":" + opt.getUrl();
            }
            ret = StringUtils.join(ops, ",");
        }
        return ret;
    }

    public Boolean correlationRole(String permissionId, String roleId) {
        try {
            TB_ROLE_PERMISSION rp = rolePermissionMapper.selectOne(new QueryWrapper<TB_ROLE_PERMISSION>().eq("role_id", roleId).eq("right_id", permissionId));
            if (rp == null) {
                TB_ROLE_PERMISSION one = new TB_ROLE_PERMISSION(roleId, permissionId);
                one.setId(UUID.randomUUID().toString());
                rolePermissionMapper.insert(one);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean isPermitted(String userId, Operation oper) {
        Boolean flag = false;
        List<Permission> permissions = findByUserId(userId);
        if (permissions == null)
            return false;
        for (Permission p : permissions) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("right_id", p.getId());
            wrapper.eq("OPERATION_ID", oper.getId());
            TB_PERMISSION_OPERATION isExist = permissionOperationMapper.selectOne(wrapper);
            if (isExist != null) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private List<Permission> findByUserId(String userId) {
        List<Permission> ret = new ArrayList<>();

        TB_USER_ROLE ur = userRoleMapper.selectOne(new QueryWrapper<TB_USER_ROLE>().eq("user_id", userId));
        if (ur == null)
            return null;
        List<TB_ROLE_PERMISSION> rpList = rolePermissionMapper.selectList(new QueryWrapper<TB_ROLE_PERMISSION>().eq("role_id", ur.getRole_id()));
        rpList.stream().forEach((rp) -> {
            TB_PERMISSION permission = permissionMapper.selectById(rp.getPermissionId());
            ret.add(new Permission(permission));
        });
        return ret;
    }
}