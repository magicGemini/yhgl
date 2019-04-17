package com.cec6.yhgl.table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@TableName("sys_role_department_relation")
@NoArgsConstructor
public class TB_ROLE_DEPT implements Serializable {

    @TableId
    private String id;
    @TableField("role_id")
    private String roleId;
    @TableField("department_id")
    private String departmentId;

    public TB_ROLE_DEPT(String roleId, String departmentId) {
        this.roleId = roleId;
        this.departmentId = departmentId;
    }

}
