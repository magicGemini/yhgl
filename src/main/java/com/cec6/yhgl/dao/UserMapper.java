package com.cec6.yhgl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cec6.yhgl.table.TB_USER;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<TB_USER> {

}
