package com.lzc.qqcommon;

import java.io.Serializable;

/**
 * @title: User
 * @Author luozouchen
 * @Date: 2022/10/9 17:20
 * @Version 1.0
 * 表示一个/用户/客户 信息
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;//用户id/用户名
    private String passwd;//用户密码

    public User() {
    }

    public User(String userId, String passwd) {
        this.userId = userId;
        this.passwd = passwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
