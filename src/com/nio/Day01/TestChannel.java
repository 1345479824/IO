package com.nio.Day01;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.SortedMap;

/**
 * 一、通道（channel）：用于源节点与目标节点的链接。
 *
 * 二、通道的主要实现类
 * java.nio.channels.Channel 接口：
 *  |--FileChannel
 *  |--SocketChannel
 *  |--ServerSocketChannel
 *  |--DatagramChannel
 *
 *  三、获取通道
 *  1.java针对支持通道的类提供了getChannel()方法
 *      本地IO:
 *      FileInputStream/FileOutPutStram
 *      RandomAccessFile
 *      网络IO:
 *      Socket
 *      ServerSocket
 *      DatagramSocket
 *  2.在jdk1.7中的NIO.2针对各个通道提供了静态方法open()
 *  3、在jdk1.7中的NIO.2的Files工具类的newByteChannel()
 *
 *  四、通道之间的数据传输
 *  transferFrom()  transferTo()
 *
 *  五、分散(Scatter)与聚集(Gather)
 *  分散读取(Scattering Reads):将通道中的数据分散到多个缓冲区中
 *  聚集写入(Gathering Writes):将多个缓冲区中的数据聚集到通道中
 *
 *  六、字符集
 *  编码：字符串->字节数组
 *
 *  解码：字节数组->字符串
 */
public class TestChannel {
    //1.利用通道完成文件的复制（非直接缓冲区）
    @Test
    public void test1(){
        //自动生成try catch代码块：①选择代码块 ②ctrl+alt+t
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("1.txt");
            fos = new FileOutputStream("2.txt");

            //获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //分配指定大小的缓冲区
            ByteBuffer buff = ByteBuffer.allocate(1024);
            while(inChannel.read(buff) != -1){
                buff.flip();
                outChannel.write(buff);
                buff.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //使用直接缓冲区完成文件的复制（通过内存映射文件）
    @Test
    public void test2(){
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("3.txt"), StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("4.txt"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
            //内存映射文件
            MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
            //直接对缓冲区进行读写操作
            byte[] buf = new byte[inMappedBuf.limit()];
            inMappedBuf.get(buf);
            System.out.println(new String(buf));
            outMappedBuf.put(buf);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //直接缓冲区方式。通过transferTo，transferFrom
    @Test
    public void test3() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("5.txt"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("6.txt"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);


        inChannel.transferTo(0, inChannel.size(), outChannel);

        outChannel.close();
        inChannel.close();
    }

    //分散和聚集
    @Test
    public void test4() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("1.txt", "rw");
        //获取通道
        FileChannel channel1 = raf.getChannel();

        //分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel1.read(bufs);
        bufs[0].flip();
        bufs[1].flip();
        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("-----------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        //聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = raf2.getChannel();
        channel2.write(bufs);

        raf2.close();
        raf.close();
    }

    @Test
    public void test5(){
        Map<String, Charset> map = Charset.availableCharsets();
        for (String key : map.keySet()) {
            Charset value = map.get(key);
            System.out.println(key + "-->" + value);
        }
    }

    @Test
    public void test6() throws IOException {
        Charset cs1 = Charset.forName("GBK");
        //获取编码器
        CharsetEncoder ce = cs1.newEncoder();

        //获取解码器
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("尚硅谷威武！");
        cBuf.flip();

        //编码
        ByteBuffer bBuf = ce.encode(cBuf);
        //System.out.println(bBuf.limit());

        for (int i = 0; i < 12; i++){
            System.out.println(bBuf.get());
        }

        //解码
        bBuf.flip();
        CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());

        //通过utf-8的解码方式来对gbk编码的字符进行读取（乱码）
        Charset cs2 = Charset.forName("UTF-8");
        bBuf.flip();
        CharBuffer cBuf3 = cs2.decode(bBuf);
        System.out.println(cBuf3.toString());
    }
}
