package com.cec6.yhgl.domain;

import com.cec6.yhgl.table.TB_USER;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class User {

    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private String chName;
    private String telephone;
    private String descript;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Boolean locked;
    private Boolean appUpdate;
    //
    private String deptName;
    private String roles;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(TB_USER user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.chName = user.getChName();
        this.telephone = user.getTelephone();
        this.descript = user.getDescript();
        this.createTime = user.getCreateTime();
        this.locked = user.getLocked();
        this.appUpdate = user.getAppUpdate();
    }
}


