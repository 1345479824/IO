package com.itheima.lesson03;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpServerDemo01 {
    public static void main(String[] args) throws Exception {
        //开放端口
        DatagramSocket socket = new DatagramSocket(9090);
        //接收数据包
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
        //阻塞接受
        socket.receive(packet);
        System.out.println(new String(packet.getData()));
        System.out.println(packet.getAddress().getHostAddress());
        //关闭连接
        socket.close();
    }
}
