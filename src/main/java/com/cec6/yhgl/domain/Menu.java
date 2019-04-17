package com.cec6.yhgl.domain;

import com.cec6.yhgl.table.TB_MENU;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Menu {

    private String id;
    private String parentId;
    private String name;
    private String url;
    private Boolean isValid;
    private Integer index;

    public Menu(Integer index, String name, String url) {
        this.index = index;
        this.name = name;
        this.url = url;
    }

    public Menu(TB_MENU menu) {
        this.id = menu.getId();
        this.parentId = menu.getParentId();
        this.name = menu.getName();
        this.url = menu.getUrl();
        this.isValid = menu.getIsValid();
        this.index = menu.getIndex();
    }
}
