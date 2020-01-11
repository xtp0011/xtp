package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * 客户端
 */
public class NioClient {

    /**
     * 启动
     */
    public void start(String nike) throws IOException {
        //1.连接服务端
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",8000));

        //2.接收服务端响应
        //新开一个线程，专门负责接收服务器响应的消息
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        new Thread(new NioClientHandler(selector)).start();

        //3.向服务端发送数据
        Scanner scanner = new Scanner(System.in);
        System.out.println("请入发送消息：");
        while (scanner.hasNextLine()){
            String request = scanner.nextLine();
            if(request!=null&&request.length()>0){
                socketChannel.write(Charset.forName("UTF-8").encode(nike+" : "+request));
            }
        }

    }
}
