package com.oleyang.springbootdemo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {

    private int code;
    private String message;
    private Date timestamp;
    private Object data;
}
