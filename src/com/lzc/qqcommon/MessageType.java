package com.lzc.qqcommon;

/**
 * @title: MessageType
 * @Author luozouchen
 * @Date: 2022/10/9 17:19
 * @Version 1.0
 * 表示消息类型
 */
public interface MessageType {
    //1.在接口中定义了一些常量
    String MESSAGE_LOGIN_SUCCEED = "1";//表示登陆成功
    String MESSAGE_LOGIN_FAIL = "2";//表示登陆失败
    String MESSAGE_COMM_MES = "3";//普通信息包
    String MESSAGE_GET_ONLINE_FRIEND="4";//要求返回在线用户列表
    String MESSAGE_RET_ONLINE_FRIEND="5";//返回在线用户列表
    String MESSAGE_CLIENT_EXIT="6";//客户端请求退出
    String MESSAGE_TOALL_MES="7";//群发信息包



}
