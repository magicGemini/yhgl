package com.cec6.yhgl.domain;

import com.cec6.yhgl.table.TB_PERMISSION;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Permission {

    private String id;
    private String permission;
    private String type;
    private String descript;
    private Boolean available;
    //
    private String permissionContent;
    private String role;

    public Permission(String permission, String type, String descript, Boolean available) {
        this.permission = permission;
        this.type = type;
        this.descript = descript;
        this.available = available;
    }

    public Permission(TB_PERMISSION p) {
        this.id = p.getId();
        this.permission = p.getPermission();
        this.type = p.getType();
        this.descript = p.getDescript();
        this.available = p.getAvailable();
    }
}
