package com.xtp.sourceanalysis.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TestSocketChannel {

    @Test
    public void testServer() throws Exception{
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8080));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true){
            SocketChannel sc = ssc.accept();
            System.out.println("client come....");
            sc.read(buffer);
            buffer.flip();
            System.out.println("server:"+new String(buffer.array()));
            sc.close();
        }

    }
    @Test
    public void testSocket() throws Exception{
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1",8080));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("hello server".getBytes());
        buffer.flip();
        sc.write(buffer);
        sc.close();
    }
}
