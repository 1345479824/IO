package com.nio.Day01;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * 一、缓冲区，在java NIO 中负责数据的存取。缓冲区就是数组，用于存储不同数据类型的数据。
 * 根据数据类型不同，提供了相应类型的缓冲区：boolean除外，其它基本类型都有
 * ByteBuffer
 * charBuffer
 * shortBuffer
 * intBuffer
 * longBuffer
 * floatBuffer
 * doubleBuffer
 *
 * 上述缓冲区的管理方式几乎一致，通过allocate()获取缓冲区
 *
 * 二、缓冲区用于存取数据的两个核心方法
 * put():存入数据到缓冲区中
 * get():获取缓冲区中的数据
 *
 * 三、缓冲区中的四个核心属性
 * capacity：容量，表述缓冲区中最大存储数据的容量，一旦申明不能改变。
 * limit：界限，表示缓冲区中可以操作数据的大小
 * position:位置，表示缓冲区中正在操作数据的位置（position<=limit<=capacity）
 * mark:标记，记录当前position的位置。可以通过reset()回复到mark记录的位置
 *
 * 四、直接缓冲区与非直接缓冲区
 * 非直接缓冲区：通过allocate()方法分配缓冲区，将缓存区建立在jvm内存中
 * 直接缓冲区：通过allocateDirect()方法分配直接缓冲区，将缓冲区建立在物理内存中，可以提高效率
 */
public class TestBuffer {

    /**
     * 测试put(),get(),allocation,limit,position,mark,hasRemaining
     */
    @Test
    public void test1(){
        System.out.println("--------分配缓冲区：allocation-------");
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println("--------写入缓冲区: put()---------");
        String str = "哈哈哈";
        buf.put(str.getBytes());
        System.out.println(buf.position());

        //切换到读数据模式，此时position会被置为0，limit会被置为9
        System.out.println("---------切换为读模式：flip()--------");
        buf.flip();
        System.out.println(buf.position());
        System.out.println(buf.limit());
        buf.mark();

        //进行读取，position会被置为9， limit也是9
        System.out.println("---------读取：get()--------");
        byte[] data = new byte[buf.limit()];
        buf.get(data);
        System.out.println(new String(data));
        System.out.println(buf.position());
        System.out.println(buf.limit());

        //测试reset，reset会将position的位置移回到0
        System.out.println("----------测试reset()--------");
        buf.reset();
        System.out.println(buf.position());
        byte[] data1 = new byte[buf.limit()];
        buf.get(data1);
        System.out.println(new String(data1));

        //清空缓冲区，将position置为0，limit置为1024
        System.out.println("------清空缓冲区：clear()-------");
        buf.clear();
        System.out.println(buf.position());
        System.out.println(buf.limit());

        //判断缓冲区中剩余数据
        if(buf.hasRemaining()){
            System.out.println(buf.remaining());
        }
    }

    //直接缓冲区
    @Test
    public void test2(){
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        System.out.println(buf.isDirect());
    }

}
