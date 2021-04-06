package com.itheima.io.stream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FIOS {
    //文件输入输出流测试 FileInputStream FileOutPutStream
    public void fileIOStreamTest(){
        File file = new File("用于读.txt");
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        byte[] buff = new byte[1024];
        long last = System.currentTimeMillis();
        try{
            inputStream = new FileInputStream(file);
            outputStream = new FileOutputStream("用于写.txt");
            int len = 0;
            while((len = inputStream.read(buff)) != -1){
                outputStream.write(buff);
            }
        }catch (Exception e){
            System.out.println("aa");
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long now = System.currentTimeMillis();
        System.out.println(now - last);
    }

    //bufferInputStream bufferOutPutStream 测试
    public void bufferIOStreamTest() {
        File file = new File("buffer用于读.txt");
        FileInputStream fIS = null;
        BufferedInputStream bufferIS = null;
        FileOutputStream fOS = null;
        BufferedOutputStream bufferOS = null;
        long last = System.currentTimeMillis();
        try {
            fIS = new FileInputStream(file);
            bufferIS = new BufferedInputStream(fIS);
            fOS = new FileOutputStream("buffer用于写.txt");
            bufferOS = new BufferedOutputStream(fOS);
            byte[] buff = new byte[1024];
            int len = -1;
            while((len = bufferIS.read(buff)) != -1){
                bufferOS.write(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferOS.close();
                fOS.close();
                bufferIS.close();
                fIS.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long now = System.currentTimeMillis();
        System.out.println(now - last);
    }

    //fileReader fileWriter
    public void fileRW(){
        FileReader fR = null;
        FileWriter fW = null;
        try {
            fR = new FileReader("reader用于读");
            fW = new FileWriter("reader用于写.txt");
            char[] buff = new char[1024];
            int len = 0;
            while((len = fR.read(buff)) != -1){
                fW.write(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fR.close();
                fW.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    
    //bufferReader bufferWriter
    public void bufferRW(){
        FileReader fR = null;
        BufferedReader bR = null;
        FileWriter fW = null;
        BufferedWriter bW = null;

        try {
            fR = new FileReader("bufferReader用于读");
            bR = new BufferedReader(fR);
            fW = new FileWriter("bufferReader用于写");
            bW = new BufferedWriter(fW);
            int len = 0;
            char[] buff = new char[1024];
            while((len = bR.read(buff)) != -1){
                bW.write(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bW.close();
                fW.close();
                bR.close();
                fR.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new FIOS().bufferRW();
    }
}
