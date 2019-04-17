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
@TableName("sys_operation_right_relation")
@NoArgsConstructor
public class TB_PERMISSION_OPERATION implements Serializable {

    @TableId
    private String id;
    @TableField("right_id")
    private String permissionId;
    @TableField("operation_id")
    private String operationId;

    public TB_PERMISSION_OPERATION(String permissionId, String operationId) {
        this.permissionId = permissionId;
        this.operationId = operationId;
    }

}
