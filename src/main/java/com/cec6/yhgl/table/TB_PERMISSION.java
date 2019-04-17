package com.cec6.yhgl.table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cec6.yhgl.domain.Permission;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@TableName("sys_right")
public class TB_PERMISSION {

    @TableId
    private String id;
    @TableField("right_name")
    private String permission;
    @TableField("type")
    private String type;
    @TableField("description")
    private String descript;
    @TableField("is_valid")
    private Boolean available;

    public TB_PERMISSION(Permission permission) {
        this.id = permission.getId();
        this.permission = permission.getPermission();
        this.type = permission.getType();
        this.descript = permission.getDescript();
        this.available = permission.getAvailable();
    }
}
