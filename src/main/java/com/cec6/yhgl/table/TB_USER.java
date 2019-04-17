package com.cec6.yhgl.table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cec6.yhgl.domain.User;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@TableName("SYS_USER")
@NoArgsConstructor
public class TB_USER implements Serializable {

    @TableId
    private String id;
    @TableField("user_name")
    private String username;
    @TableField("user_password")
    private String password;
    @TableField("user_ch_name")
    private String chName;
    @TableField("telephone")
    private String telephone;
    @TableField("descript")
    private String descript;
    @TableField("create_time")
    private Date createTime;
    @TableField("is_valid")
    private Boolean locked;
    @TableField("app_update")
    private Boolean appUpdate;

    public TB_USER(User user) {
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


