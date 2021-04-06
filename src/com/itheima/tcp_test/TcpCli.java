package com.itheima.tcp_test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpCli {
    public static void main(String[] args) {
        Socket socket = null;
        OutputStream os = null;
        try{
            socket = new Socket("localhost", 8888);
            os = socket.getOutputStream();
            os.write("你好".getBytes());
        }catch (IOException e){

        }finally {
            try {
                os.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
