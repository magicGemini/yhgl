package com.cec6.yhgl.table;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@TableName("sys_user_role_relation")
public class TB_USER_ROLE {

    @TableId
    private String id;
    @TableField("user_id")
    private String user_id;
    @TableField("role_id")
    private String role_id;

    public TB_USER_ROLE(String user_id, String role_id) {
        this.user_id = user_id;
        this.role_id = role_id;
    }
}
