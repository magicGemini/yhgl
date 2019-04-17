package com.cec6.yhgl.domain;

import com.cec6.yhgl.table.TB_ELEMENT;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class HtmlElement {

    private String id;
    private String name;
    private String code;
    private String menuId;
    private Boolean isValid;

    public HtmlElement(TB_ELEMENT element) {
        this.id = element.getId();
        this.name = element.getName();
        this.code = element.getCode();
        this.menuId = element.getMenuId();
        this.isValid = element.getIsValid();
    }
}
