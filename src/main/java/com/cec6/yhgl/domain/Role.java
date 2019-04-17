package com.cec6.yhgl.domain;

import com.cec6.yhgl.table.TB_ROLE;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Role {

    private String id;
    private String parentId;
    private String role;
    private String descript;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Boolean available;

    public Role(String role, String descript, Boolean available) {
        this.role = role;
        this.descript = descript;
        this.available = available;
    }

    public Role(TB_ROLE role) {
        this.id = role.getId();
        this.parentId = role.getParentId();
        this.role = role.getRole();
        this.descript = role.getDescript();
        this.createTime = role.getCreateTime();
        this.available = role.getAvailable();
    }
}
