package com.oleyang.springbootdemo.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@TableName("user")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)  // redis序列化
public class User {

    @TableId
    public Long id;
    public String username;
    public String password;
    public String nickname;
}
