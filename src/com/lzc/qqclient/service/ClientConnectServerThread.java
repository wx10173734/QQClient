package com.lzc.qqclient.service;

import com.lzc.qqcommon.Message;
import com.lzc.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @title: ClientConnectServerThread
 * @Author luozouchen
 * @Date: 2022/10/9 18:31
 * @Version 1.0
 */
public class ClientConnectServerThread extends Thread {
    //该线程需要持有socket
    private Socket socket;

    //构造器可以接受一个socket对象
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    //
    @Override
    public void run() {
        //因为Thread需要在后台和服务器通信，我们用一个while循环
        while (true) {
            try {
                System.out.println("客户端线程，等待读取从服务器端发送的消息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //如果服务器没有发送message对象，线程会阻塞在这里
                Message message = (Message) ois.readObject();
                //注意后面需要去使用 message
                //判断message类型，然后做相应的业务处理
                //如果读取到的是，服务端返回的在线用户列表
                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    //取出在线列表信息，并显示
                    //规定:
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n=============当前在线用户列表");
                    for (String onlineUser : onlineUsers) {
                        System.out.println("用户:" + onlineUser);
                    }
                } else {
                    System.out.println("是其他类型的message，暂时不处理");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    //为了更方便的得到一个socket
    public Socket getSocket() {
        return socket;
    }
}
