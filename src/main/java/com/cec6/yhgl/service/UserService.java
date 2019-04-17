package com.cec6.yhgl.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cec6.yhgl.dao.RoleMapper;
import com.cec6.yhgl.dao.UserDeptMapper;
import com.cec6.yhgl.dao.UserMapper;
import com.cec6.yhgl.dao.UserRoleMapper;
import com.cec6.yhgl.domain.Role;
import com.cec6.yhgl.domain.User;
import com.cec6.yhgl.table.TB_ROLE;
import com.cec6.yhgl.table.TB_USER;
import com.cec6.yhgl.table.TB_USER_DEPT;
import com.cec6.yhgl.table.TB_USER_ROLE;
import com.cec6.yhgl.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDeptMapper userDeptMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    public List<User> findAll() {
        List<User> ret = new ArrayList<>();
        userMapper.selectList(null).stream().forEach((user) -> ret.add(new User(user)));
        return ret;
    }

    public User createUser(User user) {
        if (user.getId() == null)
            user.setId(UUID.randomUUID().toString());
        //加密密码
        user.setPassword(MD5Util.encode(user.getPassword()));
        user.setLocked(false);
        user.setCreateTime(new Date());

        userMapper.insert(new TB_USER(user));
        return user;
    }

    public Boolean deleteUser(String userId) {
        try {
            List<TB_USER_DEPT> udlist = userDeptMapper.selectList(new QueryWrapper<TB_USER_DEPT>().eq("user_id", userId));
            udlist.stream().forEach((ud) -> userDeptMapper.deleteById(ud.getId()));
            List<TB_USER_ROLE> urlist = userRoleMapper.selectList(new QueryWrapper<TB_USER_ROLE>().eq("user_id", userId));
            urlist.stream().forEach((ur) -> userRoleMapper.deleteById(ur.getId()));
            userMapper.deleteById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void changePassword(String userId, String newPassword) {
        //修改密码
        TB_USER user = userMapper.selectById(userId);
        user.setPassword(MD5Util.encode(newPassword));
        userMapper.updateById(user);
    }

    public Boolean setAppUpdate(String userID, Boolean appUpdate) {
        TB_USER user = userMapper.selectById(userID);
        user.setAppUpdate(appUpdate);
        try {
            userMapper.updateById(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean getAppUpdate(String userID) {
        return userMapper.selectById(userID).getAppUpdate();
    }

    //    public void correlationRoles(String userId, String... roleIds) {
//        userDao.correlationRoles(userId, roleIds);
//    }
//
//    public void uncorrelationRoles(String userId, String... roleIds) {
//        userDao.uncorrelationRoles(userId, roleIds);
//    }
//

    public User findByUsername(String username) {
        TB_USER user = userMapper.selectOne(new QueryWrapper<TB_USER>().eq("user_name", username));
        if (user != null)
            return new User(user);
        return null;
    }

    public List<Role> findRoles(String userId) {
        List<Role> ret = new ArrayList<>();
        List<TB_USER_ROLE> roleList = userRoleMapper.selectList(new QueryWrapper<TB_USER_ROLE>().eq("user_id", userId));
        roleList.stream().forEach((role) -> {
            TB_ROLE one = roleMapper.selectById(role.getRole_id());
            if (one != null)
                ret.add(new Role(one));
        });

        return ret;
    }

//    public Set<String> findPermissions(String username) {
//        return userDao.findPermissions(username);
//    }

    public void correlationDept(String userId, String deptId) {
        TB_USER_DEPT one = new TB_USER_DEPT(userId, deptId);
        one.setId(UUID.randomUUID().toString());
        userDeptMapper.insert(one);
    }

//    public Department getDeptByUserId(String userId) {
//        return userDao.getDeptByUserId(userId);
//    }

}
