package com.cec6.yhgl.table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cec6.yhgl.domain.Department;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@ToString
@TableName("sys_department")
public class TB_DEPT {

    @TableId
    private String id;
    @TableField("parent_id")
    private String parentId;
    @TableField("name")
    private String name;
    @TableField("short_name")
    private String shortName;
    @TableField("is_valid")
    private Boolean isValid;
    @TableField("index")
    private Integer index;

    public TB_DEPT(Department department) {
        this.id = department.getId();
        this.parentId = department.getParentId();
        this.name = department.getName();
        this.shortName = department.getShortName();
        this.isValid = department.getIsValid();
        this.index = department.getIndex();
    }
}
