package com.cec6.yhgl.controller;

import com.cec6.yhgl.domain.Department;
import com.cec6.yhgl.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    /**
     * 获取部门列表
     *********************/
    @GetMapping("/deptList")
    public List<Department> deptList() {
//        String username = (String) SecurityUtils.getSubject().getPrincipal();
        //TODO: search user permission in database
        List<Department> departments = departmentService.findAll();
        departments.stream().forEach((dept) -> {
            Department department = departmentService.findOne(dept.getParentId());
            if (department != null)
                dept.setParent(department.getName());
        });
        return departments;
    }

    /**
     * 新增组织机构
     *********************/
    @PostMapping
    public Department addDept(@RequestBody Department department) {
        return departmentService.addOne(department);
    }

    /**
     * 删除组织机构
     *********************/
    @DeleteMapping("/{id}")
    public String delDept(@PathVariable String id) {
        int ret = departmentService.delDept(id);
        if (ret > 0)
            return "success";
        return "failed";
    }

//    @GetMapping("/deptTree")
//    public String deptTree() {
//        List<TreeNode> treeNodes = departmentService.getDeptTree();
//        //tree to JSON
//        JSONArray ret = TreeNode.toJSON(treeNodes);
//        return ret.toJSONString();
//    }

}
