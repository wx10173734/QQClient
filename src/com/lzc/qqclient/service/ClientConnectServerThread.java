package com.lzc.qqclient.service;

import com.lzc.qqcommon.Message;
import com.lzc.qqcommon.MessageType;

import java.io.FileOutputStream;
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
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {//普通的聊天消息
                    //把服务器转发的消息，显示到控制台即可
                    System.out.println("\n" + message.getSender() + " 对 " + message.getGetter() + " 说 " + message.getContent() + "  时间 " + message.getSendTime());
                } else if (message.getMesType().equals(MessageType.MESSAGE_TOALL_MES)) {//同时也可以是服务器群发消息
                    //显示在客户端的控制台
                    System.out.println("\n" + message.getSender() + " 对所有人说" + message.getContent() + "时间 " + message.getSendTime());
                } else if (message.getMesType().equals(MessageType.MESSAGE_FilE_MES)) {//如果是文件消息
                    System.out.println("\n" + message.getSender() +"给"+message.getGetter() +"发送文件:" + message.getSrc() +"到我的电脑目录:"+message.getDest());
                    //取出message字节数组，通过文件输出流写出磁盘
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("保存文件成功");
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
