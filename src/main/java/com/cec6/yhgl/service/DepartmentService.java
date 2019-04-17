package com.cec6.yhgl.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cec6.yhgl.dao.DeptMapper;
import com.cec6.yhgl.dao.UserDeptMapper;
import com.cec6.yhgl.domain.Department;
import com.cec6.yhgl.table.TB_DEPT;
import com.cec6.yhgl.table.TB_USER;
import com.cec6.yhgl.table.TB_USER_DEPT;
import com.sun.org.apache.bcel.internal.generic.LSTORE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private UserDeptMapper userDeptMapper;

    public List<Department> findAll() {
        List<Department> ret = new ArrayList<>();
        deptMapper.selectList(null).stream().forEach((dept) -> {
            ret.add(new Department(dept));
        });
        return ret;
    }

    public Department addOne(Department department) {
        List<TB_DEPT> depts = deptMapper.selectList(new QueryWrapper<TB_DEPT>().orderByDesc("index"));
        Integer maxIndex = 1;
        if (depts == null)
            maxIndex = depts.get(0).getIndex();
        if (department.getId() == null)
            department.setId(UUID.randomUUID().toString());
        department.setIndex(maxIndex + 1);
        deptMapper.insert(new TB_DEPT(department));
        return department;
    }

    public int delDept(String id) {
        List<TB_DEPT> children = deptMapper.selectList(new QueryWrapper<TB_DEPT>().eq("parent_id", id));
        for (TB_DEPT dept : children) {
            uncorrelationUsers(dept.getId());
            deptMapper.deleteById(dept.getId());
        }
        uncorrelationUsers(id);
        deptMapper.deleteById(id);
        return children.size() + 1;
    }

    private boolean uncorrelationUsers(String deptID) {
        try {
            userDeptMapper.delete(new QueryWrapper<TB_USER_DEPT>().eq("DEPARTMENT_ID", deptID));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Department findByUserID(String userID) {
        TB_USER_DEPT user_dept = userDeptMapper.selectOne(new QueryWrapper<TB_USER_DEPT>().eq("user_id", userID));
        if (user_dept == null)
            return null;
        Department department = new Department(deptMapper.selectById(user_dept.getDepartmentId()));
        return department;
    }

    public Department findOne(String id) {
        TB_DEPT dept = deptMapper.selectById(id);
        return dept == null ? null : new Department(dept);
    }

//    public List<TreeNode> getDeptTree() {
//        List<Department> deptList = departmentDao.findAll();
//        //list to tree
//        List<TreeNode> treeNodes = new ArrayList<>();
//        for (Department dept : deptList) {
//            if (dept.getParentId() == null || dept.getParentId().trim().isEmpty()) {
//                TreeNode node = new TreeNode();
//                node.setId(dept.getId());
//                node.setParentId(dept.getParentId());
//                node.setName(dept.getName());
//                List<TreeNode> children = findChildren(dept, deptList);
//                node.setChildren(children.size() == 0 ? null : children);
//                treeNodes.add(node);
//            }
//        }
//        return treeNodes;
//    }
//
//    private List<TreeNode> findChildren(Department parentDept, List<Department> deptList) {
//        List<TreeNode> children = new ArrayList<>();
//        for (Department dept : deptList) {
//            if (dept.getParentId() == null || dept.getParentId().trim().isEmpty())
//                continue;
//            if (dept.getParentId().equals(parentDept.getId())) {
//                TreeNode child = new TreeNode();
//                child.setName(dept.getName());
//                child.setId(dept.getId());
//                child.setParentId(dept.getParentId());
//                List<TreeNode> grandChild = findChildren(dept, deptList);
//                child.setChildren(grandChild.size() == 0 ? null : grandChild);
//                children.add(child);
//            }
//        }
//        return children;
//    }

}
