package com.cec6.yhgl.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cec6.yhgl.dao.RoleDeptMapper;
import com.cec6.yhgl.dao.RoleMapper;
import com.cec6.yhgl.dao.RolePermissionMapper;
import com.cec6.yhgl.dao.UserRoleMapper;
import com.cec6.yhgl.domain.Role;
import com.cec6.yhgl.table.TB_ROLE;
import com.cec6.yhgl.table.TB_ROLE_DEPT;
import com.cec6.yhgl.table.TB_ROLE_PERMISSION;
import com.cec6.yhgl.table.TB_USER_ROLE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleDeptMapper roleDeptMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    public List<Role> findAll() {
        List<Role> ret = new ArrayList<>();
        roleMapper.selectList(null).stream().forEach((role) -> ret.add(new Role(role)));
        return ret;
    }

    public Role createRole(Role role) {
        if (role.getId() == null)
            role.setId(UUID.randomUUID().toString());
        role.setCreateTime(new Date());
        role.setAvailable(true);
        roleMapper.insert(new TB_ROLE(role));
        return role;
    }

    public Boolean deleteRole(String roleId) {
        try {
            roleDeptMapper.delete(new QueryWrapper<TB_ROLE_DEPT>().eq("role_id", roleId));
            userRoleMapper.delete(new QueryWrapper<TB_USER_ROLE>().eq("role_id", roleId));
            rolePermissionMapper.delete(new QueryWrapper<TB_ROLE_PERMISSION>().eq("role_id", roleId));
            roleMapper.deleteById(roleId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //    public int correlationPermissions(String roleId, String... permissionIds) {
//        return roleDao.correlationPermissions(roleId, permissionIds);
//    }
//
//    public void uncorrelationPermissions(String roleId, String... permissionIds) {
//        roleDao.uncorrelationPermissions(roleId, permissionIds);
//    }

    public void correlationDepartments(String roleId, String... departmentIds) {
        for (String deptId : departmentIds) {
            TB_ROLE_DEPT one = new TB_ROLE_DEPT(roleId, deptId);
            one.setId(UUID.randomUUID().toString());
            roleDeptMapper.insert(one);
        }
    }

    public void correlationUsers(String roleId, String... userIds) {
        for (String userId : userIds) {
            TB_USER_ROLE isExist = userRoleMapper.selectOne(new QueryWrapper<TB_USER_ROLE>().eq("user_id", userId).eq("role_id", roleId));
            if (isExist != null)
                continue;
            TB_USER_ROLE one = new TB_USER_ROLE(userId, roleId);
            one.setId(UUID.randomUUID().toString());
            userRoleMapper.insert(one);
        }
    }

    public List<Role> findByUserID(String userId) {
        List<Role> ret = new ArrayList<>();
        List<TB_USER_ROLE> urList = userRoleMapper.selectList(new QueryWrapper<TB_USER_ROLE>().eq("user_id", userId));
        urList.stream().forEach((ur) -> {
            TB_ROLE role = roleMapper.selectOne(new QueryWrapper<TB_ROLE>().eq("id", ur.getRole_id()));
            ret.add(new Role(role));
        });
        return ret;
    }

    public List<Role> findByPermissionID(String id) {
        List<TB_ROLE_PERMISSION> rpList = rolePermissionMapper.selectList(new QueryWrapper<TB_ROLE_PERMISSION>().eq("right_id", id));
        List<Role> roleList = new ArrayList<>();
        rpList.stream().forEach((rp) -> {
            TB_ROLE role = roleMapper.selectById(rp.getRoleId());
            roleList.add(new Role(role));
        });
        return roleList;
    }
}
