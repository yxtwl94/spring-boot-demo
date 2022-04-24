package com.oleyang.springbootdemo.dao;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("user")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)  // redis序列化
public class User {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String role;

    private Long infoId;
    // 该信息是其他表的信息
    @TableField(exist = false)
    private String info;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtUpdate;
}
