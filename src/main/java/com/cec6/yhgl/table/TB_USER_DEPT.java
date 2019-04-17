package com.cec6.yhgl.table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@TableName("sys_user_department_relation")
@NoArgsConstructor
public class TB_USER_DEPT {

    @TableId
    private String id;
    @TableField("user_id")
    private String userId;
    @TableField("department_id")
    private String departmentId;

    public TB_USER_DEPT(String userId, String departmentId) {
        this.userId = userId;
        this.departmentId = departmentId;
    }

}
