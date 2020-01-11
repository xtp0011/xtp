package io;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 服务端
 */
public class NioServer {

    /**
     * 启动服务器
     */
    public void start() throws IOException {
        //1.创建Selector
        Selector selector = Selector.open();
        //2.通过ServerSocketChannel创建通道Channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //3.Channel绑定监听端口
        serverSocketChannel.bind(new InetSocketAddress(8000));
        //4.设置channel为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //5.将channel注册到selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器启动成功！");
        //6.循环等待新接入连接
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
                    //1、接入事件
                if(selectionKey.isAcceptable()){
                    acceptHandler(serverSocketChannel,selector);
                }
                    //2、可读事件
                if(selectionKey.isReadable()){
                    readHanddler(selectionKey,selector);
                }
            }
        }

    }

    /**
     * 接入事件处理器
     */
    private void acceptHandler(ServerSocketChannel serverSocketChannel,Selector selector) throws IOException {
        //1、创建socketChannel
        SocketChannel socketChannel = serverSocketChannel.accept();
        //2.将socketChannel非阻塞模式
        socketChannel.configureBlocking(false);
        //3.将socketChannel注册到selector上
        socketChannel.register(selector,SelectionKey.OP_READ);
        //4.回复客户端响应
        socketChannel.write(Charset.forName("UTF-8")
                .encode("你与聊天室里的其他人不说朋友关系，请注意意思安全"));
    }

    /**
     * 可读事件处理器
     */
    private void readHanddler(SelectionKey selectionKey, Selector selector) throws IOException {
        //1.selectionKey获取已就绪的channel
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //2.创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //3.循环读取客户端请求信息
        StringBuilder request = new StringBuilder();
        while (socketChannel.read(byteBuffer)>0){
            //切换为读模式
            byteBuffer.flip();
            request.append(Charset.forName("UTF-8").decode(byteBuffer));
        }
        //4.将channel再次注册到selector
        socketChannel.register(selector,SelectionKey.OP_READ);
        //5.将客户端发送的请求信息，广播给其他客户端
        if(request.length()>0){
            broadCast(selector,socketChannel,request);
        }
    }

    /**
     * 广播给其他客户端
     */
    private void broadCast(Selector selector,SocketChannel sourceChannel,StringBuilder request){
        //获取已接入的客户端channel
        Set<SelectionKey> keys = selector.keys();
        keys.forEach(selectionKey -> {
            Channel tagetChannel = selectionKey.channel();
            //剔除发送消息channel
            if(tagetChannel instanceof SocketChannel && tagetChannel!=sourceChannel){
                try {
                    //将信息发送给targetChannel
                    ((SocketChannel) tagetChannel).write(Charset.forName("UTF-8").encode(request.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 主方法
     * @param args
     */
    public static void main(String[] args) throws IOException {
        NioServer nioServer = new NioServer();
        nioServer.start();
    }
}
