package com.cec6.yhgl.table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cec6.yhgl.domain.HtmlElement;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@TableName("sys_html_element")
@NoArgsConstructor
public class TB_ELEMENT {

    @TableId
    private String id;
    @TableField("name")
    private String name;
    @TableField("code")
    private String code;
    @TableField("menu_id")
    private String menuId;
    @TableField("is_valid")
    private Boolean isValid;


    public TB_ELEMENT(HtmlElement htmlElement) {
        this.id = htmlElement.getId();
        this.name = htmlElement.getName();
        this.code = htmlElement.getCode();
        this.menuId = htmlElement.getMenuId();
        this.isValid = htmlElement.getIsValid();
    }
}
