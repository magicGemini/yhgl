package com.cec6.yhgl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cec6.yhgl.table.TB_MENU;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<TB_MENU> {

    @Select("SELECT * FROM sys_menu WHERE id in ( " +
            "   SELECT menu_id FROM sys_menu_right_relation WHERE right_id = (" +
            "       SELECT right_id FROM sys_role_right_relation WHERE role_id in (" +
            "           SELECT role_id FROM sys_user_role_relation WHERE user_id = (" +
            "               SELECT id FROM sys_user WHERE user_name = #{username}" +
            "           )" +
            "       )" +
            "   )" +
            ")")
    List<TB_MENU> findMenusByUsername(@Param("username") String username);
}
