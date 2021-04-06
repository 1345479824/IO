package com.itheima.udp_test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpSer {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(8888);

        byte[] buff = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buff, 0, buff.length);

        socket.receive(packet);

        System.out.println(new String(packet.getData()));

    }
}
