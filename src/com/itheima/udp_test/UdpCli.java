package com.itheima.udp_test;

import java.io.IOException;
import java.net.*;

public class UdpCli {
    public static void main(String[] args) throws IOException {

        DatagramSocket socket = new DatagramSocket();

        byte[] buff = "你好".getBytes();
        InetAddress address = InetAddress.getByName("localhost");
        int port = 8888;
        DatagramPacket packet = new DatagramPacket(buff, 0, buff.length, address, port);

        socket.send(packet);

        socket.close();

    }
}
