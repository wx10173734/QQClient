package com.lzc.qqclient.service;

import com.lzc.qqcommon.Message;
import com.lzc.qqcommon.MessageType;
import com.sun.xml.internal.ws.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * @title: IoService
 * @Author luozouchen
 * @Date: 2022/10/13 0:29
 * @Version 1.0
 * 文件传输服务
 */
public class FileClientService {
    /**
     * @param sender   发送人
     * @param getterId 接收者
     * @param src      源文件
     * @param dest     目标目录
     */
    public void sendFileToOne(String sender, String getterId, String src, String dest) {
        Message message = new Message();
        message.setSender(sender);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);
        message.setMesType(MessageType.MESSAGE_FilE_MES);

        //读取文件
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int) new File(src).length()];
        try {
            //读取src文件
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);//将src文件读入到程序的字节数组
            //将文件对应的字节数组设置到message
            message.setFileBytes(fileBytes);



        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println(sender + "给" + getterId + "发送文件" + src + "到对方目录:" + dest);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
