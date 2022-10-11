package com.lzc.qqclient.service;

import com.lzc.qqcommon.Message;
import com.lzc.qqcommon.MessageType;
import com.lzc.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @title: UserClientService
 * @Author luozouchen
 * @Date: 2022/10/9 18:20
 * @Version 1.0
 * 该类完成用户登陆验证和用户注册等功能
 */
public class UserClientService {
    //因为可能在别的地方使用user信息，因此作出成员属性
    private User user = new User();
    //因为socket在其他地方也可能使用，因此做出属性
    private Socket socket;

    //根据userId 和pwd到服务器验证该用户是否合法
    public boolean checkUser(String userId, String pwd) {
        boolean b = false;
        //创建User对象
        user.setUserId(userId);
        user.setPasswd(pwd);

        //连接到服务端，发送user对象
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            //得到objectOutputStream 对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);//发送user对象


            //读取从服务端回复的message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();
            if (ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {//登陆Ok
                //创建一个和服务器端保持通讯的线程->创建一个类 ClientConnectServerThread
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                //启动客户端线程
                clientConnectServerThread.start();
                //为了客户端的扩展，将线程放入到集合中管理
                ManageClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);
                b = true;
            } else {
                //如果登陆失败..,我们就不能启动和服务器通信的线程
                //要关闭socket
                socket.close();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return b;
    }

    //向服务器端请求在线用户列表
    public void onlineFriendList() {
        //发送一个Message,类型    String MESSAGE_GET_ONLINE_FRIEND="4";//要求返回在线用户列表
        Message message = new Message();
        message.setSender(user.getUserId());
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);

        //发送给服务器
        try {

//            //从管理线程的集合中，通过userId 得到线程
//            ClientConnectServerThread clientConnectThread = ManageClientConnectServerThread.getClientConnectThread(user.getUserId());
//            //得到线程的socket
//            Socket socket1 = clientConnectThread.getSocket();
            //应该得到当前线程的socket 对应的objectOutPutStream 对象
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConnectServerThread.getClientConnectThread
                            (user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);//发送一个Message对象，向服务端要在线用户列表

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
