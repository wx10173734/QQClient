package com.lzc.qqclient.view;

import com.lzc.qqclient.service.MessageClientService;
import com.lzc.qqclient.service.UserClientService;
import com.lzc.qqclient.utils.Utility;


/**
 * @title: QQView
 * @Author luozouchen
 * @Date: 2022/10/9 17:52
 * @Version 1.0
 * 客户端的菜单界面
 */
public class QQView {
    private boolean loop = true;//控制是否显示菜单
    private String key = "";//接口用户的键盘输入
    private UserClientService userClientService = new UserClientService();//对象是用于登陆服务/注册用户
    private MessageClientService messageClientService = new MessageClientService();//对象用户私聊/群聊


    public static void main(String[] args) {
        new QQView().mainMenu();
        System.out.println("客户端退出系统....");
    }


    //显示主菜单
    private void mainMenu() {
        while (loop) {
            System.out.println("==============欢迎登陆网络通信系统==============");
            System.out.println("\t\t 1 登陆系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择:");

            key = Utility.readString(1);
            //根据用户的输入，来处理不同的逻辑
            switch (key) {
                case "1":
                    System.out.print("请输入用户号:");
                    String userId = Utility.readString(50);
                    System.out.print("请输入密  码:");
                    String pwd = Utility.readString(50);
                    //这里就比较麻烦了，需要到服务端去验证该用户是否合法
                    //这里有很多代码 这里编写一个类UserClientService[用户登陆/用户注册]
                    if (userClientService.checkUser(userId, pwd)) {//还没有写完，先把整个逻辑打通...
                        System.out.println("==============欢迎 (用户 " + userId + " 登录成功)==============");
                        //进入二级菜单
                        while (loop) {
                            System.out.println("\n==============网络通信系统二级菜单(用户)" + userId + ")==============");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择:");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    //写一个方法来获取在线用户列表
                                    userClientService.onlineFriendList();
                                    //System.out.println("显示在线用户列表");
                                    break;
                                case "2":
                                    System.out.println("请输入想对大家说的话:");
                                    String s = Utility.readString(100);
                                    //调用一个方法，将消息封装成message对象，发送给服务端
                                    messageClientService.sendMessageToAll(s, userId);
                                    break;
                                case "3":
                                    System.out.println("请输入想聊天的用户号(在线):");
                                    String getterId = Utility.readString(50);
                                    System.out.println("请输入想说的话:");
                                    String content = Utility.readString(100);
                                    //编写一个方法，将消息发送给服务端
                                    messageClientService.sendMessageToOne(content, userId, getterId);
                                    System.out.println("私聊消息");
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    //调用一个方法，给服务器发送一个退出系统的message
                                    userClientService.logout();
                                    loop = false;
                                    break;
                            }
                        }
                    } else {//登陆服务器失败
                        System.out.println("==========登陆失败==========");
                    }


                    break;
                case "9":
                    loop = false;
                    break;
            }
        }
    }
}
