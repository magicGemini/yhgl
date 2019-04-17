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
@TableName("sys_menu_right_relation")
@NoArgsConstructor
public class TB_PERMISSION_MENU implements Serializable {

    @TableId
    private String id;
    @TableField("right_id")
    private String permissionId;
    @TableField("menu_id")
    private String menuId;

    public TB_PERMISSION_MENU(String permissionId, String menuId) {
        this.permissionId = permissionId;
        this.menuId = menuId;
    }

}
