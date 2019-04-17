package com.cec6.yhgl.domain;

import com.cec6.yhgl.table.TB_DEPT;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Department {

    private String id;
    private String parentId;
    private String parent;
    private String name;
    private String shortName;
    private Boolean isValid;
    private Integer index;

    public Department(TB_DEPT dept) {
        this.id = dept.getId();
        this.parentId = dept.getParentId();
        this.name = dept.getName();
        this.shortName = dept.getName();
        this.isValid = dept.getIsValid();
        this.index = dept.getIndex();
    }
}
