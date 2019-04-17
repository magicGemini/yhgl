package com.cec6.yhgl.table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cec6.yhgl.domain.Menu;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@TableName("sys_menu")
@NoArgsConstructor
public class TB_MENU {

    @TableId
    private String id;
    @TableField("parent_id")
    private String parentId;
    @TableField("name")
    private String name;
    @TableField("url")
    private String url;
    @TableField("is_valid")
    private Boolean isValid;
    @TableField("index")
    private Integer index;

    public TB_MENU(Menu menu) {
        this.id = menu.getId();
        this.parentId = menu.getParentId();
        this.name = menu.getName();
        this.url = menu.getUrl();
        this.isValid = menu.getIsValid();
        this.index = menu.getIndex();
    }
}
