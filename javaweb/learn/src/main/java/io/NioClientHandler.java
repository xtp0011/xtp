package io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 客户端线程类，接收服务端响应信息
 */
public class NioClientHandler implements Runnable{
    private Selector selector;

    public NioClientHandler(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
        for (;;){
            int readyChannel = selector.select();
            if(readyChannel==0){
                continue;
            }
            ////获取可用的Channel集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                //获取selectionKey的实例
                SelectionKey selectionKey = iterator.next();
                //移除set中当前的selectionKey
                iterator.remove();
                //7、根据就绪状态处理相应的事件
                if(selectionKey.isReadable()){
                    readHanddler(selectionKey,selector);
                }
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 可读事件处理器
     */
    private void readHanddler(SelectionKey selectionKey, Selector selector) throws IOException {
        //1.selectionKey获取已就绪的channel
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //2.创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //3.循环读取服务端响应信息
        StringBuilder response = new StringBuilder();
        while (socketChannel.read(byteBuffer)>0){
            //切换为读模式
            byteBuffer.flip();
            response.append(Charset.forName("UTF-8").decode(byteBuffer));
        }
        //4.将channel再次注册到selector
        socketChannel.register(selector,SelectionKey.OP_READ);
        //5.将服务器端响应信息打印到本地
        if(response.length()>0){
            System.out.println(response.toString() );
        }

    }
}
