package com.itheima.tcp_test;


import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class TcpSer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream is = null;

        try{
            serverSocket = new ServerSocket(8888);
            socket = serverSocket.accept();
            is = socket.getInputStream();
            int len = 0;
            byte[] buff = new byte[1024];
            while((len = is.read(buff)) != -1){
                System.out.println(new String(buff));
            }
        }catch (IOException e){

        }finally {
            try {
                is.close();
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
