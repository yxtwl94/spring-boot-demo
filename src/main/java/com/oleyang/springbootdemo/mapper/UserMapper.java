package com.oleyang.springbootdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oleyang.springbootdemo.dao.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 定义数据查询的接口函数
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 多表查询
    @Select("select a.*, b.info from user a join user_info b on a.info_id = b.id where a.id = #{id}")
    List<User> getUserJoinById(@Param("id") Integer id);

    // 可以在xml中配置
    User findOneUser(@Param("id") Integer id);

    Page<User> findPageXml(Page<User> page);
}
