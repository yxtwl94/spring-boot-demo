package com.oleyang.springbootdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oleyang.springbootdemo.dao.User;
import org.apache.ibatis.annotations.Mapper;

// 定义数据查询的接口函数
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
