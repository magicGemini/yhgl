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
@TableName("sys_element_right_relation")
@NoArgsConstructor
public class TB_PERMISSION_ElEMENT implements Serializable {

    @TableId
    private String id;
    @TableField("right_id")
    private String permissionId;
    @TableField("element_id")
    private String htmlElementId;

    public TB_PERMISSION_ElEMENT(String permissionId, String htmlElementId) {
        this.permissionId = permissionId;
        this.htmlElementId = htmlElementId;
    }

}