package com.itheima.lesson02;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServerDemo01 {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream is = null;
        try {
            serverSocket = new ServerSocket(9999);
            socket = serverSocket.accept();
            is = socket.getInputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while((len = is.read(b)) != -1){
                System.out.println(new String(b));
            }
            //System.out.println(b.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
