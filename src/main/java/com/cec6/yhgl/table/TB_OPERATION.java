package com.cec6.yhgl.table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cec6.yhgl.domain.Operation;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@TableName("sys_operation")
@NoArgsConstructor
public class TB_OPERATION {

    @TableId
    private String id;
    @TableField("name")
    private String name;
    @TableField("method")
    private String method;
    @TableField("url")
    private String url;
    @TableField("is_valid")
    private Boolean isValid;
    @TableField("index")
    private Integer index;

    public TB_OPERATION(Operation operation) {
        this.id = operation.getId();
        this.name = operation.getName();
        this.method = operation.getMethod();
        this.url = operation.getUrl();
        this.isValid = operation.getIsValid();
        this.index = operation.getIndex();
    }
}
