package com.itheima.lesson03;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpClientDemo01 {
    public static void main(String[] args) throws Exception {
        //建立socket
        DatagramSocket socket = new DatagramSocket();
        //建个包
        String msg = "你好，服务器";
        InetAddress localhost = InetAddress.getByName("localhost");
        int port = 9090;
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), 0, msg.getBytes().length, localhost, port);
        //发送包
        socket.send(packet);
        socket.close();
    }
}
