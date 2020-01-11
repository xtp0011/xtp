package com.xtp.sourceanalysis.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 1.SimpleChatServerHandler 继承自 SimpleChannelInboundHandler ，
 * 这个类实现了  ChannelInboundHandler 接口，ChannelInboundHandler 提供了许多事件处理的接口方法，
 * 然后你可以覆盖这些方法。现在仅仅只需要继承 SimpleChannelInboundHandler 类而不是你自己去实现接口方法。
 *
 * 2.覆盖了 handlerAdded() 事件处理方法。每当从服务端收到新的客户端连接时，
 * 客户端的 Channel 存入 ChannelGroup 列表中，并通知列表中的其他客户端 Channel
 *
 * 3.覆盖了 handlerRemoved() 事件处理方法。每当从服务端收到客户端断开时，
 * 客户端的 Channel 移除 ChannelGroup 列表中，并通知列表中的其他客户端 Channel
 *
 * 4.覆盖了 channelRead0() 事件处理方法。每当从服务端读到客户端写入信息时，
 * 将信息转发给其他客户端的 Channel。其中如果你使用的是 Netty 5.x 版本时，
 *需要把 channelRead0() 重命名为messageReceived()
 *
 * 5.覆盖了 channelActive() 事件处理方法。服务端监听到客户端活动
 *
 * 6.覆盖了 channelInactive() 事件处理方法。服务端监听到客户端不活动
 *
 * 7.exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，
 * 即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时。在大部分情况下，
 * 捕获的异常应该被记录下来并且把关联的 channel 给关闭掉。
 * 然而这个方法的处理方式会在遇到不同异常的情况下有不同的实现，
 * 比如你可能想在关闭连接之前发送一个错误码的响应消息。
 *
 */
public class SimpleChatServer {
    private int port;
    public SimpleChatServer(int port) {
        this.port = port;
    }
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                                    //SimpleChatClientInitializer
                    .childHandler(new SimpleChatServerInitializer())  //(4)
                    .option(ChannelOption.SO_BACKLOG, 128)		  // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
            System.out.println("SimpleChatServer 启动了");
            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync(); // (7)
            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("SimpleChatServer 关闭了");
        }
    }
    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new SimpleChatServer(port).run();
    }
}

