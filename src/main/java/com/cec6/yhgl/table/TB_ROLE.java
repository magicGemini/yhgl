package com.cec6.yhgl.table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cec6.yhgl.domain.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@TableName("sys_role")
@NoArgsConstructor
public class TB_ROLE implements Serializable {

    @TableId
    private String id;
    @TableField("parent_id")
    private String parentId;
    @TableField("role_name")
    private String role;
    @TableField("description")
    private String descript;
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @TableField("is_valid")
    private Boolean available;

    public TB_ROLE(Role role) {
        this.id = role.getId();
        this.parentId = role.getParentId();
        this.role = role.getRole();
        this.descript = role.getDescript();
        this.createTime = role.getCreateTime();
        this.available = role.getAvailable();
    }
}
