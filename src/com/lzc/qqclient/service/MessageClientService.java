package com.lzc.qqclient.service;

import com.lzc.qqcommon.Message;
import com.lzc.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * @title: MessageClientService
 * @Author luozouchen
 * @Date: 2022/10/12 21:44
 * @Version 1.0
 * 该对象/类 提供和消息相关的服务方法
 */
public class MessageClientService {
    /**
     * @param content  内容
     * @param senderId 发送用户id
     * @param getterId 接受用户id
     */
    public void sendMessageToOne(String content, String senderId, String getterId) {
        //构建message
        Message message = new Message();
        message.setSender(senderId);
        message.setMesType(MessageType.MESSAGE_COMM_MES);//普通的聊天消息
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());//发送时间设置到message对象
        System.out.println(senderId + " 对 " + getterId + " 说 " + content);
        //发送给服务端
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
