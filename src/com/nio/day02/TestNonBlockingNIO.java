package com.nio.day02;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class TestNonBlockingNIO {

    @Test
    public void client() throws IOException {
        //获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("localhost", 8888));
        //切换成非阻塞模式
        sChannel.configureBlocking(false);
        //分配缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        //发送数据给服务端
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
            String str = scan.next();
            buf.put((new Date().toString() + ":" + str).getBytes());
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //关闭通道
        sChannel.close();
    }

    @Test
    public void server() throws IOException{
        //获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        //切换模式
        ssChannel.configureBlocking(false);
        //绑定连接
        ssChannel.bind(new InetSocketAddress(8888));
        //获取选择器
        Selector selector = Selector.open();
        //将通道注册到选择器上,并且指定选择器监听接收事件
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        //轮询式地获取选择器上已经准备就绪的事件
        while(selector.select() > 0){
            //获取当前选择器中所有注册的“选择键（已就绪的监听事件）”
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while(it.hasNext()){
                //获取准备就绪的事件
                SelectionKey sk = it.next();
                //判断是什么时间准备就绪
                if (sk.isAcceptable()){
                    //若接受就绪，获取客户端连接
                    SocketChannel sChannel = ssChannel.accept();
                    //切换成非阻塞模式
                    sChannel.configureBlocking(false);
                    //将该通道注册到选择器上
                    sChannel.register(selector, SelectionKey.OP_READ);
                }else if (sk.isReadable()){
                    //获取当前选择器读就绪状态的通道
                    SocketChannel sChannel = (SocketChannel)sk.channel();
                    //读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int len = 0;
                    while((len = sChannel.read(buf)) > 0){
                        buf.flip();
                        System.out.println(new String(buf.array(), 0 , len));
                        buf.clear();
                    }
                }
            }
            //取消选择键（SelectionKey）
            it.remove();
        }
    }
}
