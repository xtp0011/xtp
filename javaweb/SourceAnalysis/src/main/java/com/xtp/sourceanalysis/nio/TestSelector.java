package com.xtp.sourceanalysis.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class TestSelector {

    @Test
    public void testNioServer() throws IOException {
        //1.获取通道
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //2.切换非阻塞模式
        ssc.configureBlocking(false);
        //3.绑定连接
        ssc.bind(new InetSocketAddress(8080));
        //4.获取选择器
        Selector selector = Selector.open();
        //5.将通道注册到选择器上，并指定监听事件
        ssc.register(selector,SelectionKey.OP_ACCEPT);
        //6.轮询式的获取选择器上已经准备就绪的事件
        while (selector.select()>0){
            //7.获取当前选择器中所有注册的“选择键（已就绪的监听事件）”
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()){
                //8.获取准备就绪的事件
                SelectionKey key = it.next();
                //9.判断具体就绪的是什么事件
                if(key.isAcceptable()){
                    //10.若接收就绪，获取客户端连接
                    SocketChannel sc = ssc.accept();
                    //11.切换非阻塞模式
                    sc.configureBlocking(false);
                    //12.将通道注册到选择器上
                    sc.register(selector,SelectionKey.OP_READ);
                }else if(key.isReadable()){
                    //13.获取当前选择器上读就绪的通道
                    SocketChannel sc = (SocketChannel) key.channel();
                    //14.读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len =-1;
                    while ((len=sc.read(buffer))!=-1){
                        buffer.flip();
                        System.out.println(new String(buffer.array(),0,len));
                        buffer.clear();
                    }
                }
                //5.取消选择键selectionKey
                it.remove();
            }
        }
    }

    @Test
    public void testNioClient() throws IOException {
        //1.获取通道
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1",8080));
        //2.切换非阻塞模式
        sc.configureBlocking(false);
        //3.分配缓存区大小
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //4.发送数据到服务端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String str = scanner.next();
            buffer.put((new Date().toString()+"\n"+str).getBytes());
            buffer.flip();
            sc.write(buffer);
            buffer.clear();
        }
       //5关闭通道
        sc.close();
    }
}
