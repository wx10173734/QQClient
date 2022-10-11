package com.lzc.qqclient.service;

import java.util.HashMap;

/**
 * @title: ManageClientConnectServerThread
 * @Author luozouchen
 * @Date: 2022/10/9 20:14
 * @Version 1.0
 * 该类管理客户端连接到服务器端的线程的类
 */
public class ManageClientConnectServerThread {
    //把多个线程放入到一个HashMap集合中，key 就是用户的id,value就是线程
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    //将某个线程加入到集合
    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId, clientConnectServerThread);
    }

    //通过userId 可以得到对应线程
    public static ClientConnectServerThread getClientConnectThread(String userId) {
        return hm.get(userId);
    }

}
