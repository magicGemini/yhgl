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
@TableName("sys_role_right_relation")
@NoArgsConstructor
public class TB_ROLE_PERMISSION implements Serializable {

    @TableId
    private String id;
    @TableField("role_id")
    private String roleId;
    @TableField("right_id")
    private String permissionId;

    public TB_ROLE_PERMISSION(String roleId, String permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
}
