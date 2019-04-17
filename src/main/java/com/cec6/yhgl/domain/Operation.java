package com.cec6.yhgl.domain;

import com.cec6.yhgl.table.TB_OPERATION;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Operation implements Serializable {

    private String id;
    private String name;
    private String method;
    private String url;
    private Boolean isValid;
    private Integer index;

    public Operation(TB_OPERATION oper) {
        this.id = oper.getId();
        this.name = oper.getName();
        this.method = oper.getMethod();
        this.url = oper.getUrl();
        this.isValid = oper.getIsValid();
        this.index = oper.getIndex();
    }
}
